package controllers.user;

import services.UtilisateurService;
import entite.Role;
import entite.StatutAbonnement;
import entite.TypeAbonnement;
import entite.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tools.SessionManager;
import java.sql.SQLException;  // Import SQLException
import services.SMSService;
import tools.SceneManager;
import javafx.stage.Stage;

public class RegisterController {
    @FXML private TextField nomField, prenomField, emailField, numeroTelephoneField;
    @FXML private PasswordField passwordField, confirmPasswordField;
    @FXML private ComboBox<String> roleComboBox;  // ComboBox for role selection
    @FXML private Button registerButton;

    private final UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void initialize() {
        // Adding available roles to ComboBox
        roleComboBox.getItems().addAll(
                Role.SPECTATEUR.getDisplayName(),
                Role.GESTIONNAIRE.getDisplayName(),
                Role.ANNONCEUR.getDisplayName(),
                Role.SPONSOR.getDisplayName(),
                Role.ADMIN.getDisplayName()
        );
        roleComboBox.setPromptText("Sélectionnez un rôle");
    }

    @FXML
    private void handleRegister() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String numeroTelephone = numeroTelephoneField.getText().trim();  // User's phone number
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String roleString = roleComboBox.getValue();

        // Vérification des champs vides
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                numeroTelephone.isEmpty() || roleString == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        // Ensure the phone number is in the correct format
        if (!numeroTelephone.matches("^\\+\\d{1,3}\\d{4,}$")) {  // Basic check for international number format
            showAlert("Erreur", "Numéro de téléphone invalide. Assurez-vous d'inclure le code pays.");
            return;
        }

        // Vérification du mot de passe
        if (!password.equals(confirmPassword)) {
            showAlert("Erreur", "Les mots de passe ne correspondent pas !");
            return;
        }

        // Vérification de l'email (simplifiée)
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            showAlert("Erreur", "Veuillez entrer un email valide !");
            return;
        }

        // Convertir en enum Role using the updated fromString() method
        Role role = Role.fromString(roleString);

        // Créer un utilisateur avec les données saisies
        Utilisateur utilisateur = new Utilisateur(
                0, nom, prenom, email, password, role,
                numeroTelephone, StatutAbonnement.SUSPENDU, // Statut par défaut SUSPENDU
                TypeAbonnement.STANDARD, 0 // Utilisation du typeAbonnement par défaut
        );

        // Ajouter l'utilisateur avec exception handling
        try {
            utilisateurService.ajouter(utilisateur);
            showAlert("Succès", "Inscription réussie !");

            // Send welcome message via SMS using the user's details
            SMSService smsService = new SMSService();
            smsService.sendWelcomeMessage(numeroTelephone, nom, prenom);  // Pass dynamic values to the method
            SessionManager.setCurrentUser(utilisateur);
            Stage stage = (Stage) registerButton.getScene().getWindow(); // Get the current stage
            SceneManager.switchScene(stage, "/mainwindow.fxml"); // Redirect to the main interface


        } catch (SQLException e) {
            showAlert("Erreur", "Une erreur s'est produite lors de l'inscription : " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
