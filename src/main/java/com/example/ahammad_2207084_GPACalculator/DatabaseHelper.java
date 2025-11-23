package com.example.ahammad_2207084_GPACalculator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public static void saveEntry(HistoryController.HistoryEntry entry) {
        String courseJson = serializeCourseList(entry.getCourseModel().getCourseList());

        String sql = "INSERT INTO student_gpa_entries (roll_no, semester, course_json, gpa) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entry.getRoll());
            pstmt.setString(2, entry.getSemester());
            pstmt.setString(3, courseJson);
            pstmt.setDouble(4, entry.getGpa());
            pstmt.executeUpdate();

            System.out.println("History entry saved for Roll: " + entry.getRoll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<HistoryController.HistoryEntry> loadAllEntries() {
        List<HistoryController.HistoryEntry> entries = new ArrayList<>();
        String sql = "SELECT roll_no, semester, course_json, gpa FROM student_gpa_entries ORDER BY id DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String roll = rs.getString("roll_no");
                String semester = rs.getString("semester");
                String courseJson = rs.getString("course_json");
                double gpa = rs.getDouble("gpa");

                List<Course> courses = deserializeCourseList(courseJson);

                CourseModel courseModel = new CourseModel(FXCollections.observableArrayList(courses));

                entries.add(new HistoryController.HistoryEntry(roll, semester, gpa, courseModel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }


    public static void clearAllEntries() {
        String sql = "DELETE FROM student_gpa_entries";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("All history entries cleared.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String serializeCourseList(ObservableList<Course> list) {
        if (list == null || list.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();

        for (Course c : list) {
            sb.append(c.getCourseCode()).append("|")
                    .append(c.getCourseCredit()).append("|")
                    .append(c.getGrade()).append("|")
                    .append(c.getCourseName()).append("|")
                    .append(c.getTeacher1()).append("|")
                    .append(c.getTeacher2()).append(";");
        }

        return sb.substring(0, sb.length() - 1); // remove last semicolon
    }


    private static List<Course> deserializeCourseList(String data) {
        List<Course> list = new ArrayList<>();
        if (data == null || data.isEmpty()) return list;

        String[] items = data.split(";");

        for (String item : items) {
            String[] p = item.split("\\|");

            if (p.length == 6) {
                list.add(new Course(
                        p[0],
                        p[1],
                        p[2],
                        p[3],
                        p[4],
                        p[5]
                ));
            }
        }

        return list;
    }




}

