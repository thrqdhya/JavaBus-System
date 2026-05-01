package ui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ui.DatabaseHelper;

public class LoginPage {

    private Main main;

    public LoginPage(Main main) {
        this.main = main;
    }

    public VBox getView() {

        // ===== TITLE =====
        Label title = new Label("Welcome Back");
        title.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #333;"
        );

        Label subtitle = new Label("Login to your account");
        subtitle.setStyle("-fx-text-fill: #777;");

        // ===== ERROR TEXT =====
        Label errorText = new Label();
        errorText.setStyle("-fx-text-fill: red;");

        // ===== EMAIL =====
        TextField email = new TextField();
        email.setPromptText("Email");
        email.setMaxWidth(260);
        email.setStyle(
                "-fx-padding: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-color: #ddd;"
        );

        // ===== PASSWORD =====
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        password.setMaxWidth(260);
        password.setStyle(
                "-fx-padding: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-color: #ddd;"
        );

        // ===== LOGIN BUTTON =====
        Button loginBtn = new Button("Login");
        loginBtn.setMaxWidth(260);
        loginBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #00c6ff, #0072ff);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10;"
        );

        // Hover effect
        loginBtn.setOnMouseEntered(e ->
                loginBtn.setStyle(
                        "-fx-background-color: linear-gradient(to right, #0072ff, #00c6ff);" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 10;" +
                                "-fx-padding: 10;"
                )
        );

        loginBtn.setOnMouseExited(e ->
                loginBtn.setStyle(
                        "-fx-background-color: linear-gradient(to right, #00c6ff, #0072ff);" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 10;" +
                                "-fx-padding: 10;"
                )
        );

        // ===== LOGIN LOGIC =====
        loginBtn.setOnAction(e -> {

            if (email.getText().isEmpty() || password.getText().isEmpty()) {
                errorText.setText("Email dan password harus diisi!");

            } else {
                boolean valid = DatabaseHelper.loginUser(
                        email.getText(),
                        password.getText()
                );

                if (!valid) {
                    errorText.setText("Email atau password salah!");
                } else {
                    errorText.setText("");
                    main.showDashboard();
                }
            }
        });

        // ===== DIVIDER =====
        Label orLabel = new Label("or continue with");
        orLabel.setStyle("-fx-text-fill: #999;");

        // ===== GOOGLE BUTTON =====
        Button googleBtn = new Button("🔵 Continue with Google");
        googleBtn.setMaxWidth(260);
        googleBtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #ddd;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-padding: 10;"
        );

        // ===== APPLE BUTTON =====
        Button appleBtn = new Button(" Continue with Apple");
        appleBtn.setMaxWidth(260);
        appleBtn.setStyle(
                "-fx-background-color: black;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10;"
        );

        // ===== CARD =====
        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-padding: 30;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 5);"
        );

        Button registerBtn = new Button("Create Account");

        registerBtn.setMaxWidth(260);
        registerBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #0072ff;" +
                        "-fx-underline: true;"
        );

// pindah ke halaman register
        registerBtn.setOnAction(e -> main.showRegisterPage());

        card.getChildren().addAll(
                title,
                subtitle,
                email,
                password,
                loginBtn,
                errorText,
                orLabel,
                googleBtn,
                appleBtn,
                registerBtn
        );

        // ===== ROOT (GRADIENT BACKGROUND) =====
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #00c6ff, #0072ff);"
        );

        root.getChildren().add(card);

        return root;
    }
}