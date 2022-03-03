package ru.intervale.course.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.intervale.course.dao.impl.BookDaoImpl;
import ru.intervale.course.external.alfa_bank.model.Rate;
import ru.intervale.course.external.alfa_bank.model.RateListResponse;
import ru.intervale.course.external.alfa_bank.service.impl.AlfaBankServiceImpl;
import ru.intervale.course.external.open_library.model.OpenLibraryBook;
import ru.intervale.course.external.open_library.model.Work;
import ru.intervale.course.external.open_library.service.impl.OpenLibraryServiceImpl;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookCurrency;
import ru.intervale.course.model.BookDto;
import ru.intervale.course.model.BookRangeCurrency;
import ru.intervale.course.service.BookService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

/**
 * Получение данных по книгам из базы данных. Поддерживается интеграция полученных данных
 * с API Альфа-банка и библиотеки Open Library
 */

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookDaoImpl bookDao;

    @Autowired
    OpenLibraryServiceImpl libraryService;

    @Autowired
    AlfaBankServiceImpl alfaBankService;

    /**
     * Возвращает список всех книг из базы данных
     * @return список всех книг
     */
    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    /**
     * Возвращает книгу из БД с указанным ID
     * @param id ID книги в БЗ
     * @return книга с указанным ID
     */
    @Override
    public Book getBookById(int id) {
        return bookDao.getBookById(id);
    }

    /**
     * Изменяет поля книги с ID, равным ID переданного BookDTO, согласно списку установленных полей данного DTO
     * @param book BookDTO с полями, которые необходимом изменить в БД
     * @return текстовый результат выполнения запроса
     */
    @Override
    public String editBook(BookDto book) {
        int executingResult = bookDao.editBook(book);
        String result;
        //если результат выполнения в BookDao больше нуля, значит книга с указанным ID была найдена и изменена
        if (executingResult > 0) {
            result = "Book with ID = " + executingResult + " changed.";
        }
        //если результат выполнения в BookDao -999, значит была передана книга с существующим в БД ID,
        //но не были заданы поля для изменения
        else if (executingResult == -999) {
            result = "No fields to edit.";
        }
        //если книга не была найдена в БД
        else {
            result = "Incorrect ID.";
        }
        return result;
    }

    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     * @return текстовый результат выполнения запроса
     */
    @Override
    public String addBook(Book book) {
        int addingResult = bookDao.addBook(book);
        String result;
        //BookDao возвращает количество затронутынх строк, если 0 - строка не была добавлена
        if (addingResult != 0) {
            result = "Book added successfully.";
        }
        else {
            result = "Error in expression.";
        }
        return result;
    }

    /**
     * Удаляет запись с указанным ID из БД
     * @param id ID для удаления в БД
     * @return текстовый результат выполнения запроса
     */
    @Override
    public String deleteBook(int id) {
        int deletingResult = bookDao.deleteBook(id);
        String result;
        //BookDao возвращает количество затронутынх строк, если 0 - строка с необходимым ID не была найдена
        if (deletingResult !=0) {
            result = "Book with ID = " + id + " deleted.";
        }
        else {
            result = "Book with ID = " + id + " doesn’t exist.";
        }
        return result;
    }

    /**
     * Возвращает список всех работ автора из БД и библиотеки Open Library, у которого
     * в Ф.И.О. встречается переданный текст
     * @param author данные автора для поиска
     * @return список работ указанного автора
     */
    @Override
    public List<Work> getWorksByAuthor(String author) {
        //Сервис возвращает список работ указанного автора из библиотеки Open Library
        List<Work> works = libraryService.getWorksByAuthorFromOpenLibrary(author);
        //BookDao возвращает список работ указанного автора из БД
        works.addAll(bookDao.getBooksByAuthorAsWork(author));
        return works;
    }

    /**
     * Возвращает список в виде Map (ключ - место поиска, значение - список книг)  всех книг автора из БД и библиотеки
     * Open Library, у которого в Ф.И.О. встречается переданный текст
     * @param author данные автора для поиска
     * @return список книг указанного автора в разрезе списка книг из БД и книг из Open Library
     */
    @Override
    public Map<String, Object> getBooksByAuthor(String author) {
        HashMap<String, Object> books = new HashMap<>();
        //Сервис возвращает список книг указанного автора из библиотеки Open Library
        List<OpenLibraryBook> booksFromOl = libraryService.getBooksByAuthorFromOpenLibrary(author);
        //BookDao возвращает список книг указанного автора из БД
        List<Book> booksFromDb = bookDao.getBooksByAuthor(author);
        String noBooksFound = "No books found for author '" + author + "'.";
        //В Map добавляются пары (ключ - библиотека для поиска списка книг, значение - список книг либо сообщение
        // об отсутствии книг в указанной библиотеке
        books.put("Books from Opel Library", booksFromOl.size() != 0 ? booksFromOl : noBooksFound);
        books.put("Books from database", booksFromDb.size() != 0 ? booksFromDb : noBooksFound);
        return books;
    }

    /**
     * Возвращает список из книги с заданным названием и стоимостью в различных валютах согласно курсам, получаемым
     * из API Альфа-банка
     * @param title название книги для поиска
     * @return список из названия книги и её стоиомсти в различных валютах
     */
    @Override
    public List<BookCurrency> getBooksWithRate(String title) {
        //Метод получает из сервиса список курсов валют из API Альфа-банка
        RateListResponse rates = alfaBankService.getRateList();
        //BookDao возвращает список книг с указанным названием
        List<Book> books = bookDao.getBooksByName(title);
        //Объединяет данные по книге и её стоимости
        return convertBookToBookCurrency(books, rates.getRates());
    }

    public List<BookRangeCurrency> getBooksWithRateInDynamic(String title, String currency) {
        List<Book> books = bookDao.getBooksByName(title);
        List<BookRangeCurrency> bookRangeCurrencies = new ArrayList<>();
        if (books.size() != 0) {
            Map<LocalDate, BigDecimal> rates = alfaBankService.getRatesInRange(currency);
            for (LocalDate key:
                    rates.keySet()) {
                System.out.println(key + " " + rates.get(key));
            }
            for (Book book: books) {
                bookRangeCurrencies.add(convertBookToRangeCurrency(book, rates));
            }
        }
        return bookRangeCurrencies;
    }

    /**
     *
     * @param books список книг
     * @param rates список валют с указанием курса конвертации
     * @return список книг по их названию и списку стоимости в различных валютах
     */
    private List<BookCurrency> convertBookToBookCurrency(List<Book> books, List<Rate> rates) {
        List<BookCurrency> bookCurrencies = new ArrayList<>();
        String byn = "BYN";
        for (Book book: books) {
            Map<String, BigDecimal> currencies = new LinkedHashMap<>();
            //Определяет стоимость книги в BYN
            BigDecimal bookPriceInByn = new BigDecimal(book.getPrice()/100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
            currencies.put(byn, bookPriceInByn);
            //Цикл для добавления к списку пересчета стомости в указанной валюте
            for (Rate rate: rates) {
                if (rate.getBuyIso().equals(byn)) {
                    currencies.put(rate.getSellIso(), bookPriceInByn.divide(rate.getBuyRate(), BigDecimal.ROUND_HALF_UP));
                }
            }
            BookCurrency bookCurrency = new BookCurrency(book.getName(), currencies);
            bookCurrencies.add(bookCurrency);
        }
        return bookCurrencies;
    }

    private BookRangeCurrency convertBookToRangeCurrency(Book book, Map<LocalDate, BigDecimal> rates) {
        BigDecimal bookPriceInByn = new BigDecimal(book.getPrice()/100.0).setScale(2, BigDecimal.ROUND_HALF_UP);
        Map<LocalDate, BigDecimal> prices = new LinkedHashMap<>();
        for (LocalDate date: rates.keySet()) {
            prices.put(date, bookPriceInByn.divide(rates.get(date)));
        }
        BookRangeCurrency bookRangeCurrency = new BookRangeCurrency(book.getName(), prices);
        return bookRangeCurrency;
    }
}
