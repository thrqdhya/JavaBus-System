package ui;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import ui.DatabaseHelper;

public class RegisterPage {

    private Main main;

    // Simulasi database
    public static Map<String, String> users = new HashMap<>();

    public RegisterPage(Main main) {
        this.main = main;
    }

    public VBox getView() {

        // ===== TITLE =====
        Label title = new Label("Create Account");
        title.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #333;"
        );

        Label subtitle = new Label("Register with your email");
        subtitle.setStyle("-fx-text-fill: #777;");

        // ===== INFO / ERROR =====
        Label info = new Label();
        info.setStyle("-fx-text-fill: red;");

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

        // ===== CODE FIELD =====
        TextField codeField = new TextField();
        codeField.setPromptText("Verification Code");
        codeField.setMaxWidth(260);
        codeField.setStyle(
                "-fx-padding: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-radius: 10;" +
                        "-fx-border-color: #ddd;"
        );

        // ===== BUTTON SEND CODE =====
        Button sendCodeBtn = new Button("Send Code");
        sendCodeBtn.setMaxWidth(260);
        sendCodeBtn.setStyle(
                "-fx-background-color: #e0e0e0;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10;"
        );

        // ===== BUTTON REGISTER =====
        Button registerBtn = new Button("Register");
        registerBtn.setMaxWidth(260);
        registerBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #00c6ff, #0072ff);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10;"
        );

        // Hover effect
        registerBtn.setOnMouseEntered(e ->
                registerBtn.setStyle(
                        "-fx-background-color: linear-gradient(to right, #0072ff, #00c6ff);" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 10;" +
                                "-fx-padding: 10;"
                )
        );

        registerBtn.setOnMouseExited(e ->
                registerBtn.setStyle(
                        "-fx-background-color: linear-gradient(to right, #00c6ff, #0072ff);" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 10;" +
                                "-fx-padding: 10;"
                )
        );

        // ===== GENERATE CODE =====
        final String[] generatedCode = {""};

        sendCodeBtn.setOnAction(e -> {
            generatedCode[0] = String.valueOf(new Random().nextInt(9000) + 1000);
            info.setStyle("-fx-text-fill: green;");
            info.setText("Verification code: " + generatedCode[0]);
        });

        // ===== REGISTER LOGIC =====
        registerBtn.setOnAction(e -> {

            if (email.getText().isEmpty() || password.getText().isEmpty()) {
                info.setStyle("-fx-text-fill: red;");
                info.setText("Email dan password harus diisi!");

            } else if (!codeField.getText().equals(generatedCode[0])) {
                info.setStyle("-fx-text-fill: red;");
                info.setText("Kode verifikasi salah!");

            } else {
                boolean success = DatabaseHelper.registerUser(
                        email.getText(),
                        password.getText()
                );

                if (success) {
                    info.setStyle("-fx-text-fill: green;");
                    info.setText("Registrasi berhasil!");
                } else {
                    info.setStyle("-fx-text-fill: red;");
                    info.setText("Email sudah digunakan!");
                }
            }
        });

        // ===== BACK BUTTON =====
        Button backBtn = new Button("Back to Login");
        backBtn.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-text-fill: #0072ff;" +
                        "-fx-underline: true;"
        );

        backBtn.setOnAction(e -> main.showLoginPage());

        // ===== CARD =====
        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER);
        card.setStyle(
                "-fx-background-color: white;" +
                        "-fx-padding: 30;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 5);"
        );

        card.getChildren().addAll(
                title,
                subtitle,
                email,
                password,
                sendCodeBtn,
                codeField,
                registerBtn,
                info,
                backBtn
        );

        // ===== ROOT (GRADIENT) =====
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #00c6ff, #0072ff);"
        );

        root.getChildren().add(card);

        return root;
    }
}