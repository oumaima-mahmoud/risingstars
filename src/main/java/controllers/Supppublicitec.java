package controllers;

import entite.Offre;
import entite.Publicite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import services.PubliciteService;

import java.sql.Date;
import java.util.List;

public class Supppublicitec {

    @FXML private ComboBox<Publicite> comboPublicites;
    @FXML private Button btnSupprimer;
    @FXML private Button btnAnnuler;

    private PubliciteService publiciteService;
    private ObservableList<Publicite> publicitesList;

    // Constructeur
    public Supppublicitec() {
        this.publiciteService = new PubliciteService() {
            @Override
            public boolean supprimer(int id) {
                return false;
            }

            @Override
            public Offre getOne(int id) {
                return null;
            }

            @Override
            public void ajouterPublicite(String titre, String description, String mediaUrl, Date date, String statut, int idAnnonceur) {

            }

            @Override
            public List<Publicite> getAllPublicites() {
                return List.of();
            }
        };
    }

    @FXML
    private void initialize() {
        // Charger les publicités dans le ComboBox
        loadPublicites();

        // Action du bouton Supprimer
        btnSupprimer.setOnAction(event -> handleSupprimerPublicite());

        // Action du bouton Annuler
        btnAnnuler.setOnAction(event -> handleAnnuler());
    }

    // Charger les publicités dans le ComboBox
    private void loadPublicites() {
        publicitesList = FXCollections.observableArrayList(publiciteService.getAllPublicites());
        comboPublicites.setItems(publicitesList);
    }

    // Méthode pour supprimer la publicité sélectionnée
    private void handleSupprimerPublicite() {
        Publicite selectedPublicite = comboPublicites.getSelectionModel().getSelectedItem();
        if (selectedPublicite != null) {
            boolean isDeleted = publiciteService.supprimer(selectedPublicite);
            if (isDeleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText("Publicité supprimée");
                alert.setContentText("La publicité a été supprimée avec succès.");
                alert.showAndWait();
                loadPublicites(); // Recharger la liste après suppression
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Échec de la suppression");
                alert.setContentText("Une erreur est survenue lors de la suppression de la publicité.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Aucune publicité sélectionnée");
            alert.setContentText("Veuillez sélectionner une publicité à supprimer.");
            alert.showAndWait();
        }
    }

    // Méthode pour annuler la suppression et fermer la fenêtre
    private void handleAnnuler() {
        // Logique pour fermer la fenêtre (par exemple, revenir à l'écran précédent)
        // Cela peut être fait en fermant la fenêtre ou en revenant au contrôleur principal
    }
}
