package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Panier;
import services.ServicePanier;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModifierPanier {

    @FXML
    private DatePicker dateCreation;

    @FXML
    private ComboBox<String> etatComboBox; // Utilisation du type spécifique String pour le ComboBox

    @FXML
    private TextField idPanier;

    @FXML
    private TextField total;

    private final ServicePanier panierService = new ServicePanier();

    @FXML
    void initialize() {
        // Initialisation du ComboBox avec des valeurs prédéfinies
        etatComboBox.getItems().addAll("Annulé", "En cours", "Validé");
        etatComboBox.setEditable(false); // Empêche la saisie manuelle
    }

    @FXML
    void Modifier(ActionEvent event) {
        if (idPanier.getText().isEmpty() || total.getText().isEmpty() ||
                dateCreation.getValue() == null || etatComboBox.getValue() == null) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis !");
            return;
        }

        int panierId;  // Renommé pour éviter le conflit avec la variable d'instance
        try {
            panierId = Integer.parseInt(idPanier.getText()); // Convertit l'ID du panier en entier
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "ID du panier invalide !");
            return;
        }

        double totalValue;
        try {
            totalValue = Double.parseDouble(total.getText()); // Convertit le total en double
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "Le total doit être un nombre valide !");
            return;
        }

        LocalDate localDate = dateCreation.getValue(); // Récupère la date depuis le DatePicker
        java.sql.Date date = java.sql.Date.valueOf(localDate); // Conversion vers Date SQL
        String etat = etatComboBox.getValue(); // Récupère l'état depuis le ComboBox

        // Création de l'objet Panier avec les données collectées
        Panier panier = new Panier(panierId, date, totalValue, etat); // Utilise panierId ici
        try {
            panierService.modifier(panier); // Appel au service pour modifier le panier
            afficherAlerte("Succès", "Panier modifié avec succès !");
        } catch (SQLException e) {
            afficherAlerte("Erreur", "Erreur lors de la modification : " + e.getMessage());
        }
    }



    @FXML
    void Afficher(ActionEvent event) {
        try {
            // Charge la vue PanierInfo.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PanierInfo.fxml"));
            Parent root = loader.load();
            total.getScene().setRoot(root); // Remplace la scène actuelle par la nouvelle vue
        } catch (IOException e) {
            afficherAlerte("Erreur", "Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }

    private void afficherAlerte(String titre, String message) {
        // Affiche un message d'alerte dans la console
        System.out.println(titre + ": " + message);
    }
}
