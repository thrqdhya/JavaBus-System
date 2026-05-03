package repository;

import model.City;
import ui.DatabaseHelper;

import java.sql.*;
import java.util.*;

public class CityRepository {

    public List<City> getAllCities() {
        List<City> list = new ArrayList<>();

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM cities")) {

            while (rs.next()) {
                list.add(new City(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
