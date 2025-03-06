package controllers.reclamation;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea; // Import TextArea
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import services.ReclamationService;
import entite.Reclamation;

public class AddReclamationController {

    @FXML
    private TextField typeTextField;

    @FXML
    private TextArea descriptionTextField; // Change to TextArea

    @FXML
    private TextField objetTextField;

    @FXML
    private TextField etatTextField;

    @FXML
    private TextField phoneNumberField; // New field for phone number

    private AdminReclamationController adminController;
    private ReclamationService reclamationService = new ReclamationService();

    // Setter pour injecter l'AdminReclamationController
    public void setAdminController(AdminReclamationController adminController) {
        this.adminController = adminController;
    }
    private UserReclamationController userController;

    public void setUserController(UserReclamationController userController) {
        this.userController = userController;
    }
    @FXML
    public void saveReclamation() {
        String type = typeTextField.getText().trim();
        String description = descriptionTextField.getText().trim(); // Works with TextArea
        String objet = objetTextField.getText().trim();
        String etat = etatTextField.getText().trim();
        String phoneNumber = phoneNumberField.getText().trim(); // Get phone number

        // Vérification des saisies
        if (!validateInput(type, description, objet, etat, phoneNumber)) {
            return; // Arrêter si la validation échoue
        }

        // Exemple d'ID utilisateur, à remplacer par la vraie logique
        int userId = 1;

        // Création et sauvegarde de la réclamation
        Reclamation reclamation = new Reclamation(userId, type, description, objet, etat, phoneNumber); // Include phone number
        reclamationService.ajouter(reclamation);

        // Fermer la fenêtre après ajout
        Stage stage = (Stage) typeTextField.getScene().getWindow();
        stage.close();

        // Rafraîchir la liste des réclamations
        if (adminController != null) {
            adminController.refreshReclamationList();
        }
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

        // Validate description length
        if (description.length() < 10) {
            showAlert("Erreur de saisie", "La description doit contenir au moins 10 caractères.");
            return false;
        }

        // Validate phone number
        if (!phoneNumber.matches("\\+216\\d{8}")) {
            showAlert("Erreur de saisie", "Le numéro de téléphone doit commencer par +216 et contenir 8 chiffres supplémentaires.");
            return false;
        }

        return true;
    }

    @FXML
    public void cancelAdding() {
        Stage stage = (Stage) typeTextField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}