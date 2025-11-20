package com.example.ahammad_2207084_cvbuilder;

import java.sql.*;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:student_gpa.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS student_gpa_entries (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                roll_no TEXT NOT NULL,
                semester TEXT NOT NULL,
                course_json TEXT NOT NULL,
                gpa REAL NOT NULL,
                timestamp TEXT DEFAULT CURRENT_TIMESTAMP
            );
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

