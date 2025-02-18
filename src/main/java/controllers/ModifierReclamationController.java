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
                // Update the fields with the new values, except the date
                reclamation.setType(typeField.getText());
                reclamation.setDescription(descriptionField.getText());
                reclamation.setObjet(objetField.getText());
                reclamation.setEtat(etatField.getText());

                // Update the reclamation
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

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
