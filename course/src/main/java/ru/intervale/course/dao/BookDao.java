package ru.intervale.course.dao;

import ru.intervale.course.model.Book;
import ru.intervale.course.model.BookDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    private final ConnectionHandler connectionHandler = ConnectionHandler.getInstance();

    public List<Book> getBooks() {
        Connection connection = connectionHandler.createConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        List<Book> books = new ArrayList<>();
        try {
            String sql = "SELECT * FROM BOOKS";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
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
        finally {
            connectionHandler.closeConnection(connection);
            connectionHandler.closeStatement(statement);
            connectionHandler.closeResultSet(resultSet);
        }
        return books;
    }

    public Book getBookById(int id) {
        Book book = null;
        Connection connection = connectionHandler.createConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            String sql = "SELECT * FROM BOOKS WHERE ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
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
        finally {
            connectionHandler.closeConnection(connection);
            connectionHandler.closeStatement(statement);
            connectionHandler.closeResultSet(resultSet);
        }
        return book;
    }

    public int deleteBook(int id) {
        Connection connection = connectionHandler.createConnection();
        PreparedStatement statement = null;
        int executingResult = -1;
        try {
            String sql = "DELETE FROM BOOKS WHERE ID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            executingResult = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connectionHandler.closeConnection(connection);
            connectionHandler.closeStatement(statement);
        }
        return executingResult;
    }

    public int addBook(Book book) {
        Connection connection = connectionHandler.createConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int executingResult = -1;
        try {
            String sql = "INSERT INTO BOOKS (ISBN, NAME, AUTHOR, PAGES, WEIGHT, PRICE) VALUES (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getName());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getPageAmount());
            statement.setInt(5, book.getWeight());
            statement.setInt(6, book.getPrice());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                executingResult = resultSet.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connectionHandler.closeConnection(connection);
            connectionHandler.closeStatement(statement);
            connectionHandler.closeResultSet(resultSet);
        }
        return executingResult;
    }

    public int editBook(BookDTO book) {
        String update = "UPDATE BOOKS SET ";
        Connection connection = connectionHandler.createConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int executingResult = -1;
        try {
            StringBuilder sql = new StringBuilder(update);
            if (book.getIsbn() != null) {
                sql.append("ISBN = ?,");
            }
            if (book.getName() != null) {
                sql.append("NAME = ?,");
            }
            if (book.getAuthor() != null) {
                sql.append("AUTHOR = ?,");
            }
            if (book.getPageAmount() != null) {
                sql.append("PAGES = ?,");
            }
            if (book.getWeight() != null) {
                sql.append("WEIGHT = ?,");
            }
            if (book.getPrice() != null) {
                sql.append("PRICE = ?,");
            }
            if (!update.equals(sql.toString())) {
                sql.deleteCharAt(sql.length() - 1);
                sql.append(" WHERE ID = ?");
                PreparedStatement preparedStatement = connection.prepareStatement(String.valueOf(sql), Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                if (book.getIsbn() != null) {
                    preparedStatement.setString(index++, book.getIsbn());
                }
                if (book.getName() != null) {
                    preparedStatement.setString(index++, book.getName());
                }
                if (book.getAuthor() != null) {
                    preparedStatement.setString(index++, book.getAuthor());
                }
                if (book.getPageAmount() != null) {
                    preparedStatement.setInt(index++, book.getPageAmount());
                }
                if (book.getWeight() != null) {
                    preparedStatement.setInt(index++, book.getWeight());
                }
                if (book.getPrice() != null) {
                    preparedStatement.setInt(index++, book.getPrice());
                }
                preparedStatement.setInt(index, book.getID());
                executingResult = preparedStatement.executeUpdate();
                resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    executingResult = resultSet.getInt("ID");
                }
            }
            else {
                executingResult = -999;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            connectionHandler.closeConnection(connection);
            connectionHandler.closeStatement(statement);
            connectionHandler.closeResultSet(resultSet);
        }
        return executingResult;
    }
}
