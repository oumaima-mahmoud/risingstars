package controllers.consommation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import entite.Panier;
import services.ServicePanier;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class AjouterPanier {

    @FXML
    private TextField totalTextField;

    @FXML
    private ComboBox<String> etatComboBox;

    @FXML
    private DatePicker dateCreationDatePicker;

    @FXML
    void initialize() {
        etatComboBox.getItems().addAll("Annulé", "En cours", "Validé");
        etatComboBox.setEditable(false);
    }

    @FXML
    void Ajouter(ActionEvent event) {
        // Récupération des valeurs saisies
        String total = totalTextField.getText().trim();
        String etat = etatComboBox.getValue();
        LocalDate dateCreation = dateCreationDatePicker.getValue();

        // ✅ Vérification des champs vides
        if (total.isEmpty()) {
            afficherAlerte("Erreur", "Veuillez entrer un total !");
            return;
        }
        if (etat == null) {
            afficherAlerte("Erreur", "Veuillez sélectionner un état !");
            return;
        }
        if (dateCreation == null) {
            afficherAlerte("Erreur", "Veuillez sélectionner une date de création !");
            return;
        }

        // ✅ Vérification du format du total (nombre valide avec max 2 décimales)
        if (!Pattern.matches("^\\d+(\\.\\d{1,2})?$", total)) {
            afficherAlerte("Erreur", "Le total doit être un nombre positif avec au maximum deux décimales !");
            return;
        }

        double totalDouble = Double.parseDouble(total);
        if (totalDouble <= 0) {
            afficherAlerte("Erreur", "Le total doit être un nombre positif supérieur à zéro !");
            return;
        }

        // ✅ Vérification de l'état sélectionné
        if (!etat.equals("Annulé") && !etat.equals("En cours") && !etat.equals("Validé")) {
            afficherAlerte("Erreur", "L'état sélectionné est invalide !");
            return;
        }

        // ✅ Vérification de la date (pas de date future)
        if (dateCreation.isAfter(LocalDate.now())) {
            afficherAlerte("Erreur", "La date ne peut pas être dans le futur !");
            return;
        }

        // Conversion en java.sql.Date
        java.sql.Date dateSQL = java.sql.Date.valueOf(dateCreation);

        // ✅ Création de l'objet Panier et ajout dans la base de données
        Panier panier = new Panier(dateSQL, totalDouble, etat);
        ServicePanier panierService = new ServicePanier();

        try {
            panierService.ajouter(panier);
            afficherAlerte("Succès", "Le panier a été ajouté avec succès !");
            // Réinitialisation des champs
            totalTextField.clear();
            etatComboBox.getSelectionModel().clearSelection();
            dateCreationDatePicker.setValue(null);
        } catch (SQLException e) {
            afficherAlerte("Erreur", "Erreur lors de l'ajout du panier : " + e.getMessage());
        }
    }

    @FXML
    void Afficher(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/consommation/PanierInfo.fxml"));
            Parent root = loader.load();
            totalTextField.getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("Erreur", "Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }

    // ✅ Méthode pour afficher une alerte
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void Modifier(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/consommation/ModifierPanier.fxml"));
            Parent root = loader.load();
            ((javafx.scene.Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("Erreur", "Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }

    @FXML
    void Supprimer(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/consommation/SupprimerPanier.fxml"));
            Parent root = loader.load();
            ((javafx.scene.Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            afficherAlerte("Erreur", "Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }

}
