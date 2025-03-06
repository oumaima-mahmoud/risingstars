package controllers.consommation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import entite.Commande;
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
    void ModifierCommande(ActionEvent event) {
        if (!validateFields()) return;

        try {
            int commandeId = Integer.parseInt(idCommandee.getText());
            int panierId = Integer.parseInt(idPanierr.getText());
            double prix = Double.parseDouble(prixx.getText());
            int quantite = Integer.parseInt(quantitee.getText());
            LocalDate localDate = dateCommandee.getValue();
            java.sql.Date date = java.sql.Date.valueOf(localDate);

            Commande commande = new Commande(commandeId, quantite, date, prix, panierId);
            serviceCommande.modifierCommande(commande);

            afficherAlerte("Succès", "Commande modifiée avec succès !", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            afficherAlerte("Erreur", "Erreur lors de la modification : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void AfficherCommande(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCommande.fxml"));
            Parent root = loader.load();
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("Erreur", "Erreur lors du chargement de la vue : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private boolean validateFields() {
        if (idCommandee.getText().trim().isEmpty() || quantitee.getText().trim().isEmpty() ||
                prixx.getText().trim().isEmpty() || idPanierr.getText().trim().isEmpty() ||
                dateCommandee.getValue() == null) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis !", Alert.AlertType.WARNING);
            return false;
        }

        return validateNumericField(idCommandee, "ID de la commande") &&
                validateNumericField(idPanierr, "ID du panier") &&
                validateNumericField(quantitee, "La quantité") &&
                validateDoubleField(prixx, "Le prix") &&
                validateDate();
    }

    private boolean validateNumericField(TextField field, String fieldName) {
        try {
            int value = Integer.parseInt(field.getText());
            if (value <= 0) {
                afficherAlerte("Erreur", fieldName + " doit être un entier positif !", Alert.AlertType.WARNING);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", fieldName + " doit être un nombre entier valide !", Alert.AlertType.WARNING);
            return false;
        }
    }

    private boolean validateDoubleField(TextField field, String fieldName) {
        try {
            double value = Double.parseDouble(field.getText());
            if (value <= 0) {
                afficherAlerte("Erreur", fieldName + " doit être supérieur à zéro !", Alert.AlertType.WARNING);
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", fieldName + " doit être un nombre valide !", Alert.AlertType.WARNING);
            return false;
        }
    }

    private boolean validateDate() {
        LocalDate date = dateCommandee.getValue();
        if (date.isAfter(LocalDate.now())) {
            afficherAlerte("Erreur", "La date ne peut pas être dans le futur !", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void afficherAlerte(String titre, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
