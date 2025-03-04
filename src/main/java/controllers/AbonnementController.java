package controllers;

import Services.AbonnementService;
import entities.Abonnement;
import entities.TypeAbonnement;
import entities.StatutAbonnement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tools.SessionManager;

import java.io.IOException;
import java.sql.SQLException;

public class AbonnementController {

    @FXML
    private DatePicker dateDebutField;
    @FXML
    private DatePicker dateFinField;
    @FXML
    private TextField tarifField;
    @FXML
    private Button addAbonnementButton;
    @FXML
    private RadioButton standardRadio;
    @FXML
    private RadioButton premiumRadio;
    @FXML
    private RadioButton vipRadio;

    private final AbonnementService abonnementService = new AbonnementService();

    @FXML
    public void initialize() {
        // Ensure only one radio button is selected at a time
        standardRadio.setSelected(true); // By default select "Standard"
        updateAbonnementDetails();  // Set default tarif for Standard
    }

    @FXML
    private void updateAbonnementDetails() {
        // Update the subscription details based on the selected radio button
        if (standardRadio.isSelected()) {
            tarifField.setText("20");  // Price for "Standard"
        } else if (premiumRadio.isSelected()) {
            tarifField.setText("50"); // Price for "Premium"
        } else if (vipRadio.isSelected()) {
            tarifField.setText("100"); // Price for "VIP"
        }
    }

    @FXML
    private void addAbonnement() {
        try {
            // Check if all fields are filled
            if (dateDebutField.getValue() == null || dateFinField.getValue() == null || tarifField.getText().isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs !");
                return;
            }

            // Determine abonnement type based on selected radio button
            TypeAbonnement typeAbonnement = TypeAbonnement.STANDARD;
            if (premiumRadio.isSelected()) {
                typeAbonnement = TypeAbonnement.PREMIUM;
            } else if (vipRadio.isSelected()) {
                typeAbonnement = TypeAbonnement.VIP;
            }

            // Define status and points
            StatutAbonnement statut = StatutAbonnement.ACTIF;
            int pointsFidelite = 100; // Example points
            int idUser = SessionManager.getCurrentUserId();  // Assuming we get the current logged-in user's ID

            // Create new abonnement
            Abonnement abonnement = new Abonnement(
                    0, typeAbonnement, java.sql.Date.valueOf(dateDebutField.getValue()),
                    java.sql.Date.valueOf(dateFinField.getValue()), tarifField.getText(),
                    statut, pointsFidelite, idUser
            );

            // Add abonnement
            abonnementService.ajouter(abonnement);

            // Show success alert
            showAlert("Succès", "Abonnement ajouté avec succès !");

            // After adding, automatically save the data to the profile
            SessionManager.getCurrentUser().setTypeAbonnement(typeAbonnement);
            SessionManager.getCurrentUser().setPointsFidelite(pointsFidelite);
            // Assume profile is updated in session

            // Redirect to profile page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/profile.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) addAbonnementButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (SQLException | IOException e) {
            showAlert("Erreur", "Une erreur est survenue lors de l'ajout de l'abonnement.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
