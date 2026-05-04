package service;

import repository.SeatRepository;
import repository.SeatRepository.SeatRow;
import ui.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class SeatService {

    private final SeatRepository repo = new SeatRepository();

    // =========================
    // 🔥 GENERATE SEATS (2-2) - SAFE & IDEMPOTENT
    // =========================
    public void generateSeatsIfNotExists(int busId, int capacity) {

        // kalau sudah ada seat untuk bus ini → skip (idempotent)
        List<SeatRow> existing = repo.findByBus(busId);
        if (!existing.isEmpty()) {
            System.out.println("Seats already exist for bus " + busId + ", skipping generate.");
            return;
        }

        generateSeatsForce(busId, capacity);
    }

    // =========================
    // 🔥 FORCE REGENERATE (DELETE + CREATE)
    // =========================
    public void regenerateSeats(int busId, int capacity) {
        deleteSeatsByBus(busId);
        generateSeatsForce(busId, capacity);
    }

    // =========================
    // 🔥 CORE GENERATOR (2-2)
    // =========================
    private void generateSeatsForce(int busId, int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }

        if (capacity % 4 != 0) {
            throw new IllegalArgumentException("Capacity must be divisible by 4 for 2-2 layout");
        }

        final String insertSql =
                "INSERT INTO seats (bus_id, seat_number, is_booked) VALUES (?, ?, 0)";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(insertSql)) {

            conn.setAutoCommit(false); // 🔥 transaction

            int seatsPerRow = 4;
            int rows = capacity / seatsPerRow;

            char rowLetter = 'A';

            for (int i = 0; i < rows; i++) {

                for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {

                    String seat = rowLetter + String.valueOf(seatNum);

                    ps.setInt(1, busId);
                    ps.setString(2, seat);
                    ps.addBatch();
                }

                rowLetter++;
            }
            conn.commit();

            System.out.println("Seats generated (2-2) for bus ID: " + busId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate seats", e);
        }
    }

    // =========================
    // 🔥 DELETE ALL SEATS BY BUS
    // =========================
    public void deleteSeatsByBus(int busId) {

        String sql = "DELETE FROM seats WHERE bus_id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, busId);
            ps.executeUpdate();

            System.out.println("All seats deleted for bus ID: " + busId);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete seats", e);
        }
    }

    // =========================
    // 🔥 GET SEATS
    // =========================
    public List<SeatRow> getSeats(int busId) {
        return repo.findByBus(busId);
    }

    // =========================
    // 🔥 BOOK SEATS (SAFE)
    // =========================
    public void bookSeats(List<Integer> seatIds) {

        if (seatIds == null || seatIds.isEmpty()) {
            throw new IllegalArgumentException("Seat list cannot be empty");
        }

        String sql = "UPDATE seats SET is_booked = 1 WHERE id = ? AND is_booked = 0";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false);

            int success = 0;

            for (int id : seatIds) {
                ps.setInt(1, id);

                int result = ps.executeUpdate(); // ✅ bukan batch
                success += result;
            }

            if (success != seatIds.size()) {
                conn.rollback();
                throw new RuntimeException("Some seats already booked!");
            }

            conn.commit();

            System.out.println("Seats booked: " + seatIds);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Booking failed", e);
        }
    }

    // =========================
    // 🔥 CHECK AVAILABILITY
    // =========================
    public boolean isSeatAvailable(int seatId) {
        return repo.isSeatAvailable(seatId);
    }

    // =========================
    // 🔥 GET SELECTED SEATS (DETAIL)
    // =========================
    public List<SeatRow> getSeatsByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) return Collections.emptyList();

        return getSeats(ids.get(0)).stream() // simple reuse
                .filter(s -> ids.contains(s.id))
                .collect(Collectors.toList());
    }
}