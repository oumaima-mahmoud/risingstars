package controllers;

import Services.UtilisateurService;
import entities.Role;
import entities.TypeAbonnement; // Add the necessary import for TypeAbonnement
import entities.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProfileController {
    @FXML private TextField nomField, prenomField, emailField, phoneNumberField, typeAbonnementField, pointsFideliteField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;  // ComboBox for role selection
    @FXML private Button updateButton;

    private final UtilisateurService utilisateurService = new UtilisateurService();
    private Utilisateur utilisateur;

    @FXML
    private void initialize() {
        utilisateur = utilisateurService.getOne(getCurrentUserId()); // Ensure this returns the correct user

        if (utilisateur != null) {
            // Initialize fields with user data
            nomField.setText(utilisateur.getNom());
            prenomField.setText(utilisateur.getPrenom());
            emailField.setText(utilisateur.getEmail());
            phoneNumberField.setText(utilisateur.getNumeroTelephone());  // Correct method for phone number

            // Convert TypeAbonnement to String and set it
            String typeAbonnementString = utilisateur.getTypeAbonnement().name(); // Convert enum to String
            typeAbonnementField.setText(typeAbonnementString);

            pointsFideliteField.setText(String.valueOf(utilisateur.getPointsFidelite()));

            // Initialize the role ComboBox with the user's role
            roleComboBox.setValue(utilisateur.getRole().getDisplayName());
        } else {
            showAlert("Erreur", "L'utilisateur n'a pas pu être trouvé.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void updateProfile() {
        if (utilisateur == null) {
            showAlert("Erreur", "L'utilisateur n'a pas pu être trouvé.", Alert.AlertType.ERROR);
            return;
        }

        // Update fields
        utilisateur.setNom(nomField.getText());
        utilisateur.setPrenom(prenomField.getText());
        utilisateur.setEmail(emailField.getText());
        utilisateur.setMotDePasse(passwordField.getText());
        utilisateur.setNumeroTelephone(phoneNumberField.getText());  // Correct method for phone number

        // Convert the role selected in the ComboBox back to Role enum
        String selectedRole = roleComboBox.getValue();
        Role role = Role.valueOf(selectedRole.toUpperCase().replace(" ", "_"));
        utilisateur.setRole(role);

        // Convert the updated typeAbonnement from String to TypeAbonnement enum
        String updatedTypeAbonnement = typeAbonnementField.getText();
        TypeAbonnement typeAbonnement = TypeAbonnement.valueOf(updatedTypeAbonnement.toUpperCase());
        utilisateur.setTypeAbonnement(typeAbonnement);

        // Update the utilisateur object
        utilisateurService.modifier(utilisateur);
        showAlert("Succès", "Profil mis à jour !", Alert.AlertType.INFORMATION);
    }

    private int getCurrentUserId() {
        return 1;  // For example, replace this with real session/user management logic
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
