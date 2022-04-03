package ru.intervale.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.intervale.course.dao.BookDao;
import ru.intervale.course.exception.IncorrectBookIdException;
import ru.intervale.course.external.alfabank.service.AlfaBankService;
import ru.intervale.course.external.openlibrary.service.OpenLibraryService;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDTO;
import ru.intervale.course.model.enums.OperationType;
import ru.intervale.course.model.responses.BookLibraryResult;
import ru.intervale.course.utils.mappers.BookDTOMapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Получение данных по книгам из базы данных. Поддерживается интеграция полученных данных из библиотеки Open Library
 */
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private OpenLibraryService openLibraryService;
    @Autowired
    private AlfaBankService alfaBankService;

    private static final String NO_BOOK_WITH_ID = "No book with ID presents in library";
    private static final String OPERATION_SUCCESSFUL = "Operation completed successfully";

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
     * @param id ID книги в БД
     * @return книга с указанным ID
     */
    @Override
    public Book getBookById(int id) {
        Book book = bookDao.getBookById(id);
        if (book == null) {
            throw new IncorrectBookIdException(NO_BOOK_WITH_ID);
        }
        return book;
    }


    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     * @return результат выполнения запроса
     */
    @Override
    public BookLibraryResult addBook(Book book) {
        bookDao.addBook(book);
        return new BookLibraryResult(OperationType.ADD, OPERATION_SUCCESSFUL);
    }

    /**
     * Заменяет книгу с указанным ID в БД на переданную в качестве параметра книгу
     * @param book книга для замены в БД
     * @return результат выполнения запроса
     */
    @Override
    public BookLibraryResult editBook(int id, Book book) {
        book.setId(id);
        if (!bookDao.editBook(book)) {
            throw new IncorrectBookIdException(NO_BOOK_WITH_ID);
        }
        return new BookLibraryResult(OperationType.EDIT, OPERATION_SUCCESSFUL);
    }

    /**
     * Удаляет запись с указанным ID из БД
     * @param id ID для удаления в БД
     * @return класс, содержащий результат выполнения запроса
     */
    @Override
    public BookLibraryResult deleteBookById(int id) {
        if (!bookDao.deleteBookById(id)) {
            throw new IncorrectBookIdException(NO_BOOK_WITH_ID);
        }
        return new BookLibraryResult(OperationType.DELETE, OPERATION_SUCCESSFUL);
    }

    /**
     * Возвращает список всех книг автора из БД и Open Library
     * @param author данные автора для поиска
     * @return список книг указанного автора
     */
    @Override
    public List<BookDTO> getBooksByAuthor(String author) {
        List<BookDTO> books = new ArrayList<>(openLibraryService.getBooksByAuthor(author));
        books.addAll(bookDao.getBooksByAuthor(author));
        return books;
    }

    /**
     * Возвращает список книг с заданным названием и стоимостью в различных валютах согласно курсам на сегодняшний день,
     * получаемым из API Альфа-банка
     * @param title название книги для поиска
     * @return список книг с заданным названием и стоимостью в различных валютах на сегодняшний день
     */
    @Override
    public List<BookDTO> getBooksByNameWithCurrentPrice(String title) {
        List<BookDTO> books = new ArrayList<>();
        List<Book> dbBooks = bookDao.getBooksByName(title);
        if (dbBooks.size() != 0) {
            Map<String, BigDecimal> rates = alfaBankService.getTodayRates();
            dbBooks.forEach(book -> books.add(BookDTOMapper.convertFromBook(book, rates)));
        }
        return books;
    }

    /**
     * Возвращает список книг с заданным названием и диапазоном их стоимости по дням в выбранной валюте
     * @param title название книги для поиска
     * @param currency код валюты согласно ISO
     * @param period диапазон, в котором выполнить поиск курсов валют
     * @return список книг с заданным названием и диапазоном их стоимости по дням в выбранной валюте
     */
    @Override
    public List<BookDTO> getBookByNameWithCurrencyPriceInRange(String title, String currency, int period) {
        List<Book> dbBooks = bookDao.getBooksByName(title);
        List<BookDTO> books = new ArrayList<>();
        if (dbBooks.size() != 0) {
            Map<String, BigDecimal> ratesInRange = alfaBankService.getRatesInRange(currency, period);
            dbBooks.forEach(book -> books.add(BookDTOMapper.convertFromBook(book, ratesInRange)));
        }
        return books;
    }
}
