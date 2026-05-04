package repository;

import model.City;
import ui.DatabaseHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CityRepository {

    // 🔥 GET ALL CITIES
    public List<City> getAll() {

        List<City> list = new ArrayList<>();

        String sql = "SELECT * FROM cities";

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                City city = new City(
                        rs.getInt("id"),
                        rs.getString("name")
                );

                list.add(city);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // 🔥 FIND BY ID
    public City findById(int id) {

        String sql = "SELECT * FROM cities WHERE id = ?";

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new City(
                        rs.getInt("id"),
                        rs.getString("name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}