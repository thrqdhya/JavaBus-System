package ui;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    public StackPane getView() {

        // ===== TITLE =====
        Label title = new Label("Payment");
        title.getStyleClass().add("title");

        Label info = new Label("Passenger: " + name);
        Label seatInfo = new Label("Seats: " + seats);

        int pricePerSeat = bus.getPrice();
        int totalPrice = seats.size() * pricePerSeat;

        Label priceInfo = new Label("Price per seat: ₺ " + pricePerSeat);
        Label total = new Label("Total: ₺ " + totalPrice);

        info.setStyle("-fx-text-fill: white;");
        seatInfo.setStyle("-fx-text-fill: white;");
        priceInfo.setStyle("-fx-text-fill: white;");

        total.setStyle("-fx-font-size: 22px; -fx-text-fill: #1a73e8; -fx-font-weight: bold;");

        // ===== PAYMENT METHOD =====
        ToggleGroup group = new ToggleGroup();

        RadioButton card = new RadioButton("Credit Card");
        RadioButton cash = new RadioButton("Pay at Terminal");
        RadioButton qr = new RadioButton("QR Payment");

        card.setToggleGroup(group);
        cash.setToggleGroup(group);
        qr.setToggleGroup(group);

        card.setSelected(true);

        VBox methods = new VBox(10, card, cash, qr);
        methods.setPadding(new Insets(10));

        // ===== BUTTON =====
        Button pay = new Button("Pay Now");
        pay.setStyle("""
        -fx-background-color: linear-gradient(to right, #1a73e8, #0b57d0);
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-background-radius: 25;
        -fx-padding: 12 30;
    """);

        pay.setOnAction(e -> {

            if (group.getSelectedToggle() == null) {
                System.out.println("Select payment method!");
                return;
            }

            pay.setDisable(true);

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

        // ===== CARD =====
        VBox cardBox = new VBox(15,
                title,
                info,
                seatInfo,
                priceInfo,
                total,
                methods,
                pay
        );

        cardBox.setAlignment(Pos.CENTER);
        cardBox.setPadding(new Insets(30));
        cardBox.setMaxWidth(400);

        cardBox.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 30, 0, 0, 10);
    """);

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

        StackPane wrapper = new StackPane(cardBox);
        wrapper.setAlignment(Pos.CENTER);

        root.getChildren().addAll(bg, overlay, wrapper);

        return root;
    }
}