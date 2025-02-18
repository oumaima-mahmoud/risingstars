package controllers;

import entite.Reclamation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReclamationDetailsController {

    @FXML
    private Label labelDetails;

    public void setReclamation(Reclamation reclamation) {
        labelDetails.setText("ID: " + reclamation.getId() + "\n" +
                "Type: " + reclamation.getType() + "\n" +
                "Description: " + reclamation.getDescription() + "\n" +
                "Objet: " + reclamation.getObjet() + "\n" +
                "Date: " + reclamation.getDateReclamation() + "\n" +
                "Etat: " + reclamation.getEtat());
    }
}
