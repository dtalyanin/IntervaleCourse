package ru.intervale.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.intervale.course.dao.BookDao;
import ru.intervale.course.exception.IncorrectBookIdException;
import ru.intervale.course.external.open_library.model.OpenLibraryBook;
import ru.intervale.course.external.open_library.service.OpenLibraryService;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.enums.OperationType;
import ru.intervale.course.model.responses.BookLibraryResult;

import java.util.LinkedHashMap;
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
     * @param id ID книги в БЗ
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
     * Изменяет поля книги с ID, равным ID переданного BookDTO, согласно списку установленных полей данного DTO
     * @param book BookDTO с полями, которые необходимом изменить в БД
     * @return класс, содержащий результат выполнения запроса
     */
    @Override
    public BookLibraryResult addBook(Book book) {
        bookDao.addBook(book);
        return new BookLibraryResult(OperationType.ADD, OPERATION_SUCCESSFUL);
    }

    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     * @return класс, содержащий результат выполнения запроса
     */
    @Override
    public BookLibraryResult editBook(int id, Book book) {
        book.setId(id);
        if (!bookDao.editBook(book)) {
            throw new IncorrectBookIdException(NO_BOOK_WITH_ID);
        }
        return new BookLibraryResult(OperationType.EDIT, OPERATION_SUCCESSFUL + id);
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
        return new BookLibraryResult(OperationType.DELETE, OPERATION_SUCCESSFUL + id);
    }

    /**
     * Возвращает Map (ключ - место поиска, значение - список книг) всех книг автора из БД и Open Library
     * @param author данные автора для поиска
     * @return список книг указанного автора
     */
    @Override
    public Map<String, Object> getBooksByAuthor(String author) {
        String noBooksFound = "No books found for author '" + author + "'.";
        String booksFrom = "Books from ";
        Map<String, Object> books = new LinkedHashMap<>();
        List<OpenLibraryBook> booksFromOl = openLibraryService.getBooksByAuthorFromOpenLibrary(author);
        List<Book> booksFromDb = bookDao.getBooksByAuthor(author);
        books.put(booksFrom + "Open Library", booksFromOl.size() != 0 ? booksFromOl : noBooksFound);
        books.put(booksFrom + "database", booksFromDb.size() != 0 ? booksFromDb : noBooksFound);
        return books;
    }
}
