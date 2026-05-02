package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DashboardPage {

    private Main main;

    public DashboardPage(Main main) {
        this.main = main;
    }

    public VBox getView() {

        // ===== NAVBAR =====
        Label logo = new Label("JavaBus");
        logo.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle(
                "-fx-background-color: rgba(255,255,255,0.2);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 20;" +
                        "-fx-padding: 5 15;"
        );
        logoutBtn.setOnAction(e -> main.showLoginPage());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox navbar = new HBox(logo, spacer, logoutBtn);
        navbar.setPadding(new Insets(20));
        navbar.setAlignment(Pos.CENTER_LEFT);

        // ===== TITLE =====
        Label title = new Label("Find Your Bus Journey");
        title.setStyle(
                "-fx-font-size: 36px;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-weight: bold;"
        );

        // ===== INPUT =====
        TextField fromField = new TextField();
        fromField.setPromptText("From");

        TextField toField = new TextField();
        toField.setPromptText("To");

        DatePicker datePicker = new DatePicker();
        Spinner<Integer> passenger = new Spinner<>(1, 10, 1);

        styleInput(fromField);
        styleInput(toField);
        styleInput(datePicker);
        styleInput(passenger);

        // ===== BUTTON =====
        Button searchBtn = new Button("Search");
        searchBtn.setStyle(
                "-fx-background-color: #ff7a00;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 10 20;"
        );

        // ===== SEARCH BOX =====
        HBox searchBox = new HBox(15,
                fromField,
                toField,
                datePicker,
                passenger,
                searchBtn
        );

        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(20));
        searchBox.setMaxWidth(700);
        searchBox.setStyle(
                "-fx-background-color: rgba(255,255,255,0.2);" +
                        "-fx-background-radius: 25;"
        );

        // ===== BACKGROUND =====
        Image bgImage = new Image("file:C:/Users/mutia/OneDrive/Downloads/bg.jpg");

        ImageView bg = new ImageView(bgImage);
        bg.setPreserveRatio(true);
        bg.setFitWidth(1200); // biar gak zoom aneh
        bg.setSmooth(true);

        // ===== OVERLAY =====
        Region overlay = new Region();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.4);");

        // ===== CENTER CONTENT =====
        VBox centerBox = new VBox(25, title, searchBox);
        centerBox.setAlignment(Pos.CENTER);

        VBox content = new VBox();
        content.getChildren().addAll(navbar);

        Region spacerTop = new Region();
        VBox.setVgrow(spacerTop, Priority.ALWAYS);

        Region spacerBottom = new Region();
        VBox.setVgrow(spacerBottom, Priority.ALWAYS);

        content.getChildren().addAll(spacerTop, centerBox, spacerBottom);

        // ===== ROOT =====
        StackPane root = new StackPane();
        root.getChildren().addAll(bg, overlay, content);

        return new VBox(root);
    }

    private void styleInput(Control field) {
        field.setStyle(
                "-fx-background-color: rgba(255,255,255,0.3);" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(255,255,255,0.4);" +
                        "-fx-padding: 10;"
        );
        field.setPrefWidth(140);
    }
}