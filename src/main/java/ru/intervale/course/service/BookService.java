package ru.intervale.course.service;

import ru.intervale.course.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getBooks();

    Book getBookById(int id);

    String addBook(Book book);

    String editBook(int id, Book book);

    String deleteBookById(int id);
}
