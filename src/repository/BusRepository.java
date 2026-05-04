package repository;

import model.Bus;
import ui.DatabaseHelper;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusRepository {

    // 🔥 1. INSERT BUS
    public void add(Bus bus) {

        String sql = """
            INSERT INTO buses (
                marka,
                from_city_id,
                to_city_id,
                departure_date,
                departure_time,
                arrival_time,
                price,
                capacity
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, bus.getMarka());
            ps.setInt(2, bus.getFromCityId());
            ps.setInt(3, bus.getToCityId());
            ps.setString(4, bus.getDepartureDate());
            ps.setString(5, bus.getDepartureTime());
            ps.setString(6, bus.getArrivalTime());
            ps.setInt(7, bus.getPrice());
            ps.setInt(8, bus.getKoltukSayisi());

            ps.executeUpdate();

            System.out.println("Bus inserted into database!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 2. DELETE BUS
    public void remove(int id) {

        String sql = "DELETE FROM buses WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

            System.out.println("Bus removed!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 3. FIND BY ID
    public Bus findById(int id) {

        String sql = "SELECT * FROM buses WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToBus(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // 🔥 4. GET ALL BUSES
    public List<Bus> getAll() {

        List<Bus> list = new ArrayList<>();

        String sql = "SELECT * FROM buses";

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                list.add(mapResultSetToBus(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔥 5. SEARCH (PENTING BANGET)
    public List<Bus> search(int fromCityId, int toCityId) {

        List<Bus> list = new ArrayList<>();

        String sql = """
        SELECT * FROM buses
        WHERE from_city_id = ?
        AND to_city_id = ?
    """;

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, fromCityId);
            ps.setInt(2, toCityId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Bus bus = new Bus(
                        rs.getInt("id"),
                        rs.getString("marka"),
                        rs.getInt("capacity")
                );

                bus.setFromCityId(rs.getInt("from_city_id"));
                bus.setToCityId(rs.getInt("to_city_id"));
                bus.setDepartureTime(rs.getString("departure_time"));
                bus.setArrivalTime(rs.getString("arrival_time"));
                bus.setPrice(rs.getInt("price"));

                list.add(bus);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔥 HELPER (WAJIB BANGET BIAR CLEAN)
    private Bus mapResultSetToBus(ResultSet rs) throws SQLException {

        Bus bus = new Bus(
                rs.getInt("id"),
                rs.getString("marka"),
                rs.getInt("capacity")
        );

        bus.setFromCityId(rs.getInt("from_city_id"));
        bus.setToCityId(rs.getInt("to_city_id"));
        bus.setDepartureDate(rs.getString("departure_date"));
        bus.setDepartureTime(rs.getString("departure_time"));
        bus.setArrivalTime(rs.getString("arrival_time"));
        bus.setPrice(rs.getInt("price"));

        return bus;
    }
}