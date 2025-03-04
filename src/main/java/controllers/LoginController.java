package controllers;

import Services.UtilisateurService;
import entities.Role;
import entities.Utilisateur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tools.SceneManager;
import tools.SessionManager;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private Button loginButton;

    private final UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        // Vérification des champs vides
        if (email.isEmpty() || password.isEmpty() || role == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs !");
            return;
        }

        try {
            // Vérification si l'utilisateur existe
            if (!utilisateurService.checkIfUserExists(email)) {
                showAlert("Erreur", "Aucun utilisateur trouvé avec cet email !");
                return;
            }

            // Authentification de l'utilisateur
            boolean isAuthenticated = utilisateurService.authenticate(email, password, role);
            if (isAuthenticated) {
                // Récupérer l'utilisateur connecté
                Utilisateur utilisateur = utilisateurService.getUserByEmail(email);
                if (utilisateur != null) {
                    // Stocker l'utilisateur dans la session
                    SessionManager.setCurrentUser(utilisateur);
                    showAlert("Succès", "Connexion réussie !", Alert.AlertType.INFORMATION);  // Change to INFORMATION type for success
                    // Redirection vers la page d'accueil
                    redirectToHomePage(utilisateur);
                } else {
                    showAlert("Erreur", "Utilisateur non trouvé !");
                }
            } else {
                showAlert("Erreur", "Identifiants incorrects !");
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la connexion : " + e.getMessage());
        }
    }

    private void redirectToHomePage(Utilisateur utilisateur) {
        Stage stage = (Stage) loginButton.getScene().getWindow();

        // Vérification du rôle
        try {
            if (utilisateur.getRole() == Role.ADMIN) {
                // If the user is an admin, redirect to the admin dashboard
                SceneManager.switchScene(stage, "/admin_dashboard.fxml");
            } else {
                // For all other roles, redirect to the profile page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/profile.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);

                // Set the scene to the stage and show it
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();  // Optional: for debugging
        }
    }

    private void showAlert(String title, String message) {
        showAlert(title, message, Alert.AlertType.ERROR);  // Default is ERROR type
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
