package controllers;


import entite.Offre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import services.OffreService;

public class SuppOffrec {

    @FXML private ComboBox<Offre> comboOffres;
    @FXML private Button btnSupprimer;
    @FXML private Button btnAnnuler;

    private OffreService offreService;
    private ObservableList<Offre> offresList;

    // Constructeur
    public SuppOffrec() {
        this.offreService = new OffreService() {
            @Override
            public boolean supprimer(int id) {
                return false;
            }

            @Override
            public Offre getOne(Offre offre) {
                return null;
            }
        };
    }

    @FXML
    private void initialize() {
        // Charger les offres dans le ComboBox
        loadOffres();

        // Action du bouton Supprimer
        btnSupprimer.setOnAction(event -> handleSupprimerOffre());

        // Action du bouton Annuler
        btnAnnuler.setOnAction(event -> handleAnnuler());
    }

    // Charger les offres dans le ComboBox
    private void loadOffres() {
        offresList = FXCollections.observableArrayList(offreService.getAll());
        comboOffres.setItems(offresList);
    }

    // Méthode pour supprimer l'offre sélectionnée
    private void handleSupprimerOffre() {
        Offre selectedOffre = comboOffres.getSelectionModel().getSelectedItem();
        if (selectedOffre != null) {
            boolean isDeleted = offreService.supprimer(selectedOffre);
            if (isDeleted) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText("Offre supprimée");
                alert.setContentText("L'offre a été supprimée avec succès.");
                alert.showAndWait();
                loadOffres(); // Recharger la liste après suppression
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Échec de la suppression");
                alert.setContentText("Une erreur est survenue lors de la suppression de l'offre.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Aucune offre sélectionnée");
            alert.setContentText("Veuillez sélectionner une offre à supprimer.");
            alert.showAndWait();
        }
    }

    // Méthode pour annuler la suppression et fermer la fenêtre
    private void handleAnnuler() {
        // Logique pour fermer la fenêtre ou revenir à l'écran précédent
        // Cela peut être fait en fermant la fenêtre ou en revenant au contrôleur principal
    }
}
