package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/OffreInterface.fxml"));

        // Create a scene with the loaded FXML content
        Scene scene = new Scene(root);

        // Set the title of the stage
        primaryStage.setTitle("Gestion des Offres");

        // Set the scene to the stage
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args Command-line arguments (not used here).
     */
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}