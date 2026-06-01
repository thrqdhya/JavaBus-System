package ui;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import model.Bus;

import repository.SeatRepository;
import service.SeatService;

import java.util.*;

public class SeatPage {

    private Main main;
    private int busId;

    // 🔥 FIX: pakai 1 sistem saja (String seat)
    private Set<String> selectedSeat = new HashSet<>();

    // 🔥 FIX: jadikan global
    private SeatService seatService = new SeatService();

    private Bus bus;

    public SeatPage(Main main, Bus bus) {
        this.main = main;
        this.bus = bus;
        this.busId = bus.getId(); // 🔥 INI YANG HILANG

        seatService.generateSeatsIfNotExists(bus.getId(), bus.getKoltukSayisi());
    }

    public StackPane getView() {

        // 🔥 generate dulu
        seatService.generateSeatsIfNotExists(busId, bus.getKoltukSayisi());

        // 🔥 ambil data SEKALI aja
        List<SeatRepository.SeatRow> seats = seatService.getSeats(busId);

        // ===== BACKGROUND =====
        ImageView bg = new ImageView(
                new Image(getClass().getResource("/bg.png").toExternalForm())
        );
        bg.setFitWidth(1600);
        bg.setPreserveRatio(true);

        Region overlay = new Region();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.6);");

        // ===== HEADER =====
        Button back = new Button("← Back");
        back.setOnAction(e -> main.showDashboardPage());

        Label title = new Label("Select Your Seat");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 36px; -fx-font-weight: bold;");

        VBox header = new VBox(10, back, title);

        // ===== SEAT GRID =====
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        Map<Character, List<SeatRepository.SeatRow>> rows = new TreeMap<>();

        for (SeatRepository.SeatRow s : seats) {
            char row = s.seatNumber.charAt(0);
            rows.computeIfAbsent(row, k -> new ArrayList<>()).add(s);
        }

        int rowIndex = 0;

        for (List<SeatRepository.SeatRow> rowSeats : rows.values()) {

            int colIndex = 0;

            for (SeatRepository.SeatRow seat : rowSeats) {

                Button btn = new Button(seat.seatNumber);
                btn.setPrefSize(50, 50);

                if (seat.isBooked) {
                    btn.setStyle("-fx-background-color: #9ca3af; -fx-text-fill: white;");
                    btn.setDisable(true);
                } else {
                    btn.setStyle("-fx-background-color: #e5e7eb;");
                }

                btn.setOnAction(e -> {

                    if (selectedSeat.contains(seat.seatNumber)) {
                        selectedSeat.remove(seat.seatNumber);
                        btn.setStyle("-fx-background-color: #e5e7eb;");
                    } else {
                        selectedSeat.add(seat.seatNumber);
                        btn.setStyle("-fx-background-color: #22c55e; -fx-text-fill: white;");
                    }

                });

                grid.add(btn, colIndex, rowIndex);
                colIndex++;

                // aisle tengah
                if (colIndex == 2) colIndex++;
            }

            rowIndex++;
        }

        // ===== CONFIRM BUTTON =====
        Button confirm = new Button("Confirm Booking");
        confirm.setStyle("-fx-background-color: #ff5a1f; -fx-text-fill: white;");

        confirm.setOnAction(e -> {

            if (selectedSeat.isEmpty()) {
                System.out.println("Select at least 1 seat!");
                return;
            }

            // 🔥 convert seat name → id
            List<SeatRepository.SeatRow> allSeats = seatService.getSeats(busId);
            List<Integer> ids = new ArrayList<>();

            for (SeatRepository.SeatRow s : allSeats) {
                if (selectedSeat.contains(s.seatNumber)) {
                    ids.add(s.id);
                }
            }


            System.out.println("Booked seats: " + selectedSeat);

            main.showPassengerPage(bus, selectedSeat);
        });

        VBox content = new VBox(30, header, grid, confirm);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(40));

        return new StackPane(bg, overlay, content);
    }
}