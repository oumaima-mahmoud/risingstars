package controllers;

import entite.Publicite;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.PubliciteService;
import java.time.LocalDate;

public class ajouterpublicitec {

    @FXML
    private TextField txtTitre;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField txtMediaUrl;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> comboStatut;

    private final PubliciteService publiciteService = new PubliciteService();

    @FXML
    private void ajouterPublicite() {
        String titre = txtTitre.getText().trim();
        String description = txtDescription.getText().trim();
        String mediaUrl = txtMediaUrl.getText().trim();
        LocalDate date = datePicker.getValue();
        String statut = comboStatut.getValue();

        if (titre.isEmpty() || description.isEmpty() || mediaUrl.isEmpty() || date == null || statut == null) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        Publicite nouvellePublicite = new Publicite(0, titre, description, mediaUrl, date, statut, 1); // 1 = id_annonceur fictif
        publiciteService.ajouter(nouvellePublicite);

        afficherAlerte("Succès", "Publicité ajoutée avec succès !");
        fermerFenetre();
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void fermerFenetre() {
        Stage stage = (Stage) txtTitre.getScene().getWindow();
        stage.close();
    }
}
