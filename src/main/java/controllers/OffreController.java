package controllers;


import entite.Offre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import services.OffreService;

import java.sql.Date;
import java.util.List;

public class AfficherPublicitec {

    @FXML private TableView<Offre> tableOffres;
    @FXML private TableColumn<Offre, Double> colMontant;
    @FXML private TableColumn<Offre, String> colConditions;
    @FXML private TableColumn<Offre, Date> colDateProposition;
    @FXML private TableColumn<Offre, String> colContact;
    @FXML private Button btnAjouterOffre;

    private OffreService offreService;
    private ObservableList<Offre> offreList;

    // Constructeur
    public AfficherPublicitec() {
        // Initialiser le service d'offres (cela peut être passé par un constructeur)
        this.offreService = new OffreService() {
            @Override
            public Offre getOne(Offre offre) {
                return null;
            }
        };
    }

    @FXML
    private void initialize() {
        // Lier les colonnes de la TableView aux attributs de l'objet Offre
        colMontant.setCellValueFactory(cellData -> cellData.getValue().getMontant());
        colConditions.setCellValueFactory(cellData -> cellData.getValue().getConditions());
        colDateProposition.setCellValueFactory(cellData -> cellData.getValue().getDate_proposition());
        colContact.setCellValueFactory(cellData -> cellData.getValue().getContact());

        // Charger les données dans la table
        loadOffres();

        // Gestion du bouton Ajouter une Offre
        btnAjouterOffre.setOnAction(event -> handleAjouterOffre());
    }

    // Charger toutes les offres depuis le service
    private void loadOffres() {
        List<Offre> offres = offreService.getAll(); // Récupère les offres de la base de données
        offreList = FXCollections.observableArrayList(offres);
        tableOffres.setItems(offreList);
    }

    // Méthode pour ajouter une offre (par exemple, ouvrir une fenêtre ou un formulaire)
    private void handleAjouterOffre() {
        // Implémente ici la logique pour ajouter une offre
        // Par exemple, ouvrir un formulaire pour saisir les détails de l'offre
    }
}
