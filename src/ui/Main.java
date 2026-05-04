package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import repository.BusRepository;
import repository.CityRepository;
import service.BusService;
import service.CityService;
import model.Bus;
import java.util.Set;

public class Main extends Application {

    private Stage stage;

    // 🔥 SERVICE (GLOBAL)
    private BusService busService = new BusService(new BusRepository());
    private CityService cityService = new CityService(new CityRepository());

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        DatabaseHelper.initializeDatabase();

        showLoginPage();

        stage.setTitle("JavaBus");
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.show();
    }

    // =========================
    // 🔥 SERVICE GETTER
    // =========================
    public BusService getBusService() {
        return busService;
    }

    public CityService getCityService() {
        return cityService;
    }

    // =========================
    // 🔐 LOGIN PAGE
    // =========================
    public void showLoginPage() {
        LoginPage loginPage = new LoginPage(this);

        Scene scene = new Scene(loginPage.getView(), 500, 600);
        scene.getStylesheets().add(getClass().getResource("/traveloka.css").toExternalForm());

        stage.setScene(scene);
    }

    // =========================
    // 🏠 DASHBOARD
    // =========================
    public void showDashboardPage() {
        DashboardPage dashboard = new DashboardPage(this);

        Scene scene = new Scene(dashboard.getView(), 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/traveloka.css").toExternalForm());

        stage.setScene(scene);
    }

    // =========================
    // 🚌 BUS LIST (SEARCH RESULT)
    // =========================
    public void showBusListPage(int fromId, int toId,
                                String fromName, String toName, String date) {

        BusListPage page = new BusListPage(this, fromId, toId, fromName, toName, date);

        Scene scene = new Scene(page.getView(), 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/traveloka.css").toExternalForm());

        stage.setScene(scene);
    }

    public void showSeatPage(Bus bus) {
        SeatPage page = new SeatPage(this, bus);

        Scene scene = new Scene(page.getView(), 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/traveloka.css").toExternalForm());

        stage.setScene(scene);
    }

    public void showPassengerPage(Bus bus, Set<String> seats) {
        PassengerPage page = new PassengerPage(this, bus, seats);

        Scene scene = new Scene(page.getView(), 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/traveloka.css").toExternalForm());

        stage.setScene(scene);
    }

    public void showPaymentPage(Bus bus, Set<String> seats, String name) {
        PaymentPage page = new PaymentPage(this, bus, seats, name);

        Scene scene = new Scene(page.getView(), 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/traveloka.css").toExternalForm());

        stage.setScene(scene);
    }

    public void showSuccessPage(int busId, String name, Set<String> seats, int total) {
        SuccessPage page = new SuccessPage(this, busId, name, seats, total);
        stage.setScene(new Scene(page.getView(), 1000, 700));
    }

    // =========================
    // 📝 REGISTER
    // =========================
    public void showRegisterPage() {
        RegisterPage register = new RegisterPage(this);

        Scene scene = new Scene(register.getView(), 500, 600);
        scene.getStylesheets().add(getClass().getResource("/traveloka.css").toExternalForm());

        stage.setScene(scene);
    }

    // =========================
    public static void main(String[] args) {
        launch();
    }
}