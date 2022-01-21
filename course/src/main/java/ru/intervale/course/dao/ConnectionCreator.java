package ru.intervale.course.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionCreator {
    private static final String DB_URL = "jdbc:h2:~/db";
    private static final String DB_Driver = "org.h2.Driver";
    private static final String USER = "admin";
    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName(DB_Driver);
        } catch (ClassNotFoundException  e) {
            e.printStackTrace();
        }
    }

    private ConnectionCreator() {
    }

    public static Connection createConnection() {
        Connection connection = null;
            try {
                connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return connection;
    }
}
