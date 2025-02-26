package controllers;

import entite.Offre;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.OffreService;

import java.time.LocalDate;

public class AddOffreController {

    @FXML
    private TextField montantField;

    @FXML
    private TextField conditionsField;

    @FXML
    private TextField contactField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField publiciteIdField;

    private final OffreService offreService = new OffreService();

    @FXML
    private void handleAddOffre() {
        String montantText = montantField.getText();
        if (!isValidDouble(montantText)) {
            showInvalidInputAlert("Montant must be a valid number.");
            return; // Stop further processing
        }

        double montant = Double.parseDouble(montantText);
        String conditions = conditionsField.getText();
        String contact = contactField.getText();

        // Get selected date from DatePicker
        LocalDate selectedDate = dateField.getValue();
        if (selectedDate == null) {
            showInvalidInputAlert("Please select a valid date.");
            return;
        }
        String date = selectedDate.toString();  // Convert the date to string format (YYYY-MM-DD)

        // Retrieve userId, using the value from userIdField or set default if not filled
        int userId = 1; // Default or test value, modify when user entity is available

        // Validate publiciteId as an integer

        // Create the Offre object
        Offre newOffre = new Offre(0, userId, montant, conditions, date, contact);
        offreService.ajouter(newOffre);

        // Close the window after adding
        montantField.getScene().getWindow().hide();
    }

    private boolean isValidDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void showInvalidInputAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
