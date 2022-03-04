package ru.intervale.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.intervale.course.dao.BookDao;
import ru.intervale.course.exception.IncorrectBookIdException;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.enums.OperationType;
import ru.intervale.course.model.responses.BookLibraryResult;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDao bookDao;
    private static final String NO_BOOK_WITH_ID = "No book with ID presents in library";
    private static final String BOOK_ADDED = "Book added successfully";
    private static final String OPERATION_SUCCESSFUL = "Operation completed successfully with book ID = ";

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Book getBookById(int id) {
        Book book = bookDao.getBookById(id);
        if (book == null) {
            throw new IncorrectBookIdException(NO_BOOK_WITH_ID);
        }
        return book;
    }

    @Override
    public BookLibraryResult addBook(Book book) {
        bookDao.addBook(book);
        return new BookLibraryResult(OperationType.ADD, BOOK_ADDED);
    }

    @Override
    public BookLibraryResult editBook(int id, Book book) {
        book.setId(id);
        if (!bookDao.editBook(book)) {
            throw new IncorrectBookIdException(NO_BOOK_WITH_ID);
        }
        return new BookLibraryResult(OperationType.EDIT, OPERATION_SUCCESSFUL + id);
    }

    @Override
    public BookLibraryResult deleteBookById(int id) {
        if (!bookDao.deleteBookById(id)) {
            throw new IncorrectBookIdException(NO_BOOK_WITH_ID);
        }
        return new BookLibraryResult(OperationType.DELETE, OPERATION_SUCCESSFUL + id);
    }
}
