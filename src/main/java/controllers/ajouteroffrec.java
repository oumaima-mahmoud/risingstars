package controllers;

import entite.Offre;
import entite.Publicite;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.OffreService;
import services.OffreServiceimpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ajouteroffrec {

    @FXML
    private TextField txtMontant;
    @FXML
    private TextArea txtConditions;
    @FXML
    private TextField txtContact;
    @FXML
    private Label lblDate;

    private final OffreService offreService = new OffreServiceimpl() {
        @Override
        public Offre getOne(Offre offre) {
            return null;
        }
    };
    private Publicite idPublicite;

    @FXML
    public void initialize() {
        // Afficher la date actuelle
        lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public void setIdPublicite(Publicite id) {
        this.idPublicite = id;
    }

    @FXML
    private void ajouterOffre() {
        try {
            double montant = Double.parseDouble(txtMontant.getText().trim());
            String conditions = txtConditions.getText().trim();
            String contact = txtContact.getText().trim();
            LocalDateTime dateProposition = LocalDateTime.now();

            if (conditions.isEmpty() || contact.isEmpty()) {
                afficherAlerte("Erreur", "Tous les champs doivent être remplis !");
                return;
            }

            Offre nouvelleOffre = new Offre(0, montant, conditions, dateProposition, contact, 1, idPublicite);
            offreService.ajouter(nouvelleOffre);

            afficherAlerte("Succès", "Offre ajoutée avec succès !");
            fermerFenetre();
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "Veuillez entrer un montant valide !");
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
        Stage stage = (Stage) txtMontant.getScene().getWindow();
        stage.close();
    }
}
