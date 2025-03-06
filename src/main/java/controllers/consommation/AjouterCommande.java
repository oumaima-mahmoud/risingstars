package controllers.consommation;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.Node;
import services.ServiceCommande;
import entite.Commande;

public class AjouterCommande {

    @FXML
    private DatePicker datecommande;

    @FXML
    private TextField idpanierr;

    @FXML
    private TextField prix;

    @FXML
    private TextField quantite;

    private final ServiceCommande commandeService = new ServiceCommande();

    @FXML
    void ajouterCommande(ActionEvent event) {
        int idPanier = validerIdPanier();
        if (idPanier == -1) return;

        double prixValue = validerPrix();
        if (prixValue == -1) return;

        int quantiteValue = validerQuantite();
        if (quantiteValue == -1) return;

        Date date = validerDate();
        if (date == null) return;

        Commande nouvelleCommande = new Commande(quantiteValue, date, prixValue, idPanier);

        try {
            commandeService.ajouterCommande(nouvelleCommande);
            afficherAlerte(Alert.AlertType.INFORMATION, "Succès", "Commande ajoutée avec succès !");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de la commande : " + e.getMessage());
        }
    }

    // ======== MÉTHODES DE VALIDATION ========

    private int validerIdPanier() {
        String texte = idpanierr.getText().trim();
        if (texte.isEmpty()) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "L'ID du panier ne peut pas être vide !");
            return -1;
        }
        try {
            int id = Integer.parseInt(texte);
            if (id <= 0) throw new NumberFormatException();
            return id;
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "L'ID du panier doit être un entier positif !");
            return -1;
        }
    }

    private double validerPrix() {
        String texte = prix.getText().trim();
        if (texte.isEmpty()) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Le prix ne peut pas être vide !");
            return -1;
        }
        try {
            double valeur = Double.parseDouble(texte);
            if (valeur <= 0) throw new NumberFormatException();
            return valeur;
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Le prix doit être un nombre décimal positif !");
            return -1;
        }
    }

    private int validerQuantite() {
        String texte = quantite.getText().trim();
        if (texte.isEmpty()) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "La quantité ne peut pas être vide !");
            return -1;
        }
        try {
            int valeur = Integer.parseInt(texte);
            if (valeur <= 0) throw new NumberFormatException();
            return valeur;
        } catch (NumberFormatException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "La quantité doit être un entier strictement positif !");
            return -1;
        }
    }

    private Date validerDate() {
        if (datecommande.getValue() == null) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une date !");
            return null;
        }
        LocalDate selectedDate = datecommande.getValue();
        if (selectedDate.isBefore(LocalDate.now())) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "La date ne peut pas être dans le passé !");
            return null;
        }
        return Date.valueOf(selectedDate);
    }

    // ======== MÉTHODE D'AFFICHAGE D'ALERTE ========
    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ======== MÉTHODES POUR CHANGER DE VUE ========

    @FXML
    void AfficherCommande(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/consommation/AfficherCommande.fxml"));
            Parent root = loader.load();
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }

    @FXML
    void ModifierCommande(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/consommation/ModifierCommande.fxml"));
            Parent root = loader.load();
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }

    @FXML
    void SupprimerCommande(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/consommation/SupprimerCommande.fxml"));
            Parent root = loader.load();
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }
}
