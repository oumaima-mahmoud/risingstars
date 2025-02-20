package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import models.Commande;
import services.ServiceCommande;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModifierCommande {

    @FXML
    private DatePicker dateCommandee;

    @FXML
    private TextField idCommandee;

    @FXML
    private TextField idPanierr;

    @FXML
    private TextField prixx;

    @FXML
    private TextField quantitee;

    private final ServiceCommande serviceCommande = new ServiceCommande();

    @FXML
    void Modifier(ActionEvent event) {
        if (!validateFields()) {
            return;
        }

        int commandeId = Integer.parseInt(idCommandee.getText());
        int panierId = Integer.parseInt(idPanierr.getText());
        double prix = Double.parseDouble(prixx.getText());
        int quantite = Integer.parseInt(quantitee.getText());
        LocalDate localDate = dateCommandee.getValue();
        java.sql.Date date = java.sql.Date.valueOf(localDate);

        Commande commande = new Commande(commandeId, quantite, date, prix, panierId);

        try {
            serviceCommande.modifierCommande(commande);
            afficherAlerte("Succès", "Commande modifiée avec succès !", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            afficherAlerte("Erreur", "Erreur lors de la modification : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void Afficher(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CommandeInfo.fxml"));
            Parent root = loader.load();
            prixx.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("Erreur", "Erreur lors du chargement de la vue : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validateFields() {
        // Vérification des champs vides
        if (idCommandee.getText().trim().isEmpty() || quantitee.getText().trim().isEmpty() ||
                prixx.getText().trim().isEmpty() || idPanierr.getText().trim().isEmpty() ||
                dateCommandee.getValue() == null) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis !", Alert.AlertType.WARNING);
            return false;
        }

        // Vérification des formats numériques
        if (!isInteger(idCommandee.getText(), "ID de la commande")) return false;
        if (!isInteger(idPanierr.getText(), "ID du panier")) return false;
        if (!isDouble(prixx.getText(), "Le prix")) return false;
        if (!isInteger(quantitee.getText(), "La quantité")) return false;

        int commandeId = Integer.parseInt(idCommandee.getText());
        int panierId = Integer.parseInt(idPanierr.getText());
        double prix = Double.parseDouble(prixx.getText());
        int quantite = Integer.parseInt(quantitee.getText());

        // Vérification des valeurs positives
        if (commandeId <= 0) {
            afficherAlerte("Erreur", "L'ID de la commande doit être positif !", Alert.AlertType.WARNING);
            return false;
        }
        if (panierId <= 0) {
            afficherAlerte("Erreur", "L'ID du panier doit être positif !", Alert.AlertType.WARNING);
            return false;
        }
        if (prix <= 0) {
            afficherAlerte("Erreur", "Le prix doit être supérieur à zéro !", Alert.AlertType.WARNING);
            return false;
        }
        if (quantite <= 0) {
            afficherAlerte("Erreur", "La quantité doit être un nombre positif !", Alert.AlertType.WARNING);
            return false;
        }

        // Vérification de la date
        LocalDate date = dateCommandee.getValue();
        if (date.isAfter(LocalDate.now())) {
            afficherAlerte("Erreur", "La date ne peut pas être dans le futur !", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    private boolean isInteger(String value, String fieldName) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", fieldName + " doit être un nombre entier valide !", Alert.AlertType.WARNING);
            return false;
        }
    }

    private boolean isDouble(String value, String fieldName) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", fieldName + " doit être un nombre valide !", Alert.AlertType.WARNING);
            return false;
        }
    }

    private void afficherAlerte(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
