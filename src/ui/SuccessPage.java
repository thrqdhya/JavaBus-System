package ui;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.swing.plaf.synth.Region;
import javax.swing.text.html.ImageView;

import model.Bus;

public class SuccessPage {

    private Main main;
    private Bus bus;
    private String name;
    private Set<String> seats;
    private int total;

    public SuccessPage(Main main, Bus bus, String name, Set<String> seats, int total) {
        this.main = main;
        this.bus = bus;
        this.name = name;
        this.seats = seats;
        this.total = total;
    }

    public StackPane getView() {

        // ===== CARD =====
        VBox ticket = new VBox();
        ticket.getStyleClass().add("ticket-card");
        ticket.setMaxWidth(450);

        // ===== HEADER =====
        VBox header = new VBox(5);
        header.getStyleClass().add("ticket-header");

        Label title = new Label("E-Ticket");
        title.getStyleClass().add("ticket-title");

        Label bookingId = new Label("Booking ID: " + generateBookingId());
        bookingId.getStyleClass().add("ticket-id");

        header.getChildren().addAll(title, bookingId);

        // ===== BODY =====
        VBox body = new VBox(15);
        body.getStyleClass().add("ticket-body");

        Label route = new Label(
        bus.getFromCityId() + " → " + bus.getToCityId()
);
        route.getStyleClass().add("ticket-route");

        Label time = new Label(
        bus.getDepartureTime() + " → " + bus.getArrivalTime()
);
        time.getStyleClass().add("ticket-time");

        VBox passengerBox = new VBox(5);
        Label pLabel = new Label("PASSENGER");
        pLabel.getStyleClass().add("ticket-label");
        Label pValue = new Label(name);
        pValue.getStyleClass().add("ticket-value");
        passengerBox.getChildren().addAll(pLabel, pValue);

        VBox seatBox = new VBox(5);
        Label sLabel = new Label("SEAT");
        sLabel.getStyleClass().add("ticket-label");
        Label sValue = new Label(String.join(", ", seats));
        sValue.getStyleClass().add("ticket-value");
        seatBox.getChildren().addAll(sLabel, sValue);

        Label totalLabel = new Label("₺ " + total);
        totalLabel.getStyleClass().add("ticket-price");

        Label qr = new Label("▇ ▓ ▇ ░ ▇ ▓");
        qr.getStyleClass().add("ticket-qr");

        Button done = new Button("Back to Home");
        done.getStyleClass().add("ticket-btn");
        done.setOnAction(e -> main.showDashboardPage());

        Separator divider1 = new Separator();
        divider1.getStyleClass().add("ticket-divider");

        Separator divider2 = new Separator();
        divider2.getStyleClass().add("ticket-divider");

        body.getChildren().addAll(
                route,
                time,
                divider1,
                passengerBox,
                seatBox,
                divider2,
                totalLabel,
                qr,
                done
        );

        ticket.getChildren().addAll(header, body);

        // ===== BACKGROUND =====
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

        // 🔥 INI YANG KEMARIN ERROR → sekarang bener
        StackPane wrapper = new StackPane(ticket);
        wrapper.setAlignment(Pos.CENTER);

        root.getChildren().addAll(bg, overlay, wrapper);

        return root;
    }

    private String generateBookingId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
