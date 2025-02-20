package Controllers;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import services.ServicePanier;

public class SupprimerPanier {

    @FXML
    private TextField idPanier;

    private final ServicePanier panierService = new ServicePanier();

    @FXML
    void supprimer(ActionEvent event) {
        // Vérifier si l'ID du panier est vide
        if (idPanier.getText().isEmpty()) {
            afficherAlerte("Erreur", "Veuillez entrer l'ID du panier à supprimer !");
            return;
        }

        // Récupérer l'ID du panier depuis le champ TextField
        int id;
        try {
            id = Integer.parseInt(idPanier.getText());  // Convertir l'ID en entier
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "ID du panier invalide !");
            return;
        }

        // Appeler la méthode du service pour supprimer le panier
        try {
            panierService.supprimer(id);  // Appel à l'instance du service pour supprimer le panier
            afficherAlerte("Succès", "Panier supprimé avec succès !");
        } catch (SQLException e) {
            afficherAlerte("Erreur", "Erreur lors de la suppression : " + e.getMessage());
        }
    }

    // Méthode pour afficher une alerte
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
