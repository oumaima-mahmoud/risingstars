package tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {

    // Method to change scenes
    public static void switchScene(Stage stage, String fxmlPath) {
        // Ensure the correct relative path for the FXML file (no leading slash)
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));  // No leading slash
        try {
            // If the file is not found, throw an exception
            if (loader.getLocation() == null) {
                throw new IllegalStateException("Le fichier FXML n'a pas été trouvé à cet emplacement : " + fxmlPath);
            }

            // Load the scene from the FXML file
            Parent root = loader.load();
            Scene scene = new Scene(root);

            // Set the scene to the stage and show it
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la scène : " + e.getMessage());
        }
    }
}