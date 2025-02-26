package controllers;

import entite.Reponse;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ReponseService;

import java.io.File;

public class UpdateReponseController {

    @FXML
    private TextArea taContenu;
    @FXML
    private TextField tfType;  // TextField for 'type'
    @FXML
    private TextField tfFichierJoint; // TextField for 'fichierJoint'
    @FXML
    private TextField tfPriorite; // TextField for 'priorite'
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSelectFile;  // Button to open file dialog for selecting files

    private Reponse reponseToEdit;
    private AdminReponseController adminReponseController; // Link to AdminReponseController

    private ReponseService reponseService = new ReponseService();

    // Set the AdminReponseController so we can refresh the list later
    public void setAdminReponseController(AdminReponseController adminReponseController) {
        this.adminReponseController = adminReponseController;
    }

    public void setReponseToEdit(Reponse reponse) {
        this.reponseToEdit = reponse;
        taContenu.setText(reponse.getContenu());

        // Set the 'type' TextField
        tfType.setText(reponse.getType());

        // Set 'fichierJoint' TextField
        tfFichierJoint.setText(reponse.getFichierJoint());

        // Set the 'priorite' TextField
        tfPriorite.setText(String.valueOf(reponse.getPriorite()));
    }

    @FXML
    private void saveReponse() {
        String contenu = taContenu.getText().trim();
        String type = tfType.getText().trim();  // Get value from TextField
        String fichierJoint = tfFichierJoint.getText().trim();
        Integer priorite = Integer.parseInt(tfPriorite.getText().trim()); // Parse as Integer

        if (contenu.isEmpty()) {
            showAlert("Erreur", "Le contenu de la réponse ne peut pas être vide.");
            return;
        }

        // Validate other fields if necessary
        if (type.isEmpty() || fichierJoint.isEmpty() || priorite == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        // Validate the fichierJoint to accept only PNG files
        if (!fichierJoint.endsWith(".png")) {
            showAlert("Erreur", "Le fichier joint doit être un fichier PNG.");
            return;
        }

        // Validate priority (only values 1, 2, 3 are allowed)
        if (priorite < 1 || priorite > 3) {
            showAlert("Erreur", "La priorité doit être un nombre entre 1 et 3.");
            return;
        }

        // Update the reponse object
        reponseToEdit.setContenu(contenu);
        reponseToEdit.setType(type);
        reponseToEdit.setFichierJoint(fichierJoint);
        reponseToEdit.setPriorite(priorite);

        // Call the service to update the response in the database
        reponseService.modifierReponse(reponseToEdit);

        // Close the window
        closeWindow();

        // Notify AdminReponseController to refresh the responses list
        if (adminReponseController != null) {
            adminReponseController.refreshReponseList(); // Call the correct method
        }
    }

    @FXML
    private void cancelModification() {
        closeWindow();
    }

    @FXML
    private void selectFile() {
        // Open file chooser to select a PNG file
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.png"));
        File file = fileChooser.showOpenDialog(btnSave.getScene().getWindow());

        if (file != null) {
            tfFichierJoint.setText(file.getAbsolutePath());  // Set file path to the TextField
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}