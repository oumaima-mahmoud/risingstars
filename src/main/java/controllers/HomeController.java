package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;

import java.io.IOException;

public class HomeController {

    @FXML
    private ComboBox<String> userDropdown;

    @FXML
    public void initialize() {
        // Make sure ComboBox is initialized here if needed
        // This is optional if ComboBox is just for visual display
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

            // Create a new scene and set it on the primary stage
            Scene scene = new Scene(root);
            Stage primaryStage = (Stage) userDropdown.getScene().getWindow(); // Get the current stage from the ComboBox's scene
            primaryStage.setTitle(pageTitle); // Set the window title
            primaryStage.setScene(scene); // Set the new scene
            primaryStage.show(); // Show the stage with the new scene

        } catch (IOException e) {
            e.printStackTrace(); // Print error to console if FXML loading fails
        }
    }
}
