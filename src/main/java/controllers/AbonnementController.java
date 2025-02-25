package controllers;

import entities.Abonnement;
import entities.StatutAbonnement;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Services.AbonnementService;

import java.util.Date;

public class AbonnementController {

    // Déclaration du ToggleGroup pour les RadioButton
    @FXML
    private ToggleGroup abonnementType;

    @FXML
    private RadioButton standardRadio;
    @FXML
    private RadioButton premiumRadio;
    @FXML
    private RadioButton vipRadio;

    @FXML
    private DatePicker dateDebutField;
    @FXML
    private DatePicker dateFinField;
    @FXML
    private TextField tarifField;

    @FXML
    private Button ajouterButton;

    private String tarifStandard = "20DT"; // Nouveau tarif en String
    private String tarifPremium = "50DT"; // Nouveau tarif en String
    private String tarifVIP = "75DT"; // Nouveau tarif en String

    private AbonnementService abonnementService = new AbonnementService();

    @FXML
    public void initialize() {
        abonnementType = new ToggleGroup();

        standardRadio.setToggleGroup(abonnementType);
        premiumRadio.setToggleGroup(abonnementType);
        vipRadio.setToggleGroup(abonnementType);

        standardRadio.setSelected(true); // Par défaut, sélectionne "Standard"
        updateAbonnementDetails(); // Met à jour les détails en fonction du type sélectionné
    }

    /**
     * Met à jour les informations de l'abonnement en fonction du type sélectionné
     */
    @FXML
    private void updateAbonnementDetails() {
        if (standardRadio.isSelected()) {
            tarifField.setText(tarifStandard); // Mise à jour avec String
        } else if (premiumRadio.isSelected()) {
            tarifField.setText(tarifPremium); // Mise à jour avec String
        } else if (vipRadio.isSelected()) {
            tarifField.setText(tarifVIP); // Mise à jour avec String
        }
    }

    /**
     * Ajoute un abonnement pour l'utilisateur connecté
     */
    @FXML
    private void ajouterAbonnement() {
        try {
            // Vérifier que la date de début est avant la date de fin
            if (dateDebutField.getValue().isAfter(dateFinField.getValue())) {
                showAlert("Erreur", "La date de début doit être avant la date de fin.", Alert.AlertType.ERROR);
                return;
            }

            String typeAbonnement = "Standard"; // Par défaut
            if (premiumRadio.isSelected()) {
                typeAbonnement = "Premium";
            } else if (vipRadio.isSelected()) {
                typeAbonnement = "VIP";
            }

            Date dateDebut = java.sql.Date.valueOf(dateDebutField.getValue());
            Date dateFin = java.sql.Date.valueOf(dateFinField.getValue());
            String tarif = tarifField.getText(); // Traitement en String

            // L'ID de l'utilisateur sera récupéré depuis le profil ou la session de l'utilisateur connecté
            int idUser = getCurrentUserId();

            // L'abonnement est ajouté pour l'utilisateur actuel
            Abonnement abonnement = new Abonnement(0, typeAbonnement, dateDebut, dateFin, tarif, StatutAbonnement.ACTIF, 0, idUser);
            abonnementService.ajouter(abonnement);

            // Mettre à jour le statut et les points de fidélité dans le profil utilisateur
            updateUserProfile(idUser);

            // Nettoyer les champs après ajout
            dateDebutField.setValue(null);
            dateFinField.setValue(null);
            tarifField.clear();

            showAlert("Succès", "Abonnement ajouté avec succès", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur", "Veuillez vérifier les données saisies.", Alert.AlertType.ERROR);
        }
    }

    private int getCurrentUserId() {
        return 1; // Remplace cette valeur par la logique réelle
    }

    private void updateUserProfile(int userId) {
        // Logique de mise à jour du profil utilisateur
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
