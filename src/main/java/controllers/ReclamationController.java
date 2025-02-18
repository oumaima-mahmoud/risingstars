package controllers;

import entite.Reclamation;
import services.ReclamationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class  ReclamationController {

    @FXML
    private TextField tfType;
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfObjet;
    @FXML
    private Button btnValider;
    @FXML
    private ListView<Reclamation> reclamationListView;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnVoirReponses;
    @FXML
    private Label erreurvalue;

    private ReclamationService reclamationService = new ReclamationService();

    @FXML
    public void initialize() {
        loadReclamations();

        // Customizing the ListView cell factory
        reclamationListView.setCellFactory(listView -> new ListCell<Reclamation>() {
            @Override
            protected void updateItem(Reclamation reclamation, boolean empty) {
                super.updateItem(reclamation, empty);
                if (empty || reclamation == null) {
                    setGraphic(null);
                } else {
                    // Creating the VBox to hold the labels
                    VBox vbox = new VBox(5);
                    vbox.setStyle("-fx-padding: 10; -fx-background-color: #f0f0f0; -fx-border-radius: 10px;");

                    Label typeLabel = new Label("Type: " + reclamation.getType());
                    typeLabel.setStyle("-fx-font-weight: bold;");
                    Label descriptionLabel = new Label("Description: " + reclamation.getDescription());
                    Label objetLabel = new Label("Objet: " + reclamation.getObjet());

                    vbox.getChildren().addAll(typeLabel, descriptionLabel, objetLabel);
                    setGraphic(vbox); // Setting the VBox as the graphic of the cell
                }
            }
        });

        reclamationListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnSupprimer.setDisable(newSelection == null);
            btnModifier.setDisable(newSelection == null);
            btnVoirReponses.setDisable(newSelection == null);
        });
    }

    @FXML
    private void saveReclamation(ActionEvent event) {
        String type = tfType.getText().trim();
        String description = tfDescription.getText().trim();
        String objet = tfObjet.getText().trim();

        if (type.isEmpty() || description.isEmpty() || objet.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        Reclamation reclamation = new Reclamation(1, type, description, objet, "en_attente");
        reclamationService.ajouter(reclamation);

        tfType.clear();
        tfDescription.clear();
        tfObjet.clear();

        loadReclamations();
    }

    @FXML
    private void supprimerReclamation(ActionEvent event) {
        Reclamation selectedReclamation = reclamationListView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            reclamationService.supprimer(selectedReclamation.getId());
            loadReclamations();
        } else {
            showAlert("Erreur", "Veuillez sélectionner une réclamation à supprimer.");
        }
    }

    @FXML
    private void remplirModifierform(ActionEvent event) {
        Reclamation selectedReclamation = reclamationListView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditReclamation.fxml"));
                AnchorPane editForm = loader.load();

                UpdateReclamationController editController = loader.getController();
                editController.setReclamationController(this);
                editController.setReclamationToEdit(selectedReclamation);

                Stage editStage = new Stage();
                editStage.setTitle("Modifier Réclamation");
                editStage.setScene(new Scene(editForm));
                editStage.show();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible de charger la fenêtre de modification.");
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner une réclamation à modifier.");
        }
    }

    @FXML
    private void ouvrirReponses(ActionEvent event) {
        Reclamation selectedReclamation = reclamationListView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reponse.fxml"));
                AnchorPane root = loader.load();

                ReponseController reponseController = loader.getController();
                reponseController.setIdReclamation(selectedReclamation.getId());

                Stage stage = new Stage();
                stage.setTitle("Gestion des Réponses");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                showAlert("Erreur", "Impossible de charger l'interface des réponses.");
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner une réclamation pour voir les réponses.");
        }
    }

    public void loadReclamations() {
        List<Reclamation> reclamations = reclamationService.getAll(new Reclamation());
        ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList(reclamations);
        reclamationListView.setItems(reclamationList);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
