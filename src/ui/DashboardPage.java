package ui;

import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.Node;

import java.sql.*;
import java.util.*;

public class DashboardPage {

    private Main main;
    private List<String> locations;

    private ComboBox<String> from;
    private ComboBox<String> to;
    private DatePicker date;

    public DashboardPage(Main main) {
        this.main = main;
        this.locations = loadLocations();
    }

    public VBox getView() {

        StackPane root = new StackPane();

        ImageView bg = new ImageView(
                new Image(getClass().getResource("/bg.png").toExternalForm())
        );
        bg.setFitWidth(1600);
        bg.setPreserveRatio(true);

        Region overlay = new Region();
        overlay.setStyle(
                "-fx-background-color: linear-gradient(to bottom, rgba(0,0,0,0.6), rgba(0,0,0,0.3));"
        );

        Label logo = new Label("JavaBus");
        logo.getStyleClass().add("logo");

        Button logout = new Button("Logout");
        logout.getStyleClass().add("logout-btn");
        logout.setOnAction(e -> main.showLoginPage());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox navbar = new HBox(logo, spacer, logout);
        navbar.getStyleClass().add("navbar");

        Label title = new Label("Find Your Bus Journey");
        title.getStyleClass().add("title");

        // 🔥 INPUT
        from = createCombo();
        to = createCombo();

        from.setPromptText("Choose departure city");
        to.setPromptText("Choose destination city");

        date = new DatePicker();
        date.setPromptText("Select date");

        HBox passenger = createPassenger();

        HBox card = new HBox(
                modernField("From", from, "/icons/bus.png"),
                divider(),
                modernField("To", to, "/icons/bus.png"),
                divider(),
                modernField("Departure", date, "/icons/kalender.png"),
                divider(),
                modernField("Passengers", passenger, "/icons/passangers.png"),
                searchButton()
        );

        card.getStyleClass().add("search-card");

        VBox wrapper = new VBox(card);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPadding(new Insets(0, 120, 0, 120));
        wrapper.setTranslateY(60);

        VBox content = new VBox(30, navbar, title, wrapper);
        content.setPadding(new Insets(100, 80, 0, 80));

        root.getChildren().addAll(bg, overlay, content);

        VBox scene = new VBox(root);
        scene.getStylesheets().add(getClass().getResource("/traveloka.css").toExternalForm());

        return scene;
    }

    // =========================
    private Button searchButton() {

        Button btn = new Button("Search");
        btn.getStyleClass().add("search-btn");

        btn.setPrefHeight(55);
        btn.setMinWidth(140);

        btn.setOnAction(e -> handleSearch());

        return btn;
    }

    // =========================
    private void handleSearch() {

        String fromCity = from.getValue();
        String toCity = to.getValue();
        String selectedDate = date.getValue() != null ? date.getValue().toString() : null;

        if (fromCity == null || toCity == null || selectedDate == null) {
            System.out.println("Please fill all fields!");
            return;
        }

        int fromId = getCityId(fromCity);
        int toId = getCityId(toCity);

        if (fromId == -1 || toId == -1) {
            System.out.println("City not found!");
            return;
        }

        // 🔥 PINDAH HALAMAN (INI YANG PENTING)
        main.showBusListPage(fromId, toId, fromCity, toCity, selectedDate);
    }

    // =========================
    private int getCityId(String name) {

        try (Connection conn = DatabaseHelper.connect();
             PreparedStatement ps = conn.prepareStatement("SELECT id FROM cities WHERE name = ?")) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // =========================
    private ComboBox<String> createCombo() {

        ComboBox<String> box = new ComboBox<>();
        box.setEditable(true);

        box.setItems(FXCollections.observableArrayList(locations));

        box.getEditor().textProperty().addListener((obs, old, val) -> {

            List<String> filtered = new ArrayList<>();

            for (String item : locations) {
                if (item.toLowerCase().contains(val.toLowerCase())) {
                    filtered.add(item);
                }
            }

            box.setItems(FXCollections.observableArrayList(filtered));
            box.show();
        });

        return box;
    }

    // =========================
    private HBox modernField(String labelText, Node input, String iconPath) {

        ImageView icon = new ImageView(
                new Image(getClass().getResource(iconPath).toExternalForm())
        );
        icon.setFitWidth(30);
        icon.setFitHeight(30);

        Label label = new Label(labelText);
        label.getStyleClass().add("field-label");

        VBox text = new VBox(8, label, input);

        return new HBox(14, icon, text);
    }

    // =========================
    private Region divider() {
        Region d = new Region();
        d.getStyleClass().add("divider");
        return d;
    }

    // =========================
    private HBox createPassenger() {

        Label count = new Label("1");

        Button minus = new Button("-");
        Button plus = new Button("+");

        minus.getStyleClass().add("passenger-btn");
        plus.getStyleClass().add("passenger-btn");

        minus.setOnAction(e -> {
            int v = Integer.parseInt(count.getText());
            if (v > 1) count.setText(String.valueOf(v - 1));
        });

        plus.setOnAction(e -> {
            int v = Integer.parseInt(count.getText());
            count.setText(String.valueOf(v + 1));
        });

        return new HBox(8, minus, count, plus);
    }

    // =========================
    private List<String> loadLocations() {

        List<String> list = new ArrayList<>();

        try (Connection conn = DatabaseHelper.connect();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT name FROM cities");

            while (rs.next()) {
                list.add(rs.getString("name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}