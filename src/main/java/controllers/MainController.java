package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    public void openGestionReclamation() {

        openWindow("/adminReclamation.fxml", "Gestion des Réclamations");
    }

    @FXML
    public void openOffre() {
        openWindow("/OffreInterface.fxml", "Gestion des Offres");
    }

    @FXML
    public void openPublicite() {
        openWindow("/main_publicite.fxml", "Gestion des Publicités");
    }

    private void openWindow(String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void openGestionPanier() {

        openWindow("/AjouterPanier.fxml", "Gestion des Paniers");
    }

    @FXML
    public void openGestionCommande() {

        openWindow("/AjouterCommande.fxml", "Gestion des Commandes");
    }
}
