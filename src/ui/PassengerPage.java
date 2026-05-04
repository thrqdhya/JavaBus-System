package ui;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import model.Bus;
import java.util.Set;

public class PassengerPage {

    private Main main;
    private Bus bus; // ✅ FIX
    private Set<String> seats;

    public PassengerPage(Main main, Bus bus, Set<String> seats) {
        this.main = main;
        this.bus = bus;
        this.seats = seats;
    }

    public StackPane getView() {

        Label title = new Label("Passenger Details");
        title.setStyle("-fx-font-size: 28px; -fx-text-fill: #111827; -fx-font-weight: bold;");

        // 🔥 tampilkan info booking
        Label routeInfo = new Label("Route: " + bus.getMarka());
        Label seatInfo = new Label("Seats: " + seats);
        Label priceInfo = new Label("Price per seat: ₺ " + bus.getPrice());

        routeInfo.setStyle("-fx-text-fill: #6b7280;");
        seatInfo.setStyle("-fx-text-fill: #6b7280;");
        priceInfo.setStyle("-fx-text-fill: #6b7280;");

        // ===== FORM =====
        TextField name = new TextField();
        name.setPromptText("Full Name");

        TextField email = new TextField();
        email.setPromptText("Email");

        TextField phone = new TextField();
        phone.setPromptText("Phone Number");

        TextField idCard = new TextField();
        idCard.setPromptText("ID / IKAMET");

        VBox form = new VBox(15, name, email, phone, idCard);

        form.setPadding(new Insets(20));
        form.setStyle("""
            -fx-background-color: white;
            -fx-background-radius: 20;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 5);
        """);

        // ===== BUTTON =====
        Button next = new Button("Continue to Payment");
        next.setStyle("""
            -fx-background-color: linear-gradient(to right, #ff7a00, #ff3d00);
            -fx-text-fill: white;
            -fx-font-size: 14px;
            -fx-background-radius: 20;
            -fx-padding: 10 25;
        """);

        next.setOnAction(e -> {

            if (name.getText().isEmpty()) {
                System.out.println("Name required!");
                return;
            }

            // 🔥 kirim semua data ke payment
            main.showPaymentPage(bus, seats, name.getText());
        });

        VBox container = new VBox(20,
                title,
                routeInfo,
                seatInfo,
                priceInfo,
                form,
                next
        );

        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(40));
        container.setMaxWidth(420);

        StackPane root = new StackPane(container);
        root.setStyle("-fx-background-color: #f3f4f6;");

        return root;
    }
}