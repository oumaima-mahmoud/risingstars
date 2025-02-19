package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField tfEmail;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private ComboBox<String> cbRole;

    @FXML
    private Button btnLogin;

    // Méthode appelée lors du clic sur le bouton "Se connecter"
    @FXML
    private void handleLogin() {
        String email = tfEmail.getText();
        String password = pfPassword.getText();
        String role = cbRole.getValue(); // Récupère le rôle sélectionné

        // Vérification simple (tu peux ajouter une vraie logique d'authentification ici)
        if (email.isEmpty() || password.isEmpty() || role == null) {
            System.out.println("Veuillez remplir tous les champs !");
            return;
        }

        System.out.println("Connexion réussie avec :");
        System.out.println("Email: " + email);
        System.out.println("Mot de passe: " + password);
        System.out.println("Rôle: " + role);

        // Ajoute ici la redirection vers une autre interface si nécessaire
    }
}
