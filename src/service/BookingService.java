package service;

import ui.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Set;

public class BookingService {

    public void createBookingWithSeats(int busId, String name, Set<String> seats, int total) {

        String updateSeatSql =
                "UPDATE seats SET is_booked = 1 " +
                        "WHERE bus_id = ? AND seat_number = ? AND is_booked = 0";

        String insertBookingSql =
                "INSERT INTO bookings (bus_id, passenger_name, seats, total_price) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseHelper.connect()) {

            conn.setAutoCommit(false); // 🔥 satu transaksi

            try (
                    PreparedStatement seatPs = conn.prepareStatement(updateSeatSql);
                    PreparedStatement bookingPs = conn.prepareStatement(insertBookingSql)
            ) {

                int updated = 0;

                // 🔥 TANPA BATCH (INI FIX UTAMA)
                for (String seat : seats) {
                    seatPs.setInt(1, busId);
                    seatPs.setString(2, seat);

                    int result = seatPs.executeUpdate(); // ✅ aman
                    updated += result;
                }

                // cek kalau ada seat gagal
                if (updated != seats.size()) {
                    conn.rollback();
                    throw new RuntimeException("Seat already booked!");
                }

                // insert booking
                bookingPs.setInt(1, busId);
                bookingPs.setString(2, name);
                bookingPs.setString(3, seats.toString());
                bookingPs.setInt(4, total);

                bookingPs.executeUpdate();

                conn.commit(); // ✅ sukses

                System.out.println("BOOKING SUCCESS");

            } catch (Exception e) {
                conn.rollback();
                throw e;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}