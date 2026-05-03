package repository;

import model.Terminal;
import ui.DatabaseHelper;

import java.sql.*;
import java.util.*;

public class TerminalRepository {

    public List<Terminal> getByCityId(int cityId) {
        List<Terminal> list = new ArrayList<>();

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM terminals WHERE city_id = ?")) {

            stmt.setInt(1, cityId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Terminal(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("city_id")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
