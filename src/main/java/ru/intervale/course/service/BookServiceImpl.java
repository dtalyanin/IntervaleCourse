package ru.intervale.course.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.intervale.course.dao.BookDao;
import ru.intervale.course.exception.IncorrectBookIdException;
import ru.intervale.course.model.Book;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    private BookDao bookDao;
    private static final String NO_BOOK_WITH_ID = "No book with ID presents in library";
    private static final String OPERATION_SUCCESSFUL = "Operation completed successfully with book ID = ";
    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Book getBookById(int id) {
        Book book = bookDao.getBookById(id);
        if (book == null) {
            throw new IncorrectBookIdException(NO_BOOK_WITH_ID, id);
        }
        return book;
    }

    @Override
    public String addBook(Book book) {
        return OPERATION_SUCCESSFUL + bookDao.addBook(book);
    }

    @Override
    public String editBook(int id, Book book) {
        book.setId(id);
        if (bookDao.editBook(book)) {
            throw new IncorrectBookIdException(NO_BOOK_WITH_ID, id);
        }
        return OPERATION_SUCCESSFUL + id;
    }

    @Override
    public String deleteBookById(int id) {
        if (bookDao.deleteBookById(id)) {
            throw new IncorrectBookIdException(NO_BOOK_WITH_ID, id);
        }
        return OPERATION_SUCCESSFUL + id;
    }
}
