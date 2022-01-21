package ru.intervale.course.dao;

import ru.intervale.course.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    public List<Book> getBooks() {
        Connection connection = ConnectionCreator.createConnection();
        List<Book> books = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM BOOKS";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String isbn = resultSet.getString("ISBN");
                String name = resultSet.getString("NAME");
                String author = resultSet.getString("AUTHOR");
                int pageAmount = resultSet.getInt("PAGES");
                int weight = resultSet.getInt("WEIGHT");
                int price = resultSet.getInt("PRICE");
                books.add(new Book(id, isbn, name, author, pageAmount, weight, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public Book getBookById(int id) {
        Book book = null;
        Connection connection = ConnectionCreator.createConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM BOOKS WHERE ID = " + id;
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.isBeforeFirst()) {
                resultSet.next();
                String isbn = resultSet.getString("ISBN");
                String name = resultSet.getString("NAME");
                String author = resultSet.getString("AUTHOR");
                int pageAmount = resultSet.getInt("PAGES");
                int weight = resultSet.getInt("WEIGHT");
                int price = resultSet.getInt("PRICE");
                book = new Book(id, isbn, name, author, pageAmount, weight, price);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }


    public int deleteBook(int id) {
        Connection connection = ConnectionCreator.createConnection();
        int res = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE FROM BOOKS WHERE ID = " + id;
            res = statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            res = -1;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public int addBook(Book book) {
        Connection connection = ConnectionCreator.createConnection();
        int res = -1;
        try {
            String sql = "INSERT INTO BOOKS (ISBN, NAME, AUTHOR, PAGES, WEIGHT, PRICE) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setInt(4, book.getPageAmount());
            preparedStatement.setInt(5, book.getWeight());
            preparedStatement.setInt(6, book.getPrice());
            preparedStatement.executeUpdate();
            ResultSet gk = preparedStatement.getGeneratedKeys();
            if (gk.next()) {
                res = gk.getInt("ID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            res = -1;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }
}
