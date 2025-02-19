package controllers;

import entities.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import Services.UtilisateurService;

public class DetailsController {
    @FXML private Label lblNom;
    @FXML private Label lblPrenom;
    @FXML private Label lblEmail;
    @FXML private ListView<String> abonnementsList;

    private Utilisateur currentUser;
    private final UtilisateurService userService = new UtilisateurService();

    public void setUser(Utilisateur user) {
        this.currentUser = user;
        if (user != null) {
            lblNom.setText(user.getNom());
            lblPrenom.setText(user.getPrenom());
            lblEmail.setText(user.getEmail());
            loadAbonnements(user.getIdUser());
        }
    }

    private void loadAbonnements(int userId) {
        // Implémentez la logique pour charger les abonnements
        abonnementsList.getItems().setAll(userService.getAbonnementsByUserId(userId));
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) lblNom.getScene().getWindow();
        stage.close();
    }
}