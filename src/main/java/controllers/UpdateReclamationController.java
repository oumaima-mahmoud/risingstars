package controllers;

import entite.Reclamation;
import services.ReclamationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateReclamationController {

    @FXML
    private TextField tfType;
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfObjet;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private Reclamation reclamationToEdit;
    private ReclamationController reclamationController;
    private ReclamationService reclamationService = new ReclamationService();

    public void setReclamationController(ReclamationController reclamationController) {
        this.reclamationController = reclamationController;
    }

    public void setReclamationToEdit(Reclamation reclamation) {
        this.reclamationToEdit = reclamation;
        tfType.setText(reclamation.getType());
        tfDescription.setText(reclamation.getDescription());
        tfObjet.setText(reclamation.getObjet());
    }

    @FXML
    private void saveReclamation() {
        String type = tfType.getText().trim();
        String description = tfDescription.getText().trim();
        String objet = tfObjet.getText().trim();

        if (type.isEmpty() || description.isEmpty() || objet.isEmpty()) {
            // Show an alert if any fields are empty
            return;
        }

        // Update the reclamation object
        reclamationToEdit.setType(type);
        reclamationToEdit.setDescription(description);
        reclamationToEdit.setObjet(objet);

        // Save the updated reclamation
        reclamationService.modifier(reclamationToEdit);

        // Close the window
        closeWindow();

        // Reload the list of reclamations in the main window
        reclamationController.loadReclamations();
    }

    @FXML
    private void cancelModification() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
}
