package controllers.consommation;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import entite.Commande;
import services.ServiceCommande;

import java.sql.SQLException;
import java.util.List;

public class RechercherCommande {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Commande> tableview;

    @FXML
    private TableColumn<Commande, String> dateCommande;

    @FXML
    private TableColumn<Commande, Double> prix;

    @FXML
    private TableColumn<Commande, Integer> quantite;

    @FXML
    private TableColumn<Commande, Integer> idPanier;

    private final ServiceCommande commandeservice = new ServiceCommande();

    @FXML
    public void initialize() {
        // Configuration des colonnes de la TableView
        dateCommande.setCellValueFactory(cellData -> cellData.getValue().dateCommandeProperty());
        prix.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject());
        quantite.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty().asObject());
        idPanier.setCellValueFactory(cellData -> cellData.getValue().idPanierProperty().asObject());

        chargerCommandes();  // Charger toutes les commandes au démarrage
    }

    @FXML
    private void rechercher() {
        String searchTerm = searchField.getText().trim();
        System.out.println("Term recherché : " + searchTerm);

        if (searchTerm.isEmpty()) {
            chargerCommandes();
            return;
        }

        try {
            int idRecherche = Integer.parseInt(searchTerm); // Convertir en entier
            List<Commande> commandesTrouvees = commandeservice.rechercherParID(searchTerm);

            if (commandesTrouvees.isEmpty()) {
                afficherAlerte("Aucun résultat", "Aucune commande trouvée pour cet ID.");
                System.out.println("Aucune commande trouvée.");
            } else {
                System.out.println("Résultats trouvés : " + commandesTrouvees.size());
            }

            ObservableList<Commande> observableList = FXCollections.observableArrayList(commandesTrouvees);
            tableview.setItems(observableList);

        } catch (NumberFormatException e) {
            afficherAlerte("Entrée invalide", "Veuillez entrer un ID valide (nombre entier).");
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur de recherche", "Impossible d'effectuer la recherche !");
        }
    }

    private void chargerCommandes() {
        try {
            List<Commande> commandes = commandeservice.afficherCommandes();
            ObservableList<Commande> observableList = FXCollections.observableArrayList(commandes);
            tableview.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur de chargement", "Impossible de charger les commandes !");
        }
    }

    private void afficherAlerte(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
