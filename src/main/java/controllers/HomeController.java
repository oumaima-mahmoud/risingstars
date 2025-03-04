package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {

    // Removed the ComboBox, as it is not needed for page switching

    @FXML
    public void initialize() {
        // This can be used for initialization if needed.
    }

    @FXML
    public void goToContactPage() {
        // Logic to go to the Contact page
        loadPage("/contact.fxml", "Contact Page");
    }

    @FXML
    public void goToRegisterPage() {
        // Logic to go to the Register page
        loadPage("/register.fxml", "Register Page");
    }

    @FXML
    public void goToLoginPage() {
        // Logic to go to the Login page
        loadPage("/Login.fxml", "Login Page");
    }

    @FXML
    public void goToAbonnements() {
        // Logic to go to the Abonnements page
        loadPage("/abonnement.fxml", "Abonnements Page");
    }

    private void loadPage(String fxmlPath, String pageTitle) {
        try {
            // Load the corresponding FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane root = loader.load();

            // Get the current stage using the Stage constructor
            Stage primaryStage = new Stage();  // This creates a new stage if no current one exists

            // Create a new scene and set it on the primary stage
            Scene scene = new Scene(root);
            primaryStage.setTitle(pageTitle); // Set the window title
            primaryStage.setScene(scene); // Set the new scene
            primaryStage.show(); // Show the stage with the new scene

        } catch (IOException e) {
            e.printStackTrace(); // Print error to console if FXML loading fails
        }
    }
}
