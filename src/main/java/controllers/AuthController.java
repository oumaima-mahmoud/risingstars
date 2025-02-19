package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Services.UtilisateurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;




public class AuthController {
    @FXML private TextField tfEmail;
    @FXML private PasswordField pfPassword;
    @FXML private ComboBox<String> cbRole;

    private final UtilisateurService authService = new UtilisateurService();

    @FXML
    private void handleLogin() {
        String email = tfEmail.getText();
        String password = pfPassword.getText();
        String role = cbRole.getValue();

        if (authService.authenticate(email, password, role)) {
            redirectToDashboard(role);
        } else {
            showAlert("Erreur d'authentification", "Email ou mot de passe incorrect.");
        }
    }

    private void redirectToDashboard(String role) {
        try {
            String fxmlPath = switch (role) {
                case "Gestionnaire" -> "/views/gestionnaire/dashboard.fxml";
                case "Spectateur" -> "/views/spectateur/dashboard.fxml";
                case "Annonceur" -> "/views/annonceur/dashboard.fxml";
                case "Sponsor" -> "/views/sponsor/dashboard.fxml";
                default -> throw new IllegalArgumentException("Rôle invalide");
            };

            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) tfEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}