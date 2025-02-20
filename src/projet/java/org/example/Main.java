package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/StadeInterface.fxml"));
        Parent root = loader.load();

        // Set scene size
        double width = 1000; // Adjust as needed
        double height = 700; // Adjust as needed
        Scene scene = new Scene(root, width, height);

        stage.setScene(scene);
        stage.setTitle("Stade");

        // Center the window
        centerStage(stage, width, height);

        stage.show();
    }

    private void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
