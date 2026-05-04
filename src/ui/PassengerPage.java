package ui;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

        // ===== TITLE =====
        Label title = new Label("Passenger Details");
        title.getStyleClass().add("title");

        // ===== INFO =====
        Label routeInfo = new Label("Route: " + bus.getMarka());
        Label seatInfo = new Label("Seats: " + seats);
        Label priceInfo = new Label("Price per seat: ₺ " + bus.getPrice());

        routeInfo.setStyle("-fx-text-fill: white;");
        seatInfo.setStyle("-fx-text-fill: white;");
        priceInfo.setStyle("-fx-text-fill: white;");

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
        form.setPadding(new Insets(25));
        form.setMaxWidth(350);

        form.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 30, 0, 0, 10);
    """);

        // ===== BUTTON (BIRU) =====
        Button next = new Button("Continue to Payment");
        next.setStyle("""
        -fx-background-color: linear-gradient(to right, #1a73e8, #0b57d0);
        -fx-text-fill: white;
        -fx-font-size: 14px;
        -fx-background-radius: 25;
        -fx-padding: 12 30;
    """);

        next.setOnAction(e -> {
            if (name.getText().isEmpty()) {
                System.out.println("Name required!");
                return;
            }

            main.showPaymentPage(bus, seats, name.getText());
        });

        VBox content = new VBox(20,
                title,
                routeInfo,
                seatInfo,
                priceInfo,
                form,
                next
        );

        content.setAlignment(Pos.CENTER);

        // ===== BACKGROUND (SAMA KAYAK DASHBOARD) =====
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

        root.getChildren().addAll(bg, overlay, content);

        return root;
    }
}