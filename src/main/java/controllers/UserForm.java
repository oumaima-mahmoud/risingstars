package controllers;

import Services.UtilisateurService;
import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserForm {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField idmail;

    @FXML
    private PasswordField idmdp;

    @FXML
    private TextField idnom;

    @FXML
    private TextField idprenom;

    private Utilisateur currentUser;
    private final UtilisateurService userService = new UtilisateurService();

    public void setUser(Utilisateur user) {
        this.currentUser = user;
        if (user != null) {
            idnom.setText(user.getNom());
            idprenom.setText(user.getPrenom());
            idmail.setText(user.getEmail());
        }
    }

    @FXML
    private void handleSubmit() {
        if (validateForm()) {
            if (currentUser == null) {
                Utilisateur newUser = new Utilisateur(
                        0,
                        idnom.getText(),
                        idprenom.getText(),
                        idmail.getText(),
                        idmdp.getText()
                );
                userService.ajouter(newUser);
            } else {
                currentUser.setNom(idnom.getText());
                currentUser.setPrenom(idprenom.getText());
                currentUser.setEmail(idmail.getText());
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
        if (idnom.getText().isEmpty() || idmail.getText().isEmpty()) {
            showAlert("Erreur", "Nom et email sont obligatoires.");
            return false;
        }
        return true;
    }

    private void closeWindow() {
        Stage stage = (Stage) idnom.getScene().getWindow();
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

