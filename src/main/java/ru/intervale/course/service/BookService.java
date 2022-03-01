package ru.intervale.course.service;

import ru.intervale.course.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooks();

    Book getBookById(int id);

    boolean addBook(Book book);

    boolean editBook(Book book);

    boolean deleteBookById(int id);
}
