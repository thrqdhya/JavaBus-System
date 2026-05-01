package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DashboardPage {

    private Main main;

    public DashboardPage(Main main) {
        this.main = main;
    }

    public VBox getView() {

        Label welcome = new Label("Welcome to Dashboard 🎉");
        welcome.setStyle("-fx-font-size: 20px;");

        Button logoutBtn = new Button("Logout");

        logoutBtn.setOnAction(e -> main.showLoginPage());

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(welcome, logoutBtn);

        return layout;
    }
}
