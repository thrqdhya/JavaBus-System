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

        // ===== BACKGROUND IMAGE =====
        Image bgImage = new Image("file:C:/Users/mutia/OneDrive/Downloads/bg.jpg");
        ImageView bg = new ImageView(bgImage);
        bg.setFitWidth(800);
        bg.setFitHeight(600);
        bg.setPreserveRatio(false);

        // ===== NAVBAR (TRANSPARENT) =====
        Label logo = new Label("JavaBus");
        logo.setStyle("-fx-text-fill: white; -fx-font-size: 22px; -fx-font-weight: bold;");

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

        HBox navbar = new HBox(10, logo, spacer, logoutBtn);
        navbar.setPadding(new Insets(15, 25, 15, 25));
        navbar.setStyle("-fx-background-color: transparent;");

        // ===== TITLE =====
        Label title = new Label("Find Your Bus Journey");
        title.setStyle(
                "-fx-font-size: 34px;" +
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

        styleGlassInput(fromField);
        styleGlassInput(toField);
        styleGlassInput(datePicker);
        styleGlassInput(passenger);

        // ===== SEARCH BUTTON =====
        Button searchBtn = new Button("Search");
        searchBtn.setStyle(
                "-fx-background-color: #ff7a00;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 10 20;"
        );

        // ===== SEARCH BOX (GLASS EFFECT) =====
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
                "-fx-background-color: rgba(255,255,255,0.15);" +
                        "-fx-background-radius: 25;" +
                        "-fx-border-color: rgba(255,255,255,0.3);" +
                        "-fx-border-radius: 25;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 20, 0, 0, 10);"
        );

        VBox content = new VBox(30, navbar, title, searchBox);
        content.setAlignment(Pos.TOP_CENTER);
        content.setPadding(new Insets(40));

        StackPane root = new StackPane(bg, content);

        return new VBox(root);
    }

    private void styleGlassInput(Control field) {
        field.setStyle(
                "-fx-background-color: rgba(255,255,255,0.3);" +
                        "-fx-text-fill: black;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: rgba(255,255,255,0.4);" +
                        "-fx-padding: 10;"
        );
        field.setPrefWidth(130);
    }
}