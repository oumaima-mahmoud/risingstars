package controllers;

import Services.AbonnementService;
import Services.UtilisateurService;
import entities.Utilisateur;
import entities.Role;
import entities.TypeAbonnement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tools.SceneManager;
import tools.SessionManager;

import java.sql.SQLException;

public class ProfileController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField typeAbonnementField;
    @FXML private TextField pointsFideliteField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Button updateButton;
    @FXML private Button addAbonnementButton;
    @FXML private Button logoutButton;

    private final AbonnementService abonnementService = new AbonnementService();
    private final UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void initialize() {
        Utilisateur utilisateur = SessionManager.getCurrentUser(); // Récupérer l'utilisateur connecté
        if (utilisateur == null) {
            showAlert("Erreur", "Aucun utilisateur connecté.");
            return;
        }

        // Initialiser les champs avec les données de l'utilisateur
        nomField.setText(utilisateur.getNom());
        prenomField.setText(utilisateur.getPrenom());
        emailField.setText(utilisateur.getEmail());
        phoneNumberField.setText(utilisateur.getNumeroTelephone());
        typeAbonnementField.setText(utilisateur.getTypeAbonnement().toString());
        pointsFideliteField.setText(String.valueOf(utilisateur.getPointsFidelite()));

        // Initialiser le ComboBox de rôle
        roleComboBox.getItems().setAll(Role.GESTIONNAIRE.name(), Role.SPECTATEUR.name(), Role.SPONSOR.name(), Role.ANNONCEUR.name());
        roleComboBox.setValue(utilisateur.getRole().name());
    }

    @FXML
    private void updateProfile() {
        Utilisateur utilisateur = SessionManager.getCurrentUser(); // Récupérer l'utilisateur connecté
        if (utilisateur == null) {
            showAlert("Erreur", "Utilisateur non trouvé");
            return;
        }

        utilisateur.setNom(nomField.getText());
        utilisateur.setPrenom(prenomField.getText());
        utilisateur.setEmail(emailField.getText());
        utilisateur.setMotDePasse(passwordField.getText());
        utilisateur.setNumeroTelephone(phoneNumberField.getText());

        String selectedRole = roleComboBox.getValue();
        utilisateur.setRole(Role.valueOf(selectedRole.toUpperCase()));

        String updatedTypeAbonnement = typeAbonnementField.getText();
        utilisateur.setTypeAbonnement(TypeAbonnement.valueOf(updatedTypeAbonnement.toUpperCase()));

        try {
            utilisateurService.modifier(utilisateur);
            showAlert("Succès", "Profil mis à jour avec succès !");
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la mise à jour");
            e.printStackTrace();
        }
    }

    @FXML
    private void addAbonnement() {
        try {
            // Get the stage from the current button's scene
            Stage stage = (Stage) addAbonnementButton.getScene().getWindow();

            // Load the FXML for the target scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/abonnement.fxml"));
            Parent root = loader.load();

            // Create a new scene with the loaded FXML
            Scene scene = new Scene(root);

            // Set the new scene to the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de la redirection.");
            e.printStackTrace();
        }
    }


    @FXML
    private void logout() {
        SessionManager.logout();
        if (logoutButton.getScene() != null) {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();
        }
        SceneManager.switchScene((Stage) logoutButton.getScene().getWindow(), "/login.fxml");
    }

    @FXML
    private void deleteAccount() {
        Utilisateur utilisateur = SessionManager.getCurrentUser(); // Récupérer l'utilisateur connecté
        if (utilisateur == null) {
            showAlert("Erreur", "Utilisateur non trouvé");
            return;
        }

        try {
            abonnementService.supprimer(utilisateur.getIdUser());  // Supprimer l'abonnement de l'utilisateur
            utilisateurService.supprimer(utilisateur.getIdUser());  // Supprimer l'utilisateur de la base de données
            showAlert("Succès", "Compte supprimé avec succès");
            SessionManager.logout(); // Déconnecter l'utilisateur après suppression
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close(); // Fermer la fenêtre après suppression
        } catch (SQLException e) {
            showAlert("Erreur", "Une erreur est survenue lors de la suppression du compte.");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}