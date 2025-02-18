package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file for the Admin Reclamation interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/adminReclamation.fxml"));
            AnchorPane root = loader.load();

            // Create a new scene with the loaded layout
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);

            // Set the title for the stage
            primaryStage.setTitle("Admin Reclamation Management");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
