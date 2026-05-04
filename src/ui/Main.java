package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.DatabaseHelper;

public class Main extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        DatabaseHelper.initializeDatabase();
        DatabaseHelper.insertTerminals();

        showLoginPage();

        stage.setTitle("Attendance System");
        stage.show();
    }

    public void showLoginPage() {
        LoginPage loginPage = new LoginPage(this);
        Scene scene = new Scene(loginPage.getView(), 500, 600);
        stage.setScene(scene);
    }

    public void showDashboard() {
        DashboardPage dashboard = new DashboardPage(this);
        Scene scene = new Scene(dashboard.getView(), 600, 400);
        stage.setScene(scene);
    }

    public void showRegisterPage() {
        RegisterPage register = new RegisterPage(this);
        Scene scene = new Scene(register.getView(), 500, 600);
        stage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}