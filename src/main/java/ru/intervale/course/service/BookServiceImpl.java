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

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Book getBookById(int id) {
        Book book = bookDao.getBookById(id);
        if (book == null) {
            throw new IncorrectBookIdException("No book with id = " + id + " presents in library.");
        }
        return book;
    }

    @Override
    public boolean addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public boolean editBook(Book book) {
        return bookDao.editBook(book);
    }

    @Override
    public boolean deleteBookById(int id) {
        return bookDao.deleteBookById(id);
    }
}
