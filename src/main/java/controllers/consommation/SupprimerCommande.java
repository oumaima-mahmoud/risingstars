package controllers.consommation;

import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import services.ServiceCommande;

public class SupprimerCommande {

    @FXML
    private TextField idCommandes;

    private final ServiceCommande commandeService = new ServiceCommande();

    @FXML
    void supprimer(ActionEvent event) {
        // Vérifier si l'ID de la commande est vide
        if (idCommandes.getText().isEmpty()) {
            afficherAlerte("Erreur", "Veuillez entrer l'ID de la commande à supprimer !");
            return;
        }

        // Récupérer l'ID de la commande depuis le champ TextField
        int id;
        try {
            id = Integer.parseInt(idCommandes.getText());  // Convertir l'ID en entier
        } catch (NumberFormatException e) {
            afficherAlerte("Erreur", "ID de la commande invalide !");
            return;
        }

        // Vérifier si la commande existe
        try {
            if (!commandeService.verifierCommandeExistante(id)) {
                afficherAlerte("Erreur", "La commande avec cet ID n'existe pas !");
                return;
            }

            // Appeler la méthode du service pour supprimer la commande
            commandeService.supprimerCommande(id);  // Appel à l'instance du service pour supprimer la commande
            afficherAlerte("Succès", "Commande supprimée avec succès !");
        } catch (SQLException e) {
            afficherAlerte("Erreur", "Erreur lors de la suppression : " + e.getMessage());
        }
    }

    // Méthode pour afficher une alerte
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        if (titre.equals("Erreur")) {
            alert.setAlertType(AlertType.ERROR);  // Si c'est une erreur, utiliser un alert de type ERROR
        }
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
