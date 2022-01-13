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
        books.put(getId(), new Book(getId(), "938-5-126-85123-9", "Гарри Поттер и Кубок огня","Дж.К. Роулинг", 280, 480, 2260));
        books.put(getId(), new Book(getId(), "548-5-123-82153-9", "Властелин Колец. Хранители Кольца","Дж.Р.Р. Толкин", 512, 793, 3570));
        books.put(getId(), new Book(getId(), "148-5-623-82153-0", "Эрагон. Брисингр","К. Паолини", 487, 635, 2420));
        books.put(getId(), new Book(getId(), "978-5-041-17971-7", "Когда мир изменился","Н. Перумов", 480, 480, 2300));
        books.put(getId(), new Book(getId(), "914-5-123-22103-0", "Короли Жути","Н. Имс", 544, 581, 2772));
    }

    public static Library getInstance() {
        return instance;
    }

    public void deleteBook(int id) {
        if (id != books.size() ) {
            freeId.add(id);
        }
        books.remove(id);
    }

    public void addBook(Book book) {
        books.put(book.getID(), book);
    }

    public Book getBook(int id) {
        return books.get(id);
    }

    public int getId() {
        int id;
        if (freeId.size() == 0) {
            id = books.size() + 1;
        }
        else {
            id = freeId.get(0);
            freeId.remove(0);
        }
        return id;
    }
}
