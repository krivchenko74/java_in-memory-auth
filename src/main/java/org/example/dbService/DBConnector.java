package org.example.dbService;

import java.sql.*;

public class DBConnector {

    private static final String URL ="jdbc:postgresql://localhost:5432/file_viewer";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load PostgreSQL driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void testConnection() {
        try (Connection conn = getConnection()) {

            if (conn == null || conn.isClosed()) {
                System.out.println("❌ Соединение не установлено");
                return;
            }

            System.out.println("✅ Соединение установлено");

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT 1")) {

                if (rs.next()) {
                    System.out.println("✅ SQL работает: " + rs.getInt(1));
                }
            }

        } catch (Exception e) {
            System.out.println("❌ Ошибка подключения:");
            e.printStackTrace();
        }
    }
}