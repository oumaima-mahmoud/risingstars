package controllers.consommation;
import javafx.scene.control.Button;

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
import entite.Panier;
import services.ServicePanier;
import tools.SceneManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PanierInfo {
    @FXML
    private TableView<Panier> tableview;

    @FXML
    private TableColumn<Panier, String> dateCreation;

    @FXML
    private TableColumn<Panier, Double> total;

    @FXML
    private TableColumn<Panier, String> etat;
    @FXML
    private TableColumn<Panier, Integer> idproduit;
    @FXML
    private TableColumn<Panier, Integer> id_user;
    @FXML
    private Button backToMainButton; // Injected from FXML


    private final ServicePanier panierService = new ServicePanier();
    private boolean ascendant = true;


    @FXML
    public void initialize() {
        // Initialisation des colonnes de la TableView
        dateCreation.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        idproduit.setCellValueFactory(new PropertyValueFactory<>("idproduit"));
        id_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        // Charger les paniers au démarrage
        chargerPaniers();
    }

    private void chargerPaniers() {
        try {
            List<Panier> paniers = panierService.getAll();
            ObservableList<Panier> observableList = FXCollections.observableArrayList(paniers);
            tableview.setItems(observableList);
        } catch (Exception e) {  // Attrape toutes les exceptions
            afficherAlerte("Erreur de chargement", "Impossible de charger les paniers !\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void trierParTotal(ActionEvent event) {
        try {
            List<Panier> paniersTries = panierService.trierParTotal(ascendant);
            tableview.setItems(FXCollections.observableArrayList(paniersTries));
            ascendant = !ascendant;
        } catch (Exception e) {  // Attrape toutes les exceptions
            afficherAlerte("Erreur de tri", "Impossible de trier les paniers !\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void Rechercher(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/consommation/RechercherPanier.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Recherche Panier");
            stage.show();
        } catch (IOException e) {
            afficherAlerte("Erreur de recherche", "Erreur lors du chargement de la fenêtre de recherche !\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void statistiquss(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/consommation/statistiquesPdf.fxml"));
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
    @FXML
    public void goBackToMain() {
        try {
            // Get the current stage
            Stage stage = (Stage) backToMainButton.getScene().getWindow();
            // Switch back to the main window
            SceneManager.switchScene(stage, "/mainwindow.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
