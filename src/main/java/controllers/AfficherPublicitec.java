package controllers;

import entite.Publicite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.PubliciteService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class AfficherPublicitec {

    @FXML
    private TableView<Publicite> tablePublicites;
    @FXML
    private TableColumn<Publicite, Integer> colId;
    @FXML
    private TableColumn<Publicite, String> colTitre;
    @FXML
    private TableColumn<Publicite, String> colDescription;
    @FXML
    private TableColumn<Publicite, LocalDate> colDate;
    @FXML
    private TableColumn<Publicite, String> colStatut;

    private final PubliciteService publiciteService = new PubliciteService();

    @FXML
    public void initialize() {
        // Associer les colonnes aux attributs de Publicite
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId_publicite()).asObject());
        colTitre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitre()));
        colDescription.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription()));
        colDate.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getDate()));
        colStatut.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatut()));

        chargerPublicites();

        // Événement de clic sur une publicité
        tablePublicites.setOnMouseClicked(this::ouvrirAjouterOffre);
    }

    private void chargerPublicites() {
        List<Publicite> publicites = publiciteService.getAll();
        ObservableList<Publicite> observableList = FXCollections.observableArrayList(publicites);
        tablePublicites.setItems(observableList);
    }

    @FXML
    private void ajouterPublicite() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ajouterpublicite.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Ajouter une publicité");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void ouvrirAjouterOffre(MouseEvent event) {
        if (event.getClickCount() == 2) {  // Double-clic
            Publicite selectedPublicite = tablePublicites.getSelectionModel().getSelectedItem();
            if (selectedPublicite != null) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ajouteroffre.fxml"));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(loader.load()));
                    stage.setTitle("Ajouter une offre");

                    // Passer la publicité sélectionnée au contrôleur AjouterOffrec
                    ajouteroffrec controller = loader.getController();
                    controller.setIdPublicite(selectedPublicite);

                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
