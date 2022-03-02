package ru.intervale.course.dao;

import org.springframework.stereotype.Repository;
import ru.intervale.course.model.Book;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookDaoImpl implements BookDao {
    private final Map<Integer, Book> books;

    private BookDaoImpl() {
        this.books = new HashMap<>();
    }

    @PostConstruct
    private void addContent() {
        books.put(1, new Book(1, "938-5-126-85123-9", "Гарри Поттер и Кубок огня", "Дж.К. Роулинг", 280, 480, 2260));
        books.put(2, new Book(2, "548-5-123-82153-9", "Властелин Колец. Хранители Кольца","Дж.Р.Р. Толкин", 512, 793, 3570));
        books.put(3, new Book(3, "148-5-623-82153-0", "Эрагон. Брисингр","К. Паолини", 487, 635, 2420));
        books.put(4, new Book(4, "978-5-041-17971-7", "Когда мир изменился","Н. Перумов", 480, 480, 2300));
        books.put(5, new Book(5, "914-5-123-22103-0", "Короли Жути","Н. Имс", 544, 581, 2772));
    }

    @Override
    public List<Book> getBooks() {
        return new ArrayList<>(books.values());
    }

    @Override
    public Book getBookById(int id) {
        return books.get(id);
    }

    @Override
    public int addBook(Book book) {
        book.setId(books.size() + 1);
        books.put(book.getId(), book);
        return book.getId();
    }

    @Override
    public boolean editBook(Book book) {
        return books.replace(book.getId(), book) == null;
    }

    @Override
    public boolean deleteBookById(int id) {
        return books.remove(id) == null;
    }
}
