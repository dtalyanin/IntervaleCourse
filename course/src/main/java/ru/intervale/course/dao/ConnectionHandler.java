package ru.intervale.course.dao;


import java.sql.*;

public class ConnectionHandler {
    private final String DB_URL = "jdbc:h2:" + System.getProperty("user.dir") + "/src/main/java/ru/intervale/course/db/db";
    private final String DB_Driver = "org.h2.Driver";
    private final String USER = "admin";
    private final String PASSWORD = "root";
    private static final ConnectionHandler instance = new ConnectionHandler();

    private ConnectionHandler() {
        try {
            Class.forName(DB_Driver);
        } catch (ClassNotFoundException  e) {
            e.printStackTrace();
        }
    }

    public static ConnectionHandler getInstance() {
        return instance;
    }

    public Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
