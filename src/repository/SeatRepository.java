package repository;

import ui.DatabaseHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatRepository {

    public static class SeatRow {
        public int id;
        public int busId;
        public String seatNumber;
        public boolean isBooked;

        public SeatRow(int id, int busId, String seatNumber, boolean isBooked) {
            this.id = id;
            this.busId = busId;
            this.seatNumber = seatNumber;
            this.isBooked = isBooked;
        }
    }

    public boolean isSeatAvailable(int seatId) {

        String sql = "SELECT is_booked FROM seats WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, seatId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("is_booked") == 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // 🔥 GET ALL SEATS BY BUS
    public List<SeatRow> findByBus(int busId) {
        List<SeatRow> list = new ArrayList<>();

        String sql = "SELECT * FROM seats WHERE bus_id = ? ORDER BY seat_number";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, busId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SeatRow seat = new SeatRow(
                        rs.getInt("id"),
                        rs.getInt("bus_id"),
                        rs.getString("seat_number"),
                        rs.getInt("is_booked") == 1 // 🔥 ini penting
                );

                list.add(seat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔥 BOOK MULTIPLE SEATS
    public void bookSeats(List<Integer> seatIds) {
        String sql = "UPDATE seats SET is_booked = 1 WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int id : seatIds) {
                ps.setInt(1, id);
                ps.addBatch();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}