package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import entite.Commande;
import entite.Panier;
import services.ServiceCommande;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AfficherCommande {
    @FXML
    private TableView<Commande> tableview;

    @FXML
    private TableColumn<Commande, Integer> idCommande;

    @FXML
    private TableColumn<Commande, Integer> quantite;

    @FXML
    private TableColumn<Commande, String> dateCommande;

    @FXML
    private TableColumn<Commande, Double> prix;

    @FXML
    private TableColumn<Commande, Integer> idPanier;

    private final ServiceCommande commandeService = new ServiceCommande();

    private boolean ascendant;

    @FXML
    public void initialize() {
        // Initialisation des colonnes de la TableView
        idCommande.setCellValueFactory(new PropertyValueFactory<>("idCommande"));
        quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        dateCommande.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        idPanier.setCellValueFactory(new PropertyValueFactory<>("idPanier"));

        // Charger les commandes au démarrage
        chargerCommandes();
    }

    private void chargerCommandes() {
        try {
            List<Commande> commandes = commandeService.afficherCommandes();
            ObservableList<Commande> observableList = FXCollections.observableArrayList(commandes);
            tableview.setItems(observableList);
        } catch (Exception e) {
            afficherAlerte("Erreur de chargement", "Impossible de charger les commandes !\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void rechercher(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RechercherCommande.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Recherche Commande");
            stage.show();
        } catch (IOException e) {
            afficherAlerte("Erreur de recherche", "Erreur lors du chargement de la fenêtre de recherche !\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void trierParPrix(ActionEvent event) {
        try {
            List<Commande> commandesTries = commandeService.trierParPrix(ascendant);
            tableview.setItems(FXCollections.observableArrayList(commandesTries));
            ascendant = !ascendant;
        } catch (Exception e) {  // Attrape toutes les exceptions
            afficherAlerte("Erreur de tri", "Impossible de trier les commandes !\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void statistiquss(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/statistiquesPdfCommande.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("statistiques");
            stage.show();
        } catch (IOException e) {
            afficherAlerte("Erreur de recherche", "Erreur lors du chargement de la fenêtre de recherche !\n" + e.getMessage());
            e.printStackTrace();
        }
    }


    // Méthode générique pour afficher une alerte
    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

