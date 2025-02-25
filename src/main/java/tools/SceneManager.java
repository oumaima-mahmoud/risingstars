package tools;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {

    private static Stage currentStage;

    // Définit le stage actuel
    public static void setStage(Stage stage) {
        currentStage = stage;
    }

    // Retourne le stage actuel
    public static Stage getCurrentStage() {
        return currentStage;
    }

    // Change la scène en chargeant un nouveau fichier FXML
    public static void switchScene(Stage stage, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
