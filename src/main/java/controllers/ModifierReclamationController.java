package controllers;

import entite.Reclamation;
import services.ReclamationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ModifierReclamationController {

    @FXML
    private TextField typeField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField objetField;
    @FXML
    private TextField etatField;
    @FXML
    private TextField dateReclamationField;
    @FXML
    private TextField phoneNumberField; // New field for phone number

    private ReclamationService reclamationService = new ReclamationService();
    private Reclamation reclamation;
    private AdminReclamationController adminReclamationController; // Parent controller

    @FXML
    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        typeField.setText(reclamation.getType());
        descriptionField.setText(reclamation.getDescription());
        objetField.setText(reclamation.getObjet());
        etatField.setText(reclamation.getEtat());
        phoneNumberField.setText(reclamation.getPhoneNumber()); // Set phone number

        try {
            // Date field is read-only, no editing here
            dateReclamationField.setText(reclamation.getDateReclamation().split(" ")[0]); // Show only the date part
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors du formatage de la date.");
            e.printStackTrace();
        }
    }

    @FXML
    public void setAdminController(AdminReclamationController adminReclamationController) {
        this.adminReclamationController = adminReclamationController;
    }

    @FXML
    private void modifierReclamation(ActionEvent event) {
        if (reclamation != null) {
            try {
                // Get the updated values
                String type = typeField.getText().trim();
                String description = descriptionField.getText().trim();
                String objet = objetField.getText().trim();
                String etat = etatField.getText().trim();
                String phoneNumber = phoneNumberField.getText().trim();

                // Validate all fields
                if (!validateInput(type, description, objet, etat, phoneNumber)) {
                    return; // Stop if validation fails
                }

                // Update the reclamation object
                reclamation.setType(type);
                reclamation.setDescription(description);
                reclamation.setObjet(objet);
                reclamation.setEtat(etat);
                reclamation.setPhoneNumber(phoneNumber);

                // Update the reclamation in the database
                reclamationService.modifier(reclamation);
                showAlert("Succès", "Réclamation modifiée avec succès.");

                // Notify the AdminReclamationController to refresh the list
                if (adminReclamationController != null) {
                    adminReclamationController.refreshReclamationList(); // Refresh the list in the parent controller
                }

                // Close the window
                Stage stage = (Stage) typeField.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                showAlert("Erreur", "Erreur lors de la modification de la réclamation.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void cancelChanges(ActionEvent event) {
        // Close the window without saving changes
        Stage stage = (Stage) typeField.getScene().getWindow();
        stage.close();
    }

    private boolean validateInput(String type, String description, String objet, String etat, String phoneNumber) {
        // Validate required fields
        if (type.isEmpty() || description.isEmpty() || objet.isEmpty() || etat.isEmpty() || phoneNumber.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return false;
        }

        // Validate type
        if (!type.matches("[A-Z][a-zA-Z\\s]+")) {
            showAlert("Erreur de saisie", "Le type doit commencer par une majuscule et ne contenir que des lettres et des espaces.");
            return false;
        }

        // Validate description length
        if (description.length() < 10) {
            showAlert("Erreur de saisie", "La description doit contenir au moins 10 caractères.");
            return false;
        }

        // Validate objet
        if (!objet.matches("[a-zA-Z\\s]+")) {
            showAlert("Erreur de saisie", "L'objet ne doit contenir que des lettres et des espaces.");
            return false;
        }

        // Validate etat
        if (!etat.matches("[a-zA-Z_]+")) {
            showAlert("Erreur de saisie", "L'état ne doit contenir que des lettres et des caractères de soulignement (_).");
            return false;
        }

        // Validate phone number
        if (!phoneNumber.matches("\\+216\\d{8}")) {
            showAlert("Erreur de saisie", "Le numéro de téléphone doit commencer par +216 et contenir 8 chiffres supplémentaires.");
            return false;
        }

        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}