package controllers;

import Services.UtilisateurService;
import entities.Role;
import entities.StatutAbonnement;
import entities.TypeAbonnement;
import entities.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegisterController {
    @FXML private TextField nomField, prenomField, emailField, numeroTelephoneField;
    @FXML private PasswordField passwordField, confirmPasswordField;
    @FXML private ComboBox<String> roleComboBox, typeAbonnementComboBox;  // ComboBox for role and typeAbonnement selection
    @FXML private Button registerButton;

    private final UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void initialize() {
        // Adding available roles to ComboBox
        roleComboBox.getItems().addAll(
                Role.SPECTATEUR.getDisplayName(),
                Role.GESTIONNAIRE.getDisplayName(),
                Role.ANNONCEUR.getDisplayName(),
                Role.SPONSOR.getDisplayName()
        );
        roleComboBox.setPromptText("Sélectionnez un rôle");

        // Adding available abonnement types to ComboBox
        typeAbonnementComboBox.getItems().addAll(
                TypeAbonnement.STANDARD.name(),
                TypeAbonnement.PREMIUM.name(),
                TypeAbonnement.VIP.name()
        );
        typeAbonnementComboBox.setPromptText("Sélectionnez un type d'abonnement");
    }

    @FXML
    private void handleRegister() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String numeroTelephone = numeroTelephoneField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String roleString = roleComboBox.getValue();
        String typeAbonnementString = typeAbonnementComboBox.getValue();

        // Vérification des champs vides
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                numeroTelephone.isEmpty() || roleString == null || typeAbonnementString == null) {
            showAlert("Erreur", "Tous les champs doivent être remplis !");
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

        // Convertir en enum Role et TypeAbonnement
        Role role = Role.valueOf(roleString.toUpperCase().replace(" ", "_"));
        TypeAbonnement typeAbonnement = TypeAbonnement.valueOf(typeAbonnementString.toUpperCase());

        // Créer un utilisateur avec les données saisies
        Utilisateur utilisateur = new Utilisateur(
                0, nom, prenom, email, password, role,
                numeroTelephone, StatutAbonnement.SUSPENDU, // Statut par défaut SUSPENDU
                typeAbonnement, 0 // No pointsFidelite
        );

        // Ajouter l'utilisateur
        utilisateurService.ajouter(utilisateur);
        showAlert("Succès", "Inscription réussie !");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
