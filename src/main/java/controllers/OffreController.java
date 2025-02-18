package controllers;

import entite.Offre;
import services.OffreService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.util.List;
public class OffreController {

    @FXML
    private TextField montantField, conditionsField, datePropositionField, contactField, idSponsorField, idPubliciteField;
    @FXML
    private Label messageLabel;

    private OffreService offreService = new OffreService();

    @FXML
    public void initialize() {
        // Initialize any required components here
    }

    @FXML
    private void ajouterOffre(ActionEvent event) {
        try {
            double montant = Double.parseDouble(montantField.getText());
            String conditions = conditionsField.getText();
            String dateProposition = datePropositionField.getText();
            String contact = contactField.getText();
            int idSponsor = Integer.parseInt(idSponsorField.getText());
            int idPublicite = Integer.parseInt(idPubliciteField.getText());

            Offre offre = new Offre(0, montant, conditions, dateProposition, contact, idSponsor, idPublicite);
            offreService.ajouter(offre);

            clearFields();
            messageLabel.setText("Offre ajoutée avec succès !");
        } catch (NumberFormatException e) {
            messageLabel.setText("Erreur : Veuillez entrer des valeurs valides.");
        }
    }

    @FXML
    private void modifierOffre(ActionEvent event) {
        try {
            int id_offre = Integer.parseInt(idPubliciteField.getText()); // Assuming ID_OFFRE is entered in idPubliciteField
            double montant = Double.parseDouble(montantField.getText());
            String conditions = conditionsField.getText();
            String dateProposition = datePropositionField.getText();
            String contact = contactField.getText();
            int idSponsor = Integer.parseInt(idSponsorField.getText());
            int idPublicite = Integer.parseInt(idPubliciteField.getText());

            Offre offre = new Offre(id_offre, montant, conditions, dateProposition, contact, idSponsor, idPublicite);
            offreService.modifier(offre);

            clearFields();
            messageLabel.setText("Offre modifiée avec succès !");
        } catch (NumberFormatException e) {
            messageLabel.setText("Erreur : Veuillez entrer des valeurs valides.");
        }
    }

    @FXML
    private void supprimerOffre(ActionEvent event) {
        try {
            int id_offre = Integer.parseInt(idPubliciteField.getText());
            offreService.supprimer(id_offre);

            clearFields();
            messageLabel.setText("Offre supprimée avec succès !");
        } catch (NumberFormatException e) {
            messageLabel.setText("Erreur : Veuillez entrer un ID valide.");
        }
    }

    @FXML
    private void voirOffres(ActionEvent event) {
        List<Offre> offres = offreService.getAll();
        StringBuilder sb = new StringBuilder("Liste des offres :\n");
        for (Offre offre : offres) {
            sb.append(offre.toString()).append("\n");
        }
        messageLabel.setText(sb.toString());
    }

    private void clearFields() {
        montantField.clear();
        conditionsField.clear();
        datePropositionField.clear();
        contactField.clear();
        idSponsorField.clear();
        idPubliciteField.clear();
    }
}