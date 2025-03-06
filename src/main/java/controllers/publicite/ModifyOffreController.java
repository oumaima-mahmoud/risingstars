package controllers.publicite;

import entite.Offre;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.OffreService;

import java.time.LocalDate;

public class ModifyOffreController {

    @FXML
    private TextField idField;

    @FXML
    private TextField montantField;

    @FXML
    private TextField conditionsField;

    @FXML
    private TextField contactField;

    @FXML
    private DatePicker dateField;  // Changed to DatePicker

    @FXML
    private TextField userIdField;

    @FXML
    private TextField publiciteIdField;

    private final OffreService offreService = new OffreService();

    private Offre selectedOffre; // To hold the selected Offre

    // Set the selected Offre and pre-fill the fields
    public void setOffre(Offre offre) {
        this.selectedOffre = offre;
        idField.setText(String.valueOf(offre.getId_offre()));
        montantField.setText(String.valueOf(offre.getMontant()));
        conditionsField.setText(offre.getConditions());
        contactField.setText(offre.getContact());

        // Convert string date (offre.getDate_proposition()) to LocalDate and set to DatePicker
        if (offre.getDate_proposition() != null && !offre.getDate_proposition().isEmpty()) {
            try {
                LocalDate date = LocalDate.parse(offre.getDate_proposition());  // Parse date
                dateField.setValue(date);  // Set the DatePicker value
            } catch (Exception e) {
                // Handle parsing errors
            }
        }

        userIdField.setText(String.valueOf(offre.getId_user()));
        // Set publiciteIdField if needed (you can keep it or omit if not necessary)
    }

    @FXML
    private void handleModifyOffre() {
        String idText = idField.getText();
        if (!isValidInteger(idText)) {
            showInvalidInputAlert("Offre ID must be a valid integer.");
            return;
        }

        int id = Integer.parseInt(idText);
        String montantText = montantField.getText();
        if (!isValidDouble(montantText)) {
            showInvalidInputAlert("Montant must be a valid number.");
            return;
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

        // Create the updated Offre object
        Offre updatedOffre = new Offre(id, selectedOffre.getId_user(), montant, conditions, date, contact);
        offreService.modifier(updatedOffre);

        // Close the window after modifying
        idField.getScene().getWindow().hide();
    }

    private boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
