package ru.intervale.course.service;

import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDTO;
import ru.intervale.course.model.responses.BookLibraryResult;

import java.util.List;

public interface BookService {
    /**
     * Возвращает список всех книг из базы данных
     * @return список всех книг
     */
    List<Book> getBooks();

    /**
     * Возвращает книгу из БД с указанным ID
     * @param id ID книги в БД
     * @return книга с указанным ID
     */
    Book getBookById(int id);

    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     * @return результат выполнения запроса
     */
    BookLibraryResult addBook(Book book);

    /**
     * Измененяет книгу с указанным ID в БД
     * @param book книга для замены в БД
     * @return результат выполнения запроса
     */
    BookLibraryResult editBook(int id, Book book);

    /**
     * Удаляет запись с указанным ID из БД
     * @param id ID для удаления в БД
     * @return результат выполнения запроса
     */
    BookLibraryResult deleteBookById(int id);

    /**
     * Возвращает список всех книг автора из БД и Open Library
     * @param author данные автора для поиска
     * @return список книг указанного автора
     */
    List<BookDTO> getBooksByAuthor(String author);
}
