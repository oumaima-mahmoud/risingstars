package controllers;

import entite.Offre;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.OffreService;
import services.OffreServiceimpl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class modifieroffrec {

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

    private Offre offreAModifier;

    @FXML
    public void initialize() {
        lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    public void setOffre(Offre offre) {
        this.offreAModifier = offre;
        txtMontant.setText(String.valueOf(offre.getMontant()));
        txtConditions.setText(offre.getConditions());
        txtContact.setText(offre.getContact());
        lblDate.setText(offre.getDate_proposition().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @FXML
    private void modifierOffre() {
        try {
            double montant = Double.parseDouble(txtMontant.getText().trim());
            String conditions = txtConditions.getText().trim();
            String contact = txtContact.getText().trim();

            if (conditions.isEmpty() || contact.isEmpty()) {
                afficherAlerte("Erreur", "Tous les champs doivent être remplis !");
                return;
            }

            offreAModifier.setMontant(montant);
            offreAModifier.setConditions(conditions);
            offreAModifier.setContact(contact);

            offreService.modifier(offreAModifier);

            afficherAlerte("Succès", "Offre modifiée avec succès !");
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
