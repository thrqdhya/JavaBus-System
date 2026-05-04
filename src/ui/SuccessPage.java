package ui;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

public class SuccessPage {

    private Main main;
    private int busId;
    private String name;
    private Set<String> seats;
    private int total;

    public SuccessPage(Main main, int busId, String name, Set<String> seats, int total) {
        this.main = main;
        this.busId = busId;
        this.name = name;
        this.seats = seats;
        this.total = total;
    }

    public StackPane getView() {

        // ===== BACKGROUND =====
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #f3f4f6;");

        // ===== CARD =====
        VBox ticket = new VBox(20);
        ticket.setPadding(new Insets(25));
        ticket.setMaxWidth(500);

        ticket.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 20;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 25, 0, 0, 10);
        """);

        // ===== HEADER =====
        Label title = new Label("E-Ticket");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        Label bookingId = new Label("Booking ID: " + generateBookingId());
        bookingId.setStyle("-fx-text-fill: #6b7280;");

        // ===== ROUTE =====
        Label route = new Label("Bartin → Adana"); // nanti bisa dari DB
        route.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label time = new Label("Departure: 08:00   Arrival: 18:00");
        time.setStyle("-fx-text-fill: #6b7280;");

        // ===== PASSENGER =====
        Label passenger = new Label("Passenger: " + name);
        Label seatInfo = new Label("Seats: " + seats);

        // ===== PRICE =====
        Label totalLabel = new Label("Total Paid: ₺ " + total);
        totalLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #ff5a1f; -fx-font-weight: bold;");

        // ===== QR / CODE SIMULASI =====
        Label qr = new Label("█ ▓ ░ █ ▓ ░ █");
        qr.setStyle("-fx-font-size: 24px;");

        // ===== BUTTON =====
        Button done = new Button("Back to Home");
        done.setStyle("-fx-background-color: #ff5a1f; -fx-text-fill: white;");
        done.setOnAction(e -> main.showDashboardPage());

        // ===== LAYOUT =====
        ticket.getChildren().addAll(
                title,
                bookingId,
                new Separator(),
                route,
                time,
                new Separator(),
                passenger,
                seatInfo,
                new Separator(),
                totalLabel,
                qr,
                done
        );

        root.getChildren().add(ticket);

        return root;
    }

    private String generateBookingId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
