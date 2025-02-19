package controllers;

import entities.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Services.UtilisateurService;

public class UserFormController {
    @FXML private TextField tfNom;
    @FXML private TextField tfPrenom;
    @FXML private TextField tfEmail;
    @FXML private PasswordField pfPassword;

    private Utilisateur currentUser;
    private final UtilisateurService userService = new UtilisateurService();

    public void setUser(Utilisateur user) {
        this.currentUser = user;
        if (user != null) {
            tfNom.setText(user.getNom());
            tfPrenom.setText(user.getPrenom());
            tfEmail.setText(user.getEmail());
        }
    }

    @FXML
    private void handleSubmit() {
        if (validateForm()) {
            if (currentUser == null) {
                Utilisateur newUser = new Utilisateur(
                        0,
                        tfNom.getText(),
                        tfPrenom.getText(),
                        tfEmail.getText(),
                        pfPassword.getText()
                );
                userService.ajouter(newUser);
            } else {
                currentUser.setNom(tfNom.getText());
                currentUser.setPrenom(tfPrenom.getText());
                currentUser.setEmail(tfEmail.getText());
                userService.modifier(currentUser);
            }
            closeWindow();
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private boolean validateForm() {
        if (tfNom.getText().isEmpty() || tfEmail.getText().isEmpty()) {
            showAlert("Erreur", "Nom et email sont obligatoires.");
            return false;
        }
        return true;
    }

    private void closeWindow() {
        Stage stage = (Stage) tfNom.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}