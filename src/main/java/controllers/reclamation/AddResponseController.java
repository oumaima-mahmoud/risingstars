package controllers.reclamation;

import entite.Reponse;
import services.ReponseService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

import java.io.File;

public class AddResponseController {

    @FXML
    private TextArea responseContentTextArea;

    @FXML
    private TextField typeTextField;  // TextField for Type input

    @FXML
    private TextField priorityTextField;  // TextField for Priority input

    @FXML
    private Button selectImageButton;  // Button to trigger image selection

    @FXML
    private Label imagePathLabel;  // Label to show selected image file path

    private ReponseService reponseService = new ReponseService();
    private int reclamationId; // This ID should be passed when opening the window
    private String imageFilePath = "";  // Variable to store the selected image file path

    private AdminReponseController adminReponseController; // Reference to AdminReponseController

    // Setter for AdminReponseController
    public void setAdminReponseController(AdminReponseController adminReponseController) {
        this.adminReponseController = adminReponseController;
    }

    public void setReclamationId(int reclamationId) {
        this.reclamationId = reclamationId;
    }

    @FXML
    private void onAddResponse() {
        String responseContent = responseContentTextArea.getText().trim();
        String type = typeTextField.getText().trim();  // Get manually entered type
        String priorityString = priorityTextField.getText().trim();  // Get manually entered priority

        if (responseContent.isEmpty()) {
            showAlert("Erreur", "La réponse ne peut pas être vide.");
        } else if (type.isEmpty()) {
            showAlert("Erreur", "Le type ne peut pas être vide.");
        } else if (priorityString.isEmpty()) {
            showAlert("Erreur", "La priorité ne peut pas être vide.");
        } else {
            int priority = 0;
            try {
                // Validate that priority is either 1, 2, or 3
                priority = Integer.parseInt(priorityString);
                if (priority < 1 || priority > 3) {
                    throw new NumberFormatException();  // Throws if priority is out of range
                }
            } catch (NumberFormatException e) {
                showAlert("Erreur", "La priorité doit être 1, 2 ou 3.");
                return;
            }

            // Creating a Reponse object and setting the required values
            Reponse reponse = new Reponse(
                    reclamationId, // idReclamation
                    responseContent, // contenu
                    getCurrentDate(), // dateReponse
                    type, // type (manual entry)
                    imageFilePath, // fichierJoint (path to the selected image, if any)
                    priority // priorite
            );

            // Adding the response using the service
            reponseService.ajouterReponse(reponse);

            // Notify AdminReponseController to refresh the responses list
            if (adminReponseController != null) {
                adminReponseController.refreshReponseList();
            }

            closeWindow();
        }
    }

    @FXML
    private void onCancel() {
        closeWindow();
    }

    @FXML
    private void onSelectImage() {
        // Open a file chooser to select an image
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(new Stage()); // Open file chooser dialog

        if (selectedFile != null) {
            // If a file is selected, store the file path and update the label
            imageFilePath = selectedFile.getAbsolutePath();
            imagePathLabel.setText("Image sélectionnée: " + selectedFile.getName());
        }
    }

    private void closeWindow() {
        // Close the current window (Stage)
        Stage stage = (Stage) responseContentTextArea.getScene().getWindow();
        stage.close();
    }

    private String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}