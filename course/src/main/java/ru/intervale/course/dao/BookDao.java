package ru.intervale.course.dao;

import ru.intervale.course.integration.model.Work;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDto;
import java.util.List;

public interface BookDao {
    public List<Book> getBooks();

    public Book getBookById(int id);

    public List<Book> getBooksByAuthor(String author);

    public List<Work> getBooksByAuthorAsWork(String author);

    public int deleteBook(int id);

    public int addBook(Book book);

    public int editBook(BookDto book);
}
