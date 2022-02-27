package ru.intervale.course.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Library {
    private static Library instance = new Library();
    private Map<Integer, Book> books;
    private List<Integer> freeId;

    private Library() {
        this.books = new HashMap<>();
        this.freeId = new ArrayList<>();
        books.put(1, new Book(1, "938-5-126-85123-9", "Гарри Поттер и Кубок огня","Дж.К. Роулинг", 280, 480, 2260));
        books.put(2, new Book(2, "548-5-123-82153-9", "Властелин Колец. Хранители Кольца","Дж.Р.Р. Толкин", 512, 793, 3570));
        books.put(3, new Book(3, "148-5-623-82153-0", "Эрагон. Брисингр","К. Паолини", 487, 635, 2420));
        books.put(4, new Book(4, "978-5-041-17971-7", "Когда мир изменился","Н. Перумов", 480, 480, 2300));
        books.put(5, new Book(5, "914-5-123-22103-0", "Короли Жути","Н. Имс", 544, 581, 2772));
    }

    public static Library getInstance() {
        return instance;
    }

    public void deleteBook(int id) {
        books.remove(id);
    }

    public boolean addBook(Book book) {
        boolean addingResult = false;
        if (!containsBookWithId(book.getID())) {
            books.put(book.getID(), book);
            addingResult = true;
        }
        return addingResult;
    }

    public Book getBook(int id) {
        return books.get(id);
    }

    public boolean containsBookWithId(int id) {
        return books.containsKey(id);
    }

    public Book editBook(Book book) {
        return books.replace(book.getID(), book);
    }
}
