package controllers;

import entities.Utilisateur;
import entities.Role; // Assuming Role is an enum
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tools.SceneManager;
import Services.UtilisateurService;

public class EditUserController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<Role> roleComboBox; // Change from String to Role

    @FXML
    private Button updateButton;
    @FXML
    private Button backButton; // Ensure this is correctly linked

    private UtilisateurService utilisateurService = new UtilisateurService();
    private Utilisateur selectedUser;

    // Method to set the selected user for editing
    public void setUser(Utilisateur user) {
        this.selectedUser = user;
        nomField.setText(user.getNom());
        prenomField.setText(user.getPrenom());
        emailField.setText(user.getEmail());

        // Set the ComboBox value from the user's role
        roleComboBox.setValue(user.getRole());

        // If you want to fill ComboBox with all possible roles dynamically
        roleComboBox.setItems(FXCollections.observableArrayList(Role.values()));
    }

    // Method to handle updating the user
    @FXML
    private void updateUser() {
        try {
            // Update the user details
            selectedUser.setNom(nomField.getText());
            selectedUser.setPrenom(prenomField.getText());
            selectedUser.setEmail(emailField.getText());
            selectedUser.setRole(roleComboBox.getValue()); // Set role from ComboBox

            // Save the updated user to the database
            utilisateurService.modifier(selectedUser);

            // Show success message
            showAlert("Succès", "L'utilisateur a été mis à jour avec succès.");
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de la modification de l'utilisateur.");
        }
    }

    // Method to handle going back to the Admin Dashboard
    @FXML
    private void goBack() {
        // Switch back to the admin dashboard screen
        Stage stage = (Stage) backButton.getScene().getWindow();
        SceneManager.switchScene(stage, "/admin_dashboard.fxml");
    }

    // Method to show alert
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
