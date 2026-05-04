package repository;

import ui.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BookingRepository {

    public void saveBooking(int busId, String name, String seats, int total) {

        String sql = "INSERT INTO bookings (bus_id, passenger_name, seats, total_price, booking_date) VALUES (?, ?, ?, ?, datetime('now'))";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, busId);
            ps.setString(2, name);
            ps.setString(3, seats);
            ps.setInt(4, total);

            ps.executeUpdate();

            System.out.println("Booking saved to DB!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
