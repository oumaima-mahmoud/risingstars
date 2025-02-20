package Controllers;

import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.ServiceCommande;
import models.Commande;

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
        // Vérification et récupération des valeurs
        int idPanier = validerIdPanier();
        if (idPanier == -1) return;

        double prixValue = validerPrix();
        if (prixValue == -1) return;

        int quantiteValue = validerQuantite();
        if (quantiteValue == -1) return;

        Date date = validerDate();
        if (date == null) return;

        // Création de la commande
        Commande nouvelleCommande = new Commande(quantiteValue, date, prixValue, idPanier);

        // Ajout via le service
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
}
