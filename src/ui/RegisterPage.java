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

    final String[] generatedCode = {""};
    final long[] expireTime = {0};

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

            if (email.getText().isEmpty()) {
                info.setStyle("-fx-text-fill: red;");
                info.setText("Masukkan email dulu!");
                return;
            }

            // generate 6 digit OTP
            generatedCode[0] = String.valueOf((int)(Math.random() * 900000) + 100000);

            // set expire time (1 menit)
            expireTime[0] = System.currentTimeMillis() + 60000;

            // kirim email
            EmailService.sendOTP(email.getText(), generatedCode[0]);

            info.setStyle("-fx-text-fill: green;");
            info.setText("Kode dikirim ke email!");

            // disable button + warna abu
            sendCodeBtn.setDisable(true);
            sendCodeBtn.setStyle(
                    "-fx-background-color: #ccc;" +
                            "-fx-background-radius: 10;" +
                            "-fx-padding: 10;"
            );

            // countdown
            startCountdown(sendCodeBtn, info);
        });

        // ===== REGISTER LOGIC =====
        registerBtn.setOnAction(e -> {

            if (email.getText().isEmpty() || password.getText().isEmpty()) {
                info.setStyle("-fx-text-fill: red;");
                info.setText("Email dan password harus diisi!");

            } else if (System.currentTimeMillis() > expireTime[0]) {
                info.setStyle("-fx-text-fill: red;");
                info.setText("Kode sudah expired!");
            }
            else if (!codeField.getText().equals(generatedCode[0])) {
                info.setStyle("-fx-text-fill: red;");
                info.setText("Kode salah!");
            }
            else {
                // sukses → hapus kode (1x pakai)
                generatedCode[0] = "";

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

    private void startCountdown(Button btn, Label info) {

        new Thread(() -> {
            try {
                for (int i = 60; i >= 0; i--) {

                    int finalI = i;

                    javafx.application.Platform.runLater(() ->
                            info.setText("Masukkan kode (" + finalI + " detik)")
                    );

                    Thread.sleep(1000);
                }

                javafx.application.Platform.runLater(() -> {
                    btn.setDisable(false);
                    btn.setStyle(
                            "-fx-background-color: #4CAF50;" +
                                    "-fx-text-fill: white;" +
                                    "-fx-background-radius: 10;" +
                                    "-fx-padding: 10;"
                    );
                    info.setText("Kode expired, kirim ulang.");
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}