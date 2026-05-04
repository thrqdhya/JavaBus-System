package ui;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import model.Bus;
import service.BookingService;

import java.util.Set;

public class PaymentPage {

    private Main main;
    private Bus bus;
    private Set<String> seats;
    private String name;

    public PaymentPage(Main main, Bus bus, Set<String> seats, String name) {
        this.main = main;
        this.bus = bus;
        this.seats = seats;
        this.name = name;
    }

    public VBox getView() {

        Label title = new Label("Payment");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Label info = new Label("Passenger: " + name);
        Label seatInfo = new Label("Seats: " + seats);

        int pricePerSeat = bus.getPrice();
        int totalPrice = seats.size() * pricePerSeat;

        Label priceInfo = new Label("Price per seat: ₺ " + pricePerSeat);
        Label total = new Label("Total: ₺ " + totalPrice);

        total.setStyle("-fx-font-size: 20px; -fx-text-fill: #ff5a1f;");

        ToggleGroup group = new ToggleGroup();

        RadioButton card = new RadioButton("Credit Card");
        RadioButton cash = new RadioButton("Pay at Terminal");
        RadioButton qr = new RadioButton("QR Payment");

        card.setToggleGroup(group);
        cash.setToggleGroup(group);
        qr.setToggleGroup(group);

        card.setSelected(true);

        VBox methods = new VBox(10, card, cash, qr);

        Button pay = new Button("Pay Now");
        pay.setStyle("-fx-background-color: #ff5a1f; -fx-text-fill: white;");

        pay.setOnAction(e -> {

            if (group.getSelectedToggle() == null) {
                System.out.println("Select payment method!");
                return;
            }

            pay.setDisable(true); // 🔥 anti double click

            String method = ((RadioButton) group.getSelectedToggle()).getText();
            System.out.println("Payment success via: " + method);

            BookingService bookingService = new BookingService();

            bookingService.createBookingWithSeats(
                    bus.getId(),
                    name,
                    seats,
                    totalPrice
            );

            main.showSuccessPage(bus.getId(), name, seats, totalPrice);
        });

        VBox root = new VBox(20,
                title,
                info,
                seatInfo,
                priceInfo,
                total,
                methods,
                pay
        );

        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        return root;
    }
}