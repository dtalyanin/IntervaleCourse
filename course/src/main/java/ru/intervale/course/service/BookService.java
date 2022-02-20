package ru.intervale.course.service;


import ru.intervale.course.integration.model.Work;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDto;
import java.util.List;
import java.util.Map;

public interface BookService {
    public List<Book> getBooks();

    public Book getBookById(int id);

    public String editBook(BookDto book);

    public String addBook(Book book);

    public String deleteBook(int id);

    public List<Work> getWorksByAuthor(String author);

    public Map<String, Object> getBooksByAuthor(String author);
}
