package controllers;

import Services.UtilisateurService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tools.SceneManager;

import java.util.Objects;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private Button loginButton;

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

        // Vérification si l'utilisateur existe
        if (!utilisateurService.checkIfUserExists(email)) {
            showAlert("Erreur", "Aucun utilisateur trouvé avec cet email !");
            return;
        }

        // Authentification de l'utilisateur
        boolean isAuthenticated = utilisateurService.authenticate(email, password, role);
        if (isAuthenticated) {
            showAlert("Succès", "Connexion réussie !");
            // Redirection selon le rôle
            redirectUser(role);
        } else {
            showAlert("Erreur", "Identifiants incorrects !");
        }
    }

    private void redirectUser(String role) {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        if (Objects.equals(role, "Spectateur")) {
            SceneManager.switchScene(stage, "/abonnement.fxml");
        } else {
            SceneManager.switchScene(stage, "/home.fxml");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
