package ui;

import java.sql.*;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:users.db";

    // Buat tabel
    public static void initializeDatabase() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "email TEXT PRIMARY KEY," +
                "password TEXT NOT NULL" +
                ")";

        try (Connection conn = DriverManager.getConnection(URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Register user
    public static boolean registerUser(String email, String password) {
        String sql = "INSERT INTO users(email, password) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false; // email sudah ada
        }
    }

    // Login user
    public static boolean loginUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
