package ru.intervale.course.utils.mappers;

import org.springframework.stereotype.Component;
import ru.intervale.course.external.openlibrary.model.OpenLibraryBook;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDTO;

import java.util.Arrays;

/**
 * Конвертация книги из БД и библиотеки Open Library в её представление объекта класса BookDTO
 */
@Component
public class BookDTOMapper {

    /**
     * Конвертирует книгу из БД в объект класса BookDTO
     * @param book книга для конвертации
     * @return представление книги в виде объекта класса BookDTO
     */
    public BookDTO convertBookToBookDto(Book book) {
        BookDTO.BookDTOBuilder dto = BookDTO.builder();
        dto.name(book.getName());
        dto.isbn(Arrays.asList(book.getIsbn()));
        dto.authors(Arrays.asList(book.getAuthor()));
        dto.pageCount(book.getPageCount());
        dto.weight(book.getWeight() + " grams");
        dto.price(book.getPrice());
        return dto.build();
    }

    /**
     * Конвертирует книгу из библиотеки Open Library в объект класса BookDTO
     * @param book книга для конвертации
     * @return представление книги в виде объекта класса BookDTO
     */
    public BookDTO convertOpenLibraryBookToBookDto(OpenLibraryBook book) {
        BookDTO.BookDTOBuilder dto = BookDTO.builder();
        dto.id(book.getOlid());
        dto.name(book.getTitle());
        dto.authors(book.getAuthors());
        dto.isbn(book.getIsbn());
        if (book.getPagesCount() != null) {
            dto.pageCount(book.getPagesCount());
        }
        if (book.getWeight() != null) {
            dto.weight(book.getWeight());
        }
        return dto.build();
    }
}
