package ui;

import ui.DatabaseHelper;

import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

public class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:javabus.db";

    public static Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(URL);

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");
        }

        return conn;
    }

    // =========================
    // INIT DATABASE
    // =========================
    public static void initializeDatabase() {

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            // =========================
            // USERS TABLE
            // =========================
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "email TEXT PRIMARY KEY," +
                    "password TEXT NOT NULL" +
                    ")");

            // =========================
            // CITIES TABLE
            // =========================
            stmt.execute("CREATE TABLE IF NOT EXISTS cities (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL UNIQUE" +
                    ")");

            // =========================
            // TERMINALS TABLE
            // =========================
            stmt.execute("CREATE TABLE IF NOT EXISTS terminals (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "city_id INTEGER," +
                    "FOREIGN KEY (city_id) REFERENCES cities(id)" +
                    ")");

            // =========================
            // SEED DATA (ANTI DUPLICATE)
            // =========================

            // Cities
            stmt.execute("INSERT OR IGNORE INTO cities (name) VALUES " +
                    "('Istanbul')," +
                    "('Ankara')," +
                    "('Izmir')," +
                    "('Bursa')");

            // Terminals
            stmt.execute("INSERT OR IGNORE INTO terminals (name, city_id) VALUES " +
                    "('Esenler Otogar', 1)," +
                    "('Harem Otogar', 1)," +
                    "('ASTI Terminal', 2)," +
                    "('Izmir Bus Terminal', 3)," +
                    "('Bursa Terminal', 4)");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // REGISTER
    // =========================
    public static boolean registerUser(String email, String password) {
        String sql = "INSERT INTO users(email, password) VALUES(?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            pstmt.setString(2, hashed);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false; // email sudah ada
        }
    }

    // =========================
    // LOGIN
    // =========================
    public static boolean loginUser(String email, String password) {
        String sql = "SELECT password FROM users WHERE email = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password");
                return BCrypt.checkpw(password, storedHash);
            }

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
}