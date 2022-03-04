package ru.intervale.course.dao;

import ru.intervale.course.model.Book;
import java.util.List;

public interface BookDao {
    List<Book> getBooks();

    Book getBookById(int id);

    void addBook(Book book);

    boolean editBook(Book book);

    boolean deleteBookById(int id);
}
