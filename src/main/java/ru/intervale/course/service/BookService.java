package ru.intervale.course.service;

import ru.intervale.course.model.Book;
import ru.intervale.course.model.responses.BookLibraryResult;

import java.util.List;
import java.util.Map;

public interface BookService {
    /**
     * Возвращает список всех книг из базы данных
     * @return список всех книг
     */
    List<Book> getBooks();

    /**
     * Возвращает книгу из БД с указанным ID
     * @param id ID книги в БЗ
     * @return книга с указанным ID
     */
    Book getBookById(int id);

    /**
     * Изменяет поля книги с ID, равным ID переданного BookDTO, согласно списку установленных полей данного DTO
     * @param book BookDTO с полями, которые необходимом изменить в БД
     * @return класс, содержащий результат выполнения запроса
     */
    BookLibraryResult addBook(Book book);

    /**
     * Добавляет новую запись книги в БД
     * @param book добавляемая книга в БД
     * @return класс, содержащий результат выполнения запроса
     */
    BookLibraryResult editBook(int id, Book book);

    /**
     * Удаляет запись с указанным ID из БД
     * @param id ID для удаления в БД
     * @return класс, содержащий результат выполнения запроса
     */
    BookLibraryResult deleteBookById(int id);

    /**
     * Возвращает Map (ключ - место поиска, значение - список книг) всех книг автора из БД и Open Library
     * @param author данные автора для поиска
     * @return список книг указанного автора
     */
    Map<String, Object> getBooksByAuthor(String author);
}
