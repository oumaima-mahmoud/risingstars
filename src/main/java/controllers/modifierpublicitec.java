package controllers;

import entite.Publicite;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.PubliciteService;
import services.PubliciteServiceimpl;

public class modifierpublicitec {

    @FXML
    private TextField txtTitre;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField txtMediaUrl;
    @FXML
    private ComboBox<String> cbStatut;

    private final PubliciteService publiciteService;
    private Publicite publiciteAModifier;

    public modifierpublicitec() {
        publiciteService = new PubliciteServiceimpl();
    }

    public void setPublicite(Publicite publicite) {
        this.publiciteAModifier = publicite;
        txtTitre.setText(publicite.getTitre());
        txtDescription.setText(publicite.getDescription());
        txtMediaUrl.setText(publicite.getMedia_url());
        cbStatut.setValue(publicite.getStatut());
    }

    @FXML
    private void modifierPublicite() {
        try {
            String titre = txtTitre.getText().trim();
            String description = txtDescription.getText().trim();
            String mediaUrl = txtMediaUrl.getText().trim();
            String statut = cbStatut.getValue();

            if (titre.isEmpty() || description.isEmpty() || mediaUrl.isEmpty() || statut == null) {
                afficherAlerte("Erreur", "Tous les champs doivent être remplis !");
                return;
            }

            publiciteAModifier.setTitre(titre);
            publiciteAModifier.setDescription(description);
            publiciteAModifier.setMedia_url(mediaUrl);
            publiciteAModifier.setStatut(statut);

            publiciteService.modifier(publiciteAModifier);

            afficherAlerte("Succès", "Publicité modifiée avec succès !");
            fermerFenetre();
        } catch (Exception e) {
            afficherAlerte("Erreur", "Une erreur est survenue lors de la modification.");
        }
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
