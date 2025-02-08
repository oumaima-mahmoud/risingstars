package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.ReclamationService;
import entite.Reclamation;

import java.util.List;

public class ReclamationController {
    @FXML
    private TextField userIdField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField objetField;
    @FXML
    private TextField etatField;
    @FXML
    private TextArea displayArea;

    private ReclamationService reclamationService = new ReclamationService();

    @FXML
    private void addReclamation() {
        try {
            int userId = Integer.parseInt(userIdField.getText());
            String type = typeField.getText();
            String description = descriptionField.getText();
            String objet = objetField.getText();
            String etat = etatField.getText();

            // Création d'une réclamation avec les valeurs des champs
            Reclamation reclamation = new Reclamation(userId, type, description, objet, etat);
            reclamationService.ajouter(reclamation);
            displayArea.appendText("Réclamation ajoutée avec succès !\n");
        } catch (NumberFormatException e) {
            displayArea.appendText("Erreur : User ID doit être un nombre valide.\n");
        }
    }

    @FXML
    private void showReclamations() {
        displayArea.clear();

        // Récupération des réclamations sans créer une instance de Reclamation
        List<Reclamation> reclamations = reclamationService.getAll(new Reclamation(0, "", "", "", ""));

        for (Reclamation rec : reclamations) {
            displayArea.appendText("ID: " + rec.getId() + "\n");
            displayArea.appendText("User ID: " + rec.getUserId() + "\n");
            displayArea.appendText("Type: " + rec.getType() + "\n");
            displayArea.appendText("Description: " + rec.getDescription() + "\n");
            displayArea.appendText("Objet: " + rec.getObjet() + "\n");
            displayArea.appendText("État: " + rec.getEtat() + "\n");
            displayArea.appendText("Date: " + rec.getDateReclamation() + "\n");
            displayArea.appendText("----------------------------\n");
        }
    }
}
