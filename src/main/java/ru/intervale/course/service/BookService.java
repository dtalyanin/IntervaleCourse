package ru.intervale.course.service;

import ru.intervale.course.model.Book;
import ru.intervale.course.model.responses.BookLibraryResult;

import java.util.List;

public interface BookService {
    List<Book> getBooks();

    Book getBookById(int id);

    BookLibraryResult addBook(Book book);

    BookLibraryResult editBook(int id, Book book);

    BookLibraryResult deleteBookById(int id);
}
