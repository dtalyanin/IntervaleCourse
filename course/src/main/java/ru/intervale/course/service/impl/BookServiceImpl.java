package ru.intervale.course.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.intervale.course.dao.impl.BookDaoImpl;
import ru.intervale.course.integration.model.OpenLibraryBook;
import ru.intervale.course.integration.model.Work;
import ru.intervale.course.integration.service.impl.OpenLibraryServiceImpl;
import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDto;
import ru.intervale.course.service.BookService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookDaoImpl bookDao;

    @Autowired
    OpenLibraryServiceImpl libraryService;

    @Override
    public List<Book> getBooks() {
        return bookDao.getBooks();
    }

    @Override
    public Book getBookById(int id) {
        return bookDao.getBookById(id);
    }

    @Override
    public String editBook(BookDto book) {
        int executingResult = bookDao.editBook(book);
        String result;
        if (executingResult > 0) {
            result = "Book with ID = " + executingResult + " changed.";
        }
        else if (executingResult == -999) {
            result = "No fields to edit.";
        }
        else {
            result = "Incorrect ID.";
        }
        return result;
    }

    @Override
    public String addBook(Book book) {
        int addingResult = bookDao.addBook(book);
        String result;
        if (addingResult != 0) {
            result = "Book added successfully.";
        }
        else {
            result = "Error in expression.";
        }
        return result;
    }

    @Override
    public String deleteBook(int id) {
        int deletingResult = bookDao.deleteBook(id);
        String result;
        if (deletingResult !=0) {
            result = "Book with ID = " + id + " deleted.";
        }
        else {
            result = "Book with ID = " + id + " doesn’t exist.";
        }
        return result;
    }

    @Override
    public List<Work> getWorksByAuthor(String author) {
        List<Work> works = libraryService.getWorksByAuthorFromOpenLibrary(author);
        works.addAll(bookDao.getBooksByAuthorAsWork(author));
        return works;
    }

    @Override
    public Map<String, Object> getBooksByAuthor(String author) {
        HashMap<String, Object> books = new HashMap<>();
        List<OpenLibraryBook> booksFromOl = libraryService.getBooksByAuthorFromOpenLibrary(author);
        List<Book> booksFromDb = bookDao.getBooksByAuthor(author);
        String noBooksFound = "No books found for author '" + author + "'.";
        books.put("Books from Opel Library", booksFromOl.size() != 0 ? booksFromOl : noBooksFound);
        books.put("Books from database", booksFromDb.size() != 0 ? booksFromDb : noBooksFound);
        return books;
    }
}
