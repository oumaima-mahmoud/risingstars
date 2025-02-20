package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Commande;
import services.ServiceCommande;
import java.sql.SQLException;

public class AfficherCommande {

    @FXML
    private TableColumn<Commande, Integer> Quantite;  // La colonne pour la quantité

    @FXML
    private TableColumn<Commande, java.sql.Date> dateCommande;  // La colonne pour la date de la commande

    @FXML
    private TableColumn<Commande, Integer> idPanier;  // La colonne pour l'ID du panier

    @FXML
    private TableColumn<Commande, Double> prix;  // La colonne pour le prix

    @FXML
    private TableView<Commande> tableview;  // TableView pour afficher les commandes

    private final ServiceCommande commandeService = new ServiceCommande();  // Service pour récupérer les commandes

    @FXML
    public void initialize() {
        // Vérification des injections FXML
        assert Quantite != null : "fx:id=\"Quantite\" was not injected: check your FXML file 'AfficherCommande.fxml'.";
        assert dateCommande != null : "fx:id=\"dateCommande\" was not injected: check your FXML file 'AfficherCommande.fxml'.";
        assert idPanier != null : "fx:id=\"idPanier\" was not injected: check your FXML file 'AfficherCommande.fxml'.";
        assert prix != null : "fx:id=\"prix\" was not injected: check your FXML file 'AfficherCommande.fxml'.";

    }
}
