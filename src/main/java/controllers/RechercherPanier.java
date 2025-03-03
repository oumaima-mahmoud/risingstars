package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import entite.Panier;
import services.ServicePanier;

import java.sql.SQLException;
import java.util.List;

public class RechercherPanier {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Panier> tableview;

    @FXML
    private TableColumn<Panier, String> dateCreation;

    @FXML
    private TableColumn<Panier, Double> total;

    @FXML
    private TableColumn<Panier, String> etat;

    private final ServicePanier panierService = new ServicePanier();

    @FXML
    public void initialize() {
        // Configuration des colonnes de la TableView
        dateCreation.setCellValueFactory(cellData -> cellData.getValue().dateCreationProperty());
        total.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        etat.setCellValueFactory(cellData -> cellData.getValue().etatProperty());

        chargerPaniers();  // Charger tous les paniers au démarrage
    }

    @FXML
    private void rechercher() {
        String searchTerm = searchField.getText().trim();  // Retirer les espaces inutiles
        System.out.println("Term recherché : " + searchTerm);  // Afficher ce que l'utilisateur a saisi

        if (searchTerm.isEmpty()) {
            chargerPaniers();  // Charger tous les paniers si le champ est vide
        } else {
            // Effectuer la recherche dans la base de données
            try {
                List<Panier> paniersTrouves = panierService.rechercherParID(searchTerm);
                if (paniersTrouves.isEmpty()) {
                    afficherAlerte("Aucun résultat", "Aucun panier trouvé pour ce terme de recherche.");
                    System.out.println("Aucun panier trouvé.");  // Afficher dans la console
                } else {
                    System.out.println("Résultats trouvés : " + paniersTrouves.size());  // Afficher le nombre de résultats
                }
                ObservableList<Panier> observableList = FXCollections.observableArrayList(paniersTrouves);
                tableview.setItems(observableList);
            } catch (SQLException e) {
                e.printStackTrace();
                afficherAlerte("Erreur de recherche", "Impossible d'effectuer la recherche !");
            }
        }
    }


    private void chargerPaniers() {
        try {
            List<Panier> paniers = panierService.getAll();  // Récupérer tous les paniers
            ObservableList<Panier> observableList = FXCollections.observableArrayList(paniers);
            tableview.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur de chargement", "Impossible de charger les paniers !");
        }
    }

    private void afficherAlerte(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
