package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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
    private ComboBox<String> etatComboBox;

    @FXML
    private TextField idPanier;

    @FXML
    private TextField total;

    private final ServicePanier panierService = new ServicePanier();

    @FXML
    void initialize() {
        etatComboBox.getItems().addAll("Annulé", "En cours", "Validé");
        etatComboBox.setEditable(false);
    }

    @FXML
    void Modifier(ActionEvent event) {
        if (idPanier.getText().isEmpty() || total.getText().isEmpty() ||
                dateCreation.getValue() == null || etatComboBox.getValue() == null) {
            afficherAlerte("Erreur", "Tous les champs doivent être remplis !", Alert.AlertType.ERROR);
            return;
        }

        int panierId;
        try {
            panierId = Integer.parseInt(idPanier.getText());
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "ID du panier invalide !", Alert.AlertType.ERROR);
            return;
        }

        double totalValue;
        try {
            totalValue = Double.parseDouble(total.getText());
            if (totalValue < 0) {
                afficherAlerte("Erreur", "Le total doit être un nombre positif !", Alert.AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "Le total doit être un nombre valide !", Alert.AlertType.ERROR);
            return;
        }

        LocalDate localDate = dateCreation.getValue();
        java.sql.Date date = java.sql.Date.valueOf(localDate);
        String etat = etatComboBox.getValue();

        Panier panier = new Panier(panierId, date, totalValue, etat);
        try {
            panierService.modifier(panier);
            afficherAlerte("Succès", "Panier modifié avec succès !", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            afficherAlerte("Erreur", "Erreur lors de la modification : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void AfficherPanier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PanierInfo.fxml"));
            Parent root = loader.load();
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("Erreur", "Erreur lors du chargement de la vue : " + e.getMessage(), Alert.AlertType.ERROR);
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
