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

    public DashboardPage(Main main) {
        this.main = main;
        this.locations = loadLocations();
    }

    public VBox getView() {

        StackPane root = new StackPane();

        // ===== BACKGROUND =====
        ImageView bg = new ImageView(
                new Image(getClass().getResource("/bg.png").toExternalForm())
        );

        bg.setFitWidth(1600);
        bg.setPreserveRatio(true);

        Region overlay = new Region();
        overlay.setStyle(
                "-fx-background-color: linear-gradient(to bottom, rgba(0,0,0,0.6), rgba(0,0,0,0.3));"
        );

        // ===== NAVBAR =====
        Label logo = new Label("JavaBus");
        logo.getStyleClass().add("logo");

        Button logout = new Button("Logout");
        logout.getStyleClass().add("logout-btn");
        logout.setOnAction(e -> main.showLoginPage());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox navbar = new HBox(logo, spacer, logout);
        navbar.getStyleClass().add("navbar");

        // ===== TITLE =====
        Label title = new Label("Find Your Bus Journey");
        title.getStyleClass().add("title");

        // ===== INPUT =====
        ComboBox<String> from = createCombo();
        ComboBox<String> to = createCombo();
        from.setPromptText("Choose departure city");
        to.setPromptText("Choose destination city");

        DatePicker date = new DatePicker();
        date.setPromptText("Select date");
        date.setShowWeekNumbers(false);

        HBox passenger = createPassenger();

        // ===== SEARCH CARD =====
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

        card.setAlignment(Pos.CENTER);

        card.getStyleClass().add("search-card");
        card.setAlignment(Pos.CENTER_LEFT);

        VBox wrapper = new VBox(card);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPadding(new Insets(0, 120, 0, 120)); // 🔥 kasih margin kiri kanan
        wrapper.setTranslateY(60);

        card.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(card, Priority.ALWAYS);

        VBox content = new VBox(30, navbar, title, wrapper);
        content.setPadding(new Insets(100, 80, 0, 80));

        root.getChildren().addAll(bg, overlay, content);

        VBox scene = new VBox(root);
        scene.getStylesheets().add(getClass().getResource("/traveloka.css").toExternalForm());

        return scene;
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
    private VBox field(String labelText, Node input) {

        Label label = new Label(labelText);
        label.getStyleClass().add("field-label");

        VBox box = new VBox(5, label, input);
        box.setMinWidth(180);


        return box;
    }

    private HBox modernField(String labelText, Node input, String iconPath) {

        ImageView icon = new ImageView(
                new Image(getClass().getResource(iconPath).toExternalForm())
        );
        icon.setFitWidth(30);
        icon.setFitHeight(30);

        Label label = new Label(labelText);
        label.getStyleClass().add("field-label");

        VBox text = new VBox(8, label, input);
        text.setPadding(new Insets(2, 0, 2, 0));

        HBox box = new HBox(14, icon, text);
        box.setPadding(new Insets(5, 10, 5, 10));
        box.setAlignment(Pos.BOTTOM_LEFT);
        box.setMinWidth(180);
        box.setPadding(new Insets(8, 15, 8, 15));

        text.setPrefWidth(200);

        return box;
    }

    // =========================
    private Region divider() {
        Region d = new Region();
        d.getStyleClass().add("divider");
        return d;
    }

    // =========================
    private Button searchButton() {

        Button btn = new Button("Search");

        btn.getStyleClass().add("search-btn");

        // 🔥 ukuran tombol biar kelihatan premium
        btn.setPrefHeight(55);
        btn.setMinWidth(140);

        return btn;
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