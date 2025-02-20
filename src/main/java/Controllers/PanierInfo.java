package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Panier;
import services.ServicePanier;
import java.sql.SQLException;

public class PanierInfo {

    @FXML
    private TableView<Panier> tableview;

    @FXML
    private TableColumn<Panier, String> dateCreation;

    @FXML
    private TableColumn<Panier, Double> total;

    @FXML
    private TableColumn<Panier, String> etat;

    private ServicePanier panierService = new ServicePanier();

    @FXML
    public void initialize() {
        // Configuration des colonnes de la TableView
        dateCreation.setCellValueFactory(cellData -> cellData.getValue().dateCreationProperty());
        total.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        etat.setCellValueFactory(cellData -> cellData.getValue().etatProperty());

        // Chargement des paniers dans la table
        try {
            ObservableList<Panier> paniers = panierService.getAll(); // Utilisation de ObservableList
            tableview.setItems(paniers);
        } catch (SQLException e) {
            e.printStackTrace();
            // Afficher une alerte ou un message d'erreur
        }
    }
}
