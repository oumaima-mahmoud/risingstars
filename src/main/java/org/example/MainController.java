package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;  // Change this to AnchorPane since FXML root is AnchorPane
import javafx.stage.Stage;
import java.io.IOException;

public class MainController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Charger le fichier FXML (ici admin_dashboard.fxml)
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));

        try {
            // Charger la racine comme AnchorPane
            AnchorPane root = loader.load();  // Use AnchorPane here
            Scene scene = new Scene(root);

            // Configuration du stage
            primaryStage.setTitle("Gestion des utilisateurs");
            primaryStage.setScene(scene);
            primaryStage.show(); // Afficher la fenêtre

        } catch (IOException e) {
            e.printStackTrace(); // Afficher l'erreur si une exception se produit
        }
    }
}
