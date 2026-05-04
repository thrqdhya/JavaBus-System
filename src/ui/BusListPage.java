package ui;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.image.*;

import model.Bus;

import java.util.List;

public class BusListPage {

    private Main main;
    private int fromId;
    private int toId;
    private String fromName;
    private String toName;
    private String date;

    public BusListPage(Main main, int fromId, int toId,
                       String fromName, String toName, String date) {

        this.main = main;
        this.fromId = fromId;
        this.toId = toId;
        this.fromName = fromName;
        this.toName = toName;
        this.date = date;
    }

    public StackPane getView() {

        // ===== BACKGROUND =====
        ImageView bg = new ImageView(
                new Image(getClass().getResource("/bg.png").toExternalForm())
        );
        bg.setFitWidth(1600);
        bg.setPreserveRatio(true);

        Region overlay = new Region();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.55);");

        // ===== HEADER =====
        Button back = new Button("← Back");
        back.getStyleClass().add("back-btn");
        back.setOnAction(e -> main.showDashboardPage());

        Label title = new Label(fromName + " → " + toName);
        title.getStyleClass().add("title");

        Label sub = new Label(date);
        sub.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

        VBox headerText = new VBox(5, title, sub);

        HBox header = new HBox(20, back, headerText);
        header.setAlignment(Pos.CENTER_LEFT);

        // ===== BUS LIST =====
        VBox list = new VBox(20);

        List<Bus> buses = main.getBusService()
                .searchBuses(fromId, toId);

        if (buses.isEmpty()) {

            Label empty = new Label("No buses available 😔");
            empty.setStyle("-fx-text-fill: white; -fx-font-size: 18px;");

            list.getChildren().add(empty);

        } else {
            for (Bus b : buses) {
                list.getChildren().add(createCard(b));
            }
        }

        ScrollPane scroll = new ScrollPane(list);
        scroll.setFitToWidth(true);
        scroll.getStyleClass().add("scroll-clean");

        VBox content = new VBox(30, header, scroll);
        content.setPadding(new Insets(40, 100, 40, 100));

        StackPane root = new StackPane(bg, overlay, content);

        return root;
    }

    // ===== CARD DESIGN =====
    private HBox createCard(Bus b) {

        // LEFT SIDE
        Label name = new Label(b.getMarka());
        name.getStyleClass().add("bus-name");

        Label route = new Label(fromName + " → " + toName);
        route.getStyleClass().add("bus-route");

        Label time = new Label(
                b.getDepartureTime() + "  →  " + b.getArrivalTime()
        );
        time.getStyleClass().add("time-row");

        VBox left = new VBox(8, name, route, time);

        // RIGHT SIDE
        Label price = new Label("₺ " + String.format("%,d", b.getPrice()));
        price.getStyleClass().add("price");

        Button book = new Button("Book Now");
        book.getStyleClass().add("book-btn");

        book.setOnAction(e -> {
            main.showSeatPage(b);
        });

        VBox right = new VBox(10, price, book);
        right.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox card = new HBox(30, left, spacer, right);
        card.getStyleClass().add("bus-card");

        return card;
    }
}