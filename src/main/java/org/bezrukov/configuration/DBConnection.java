package org.bezrukov.configuration;

import java.sql.*;

public class DBConnection{
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String pass = "123";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            connection = createConnection();
        }
        return connection;
    }

    private static Connection createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
