package controllers;

import entite.Reclamation;
import services.ReclamationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class AdminReclamationController {

    @FXML
    private ListView<Reclamation> reclamationListView;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnVoirReponses;
    @FXML
    private Button btnAjouter;  // New Add Button
    @FXML
    private TextField typeTextField;
    @FXML
    private TextField descriptionTextField;
    @FXML
    private TextField objetTextField;
    @FXML
    private TextField etatTextField;

    private ReclamationService reclamationService = new ReclamationService();

    @FXML
    public void initialize() {
        loadReclamations();

        reclamationListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnSupprimer.setDisable(newSelection == null);
            btnModifier.setDisable(newSelection == null);
            btnVoirReponses.setDisable(newSelection == null);
        });
    }

    public void loadReclamations() {
        List<Reclamation> reclamations = reclamationService.getAll(new Reclamation());
        ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList(reclamations);
        reclamationListView.setItems(reclamationList);

        // Customizing how each reclamation is displayed in the ListView
        reclamationListView.setCellFactory(listView -> new javafx.scene.control.ListCell<Reclamation>() {
            @Override
            protected void updateItem(Reclamation reclamation, boolean empty) {
                super.updateItem(reclamation, empty);
                if (empty || reclamation == null) {
                    setText(null);
                } else {
                    // Create a layout for each item to display in a better way
                    String text = "ID: " + reclamation.getId() +
                            " | User ID: " + reclamation.getUserId() +  // Add the userId
                            " | Type: " + reclamation.getType() +
                            " | Objet: " + reclamation.getObjet() +
                            " | Etat: " + reclamation.getEtat() +
                            " | Date: " + reclamation.getDateReclamation();

                    // Adding custom styling and displaying it
                    setText(text);
                    setStyle("-fx-text-fill: #333; -fx-font-size: 14px;");
                }
            }
        });

        // Fade-in effect when the list is loaded
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), reclamationListView);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    public void refreshReclamationList() {
        loadReclamations();  // Reload the reclamations list view
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierReclamation.fxml"));
                Parent root = loader.load();

                ModifierReclamationController modifierController = loader.getController();
                modifierController.setReclamation(selectedReclamation);
                modifierController.setAdminController(this);

                Stage stage = new Stage();
                stage.setTitle("Modifier Réclamation");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
            } catch (IOException e) {
                showAlert("Erreur", "Erreur lors de l'ouverture du formulaire de modification.");
                e.printStackTrace();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admineReponse.fxml"));
                Parent root = loader.load();

                AdminReponseController reponseController = loader.getController();
                reponseController.setReclamationId(selectedReclamation.getId());

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

    @FXML
    private void ajouterReclamation(ActionEvent event) {
        String type = typeTextField.getText();
        String description = descriptionTextField.getText();
        String objet = objetTextField.getText();
        String etat = etatTextField.getText();

        if (type.isEmpty() || description.isEmpty() || objet.isEmpty() || etat.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
        } else {
            // Assuming user_id is 1 for simplicity, but you can replace it with actual user ID
            Reclamation newReclamation = new Reclamation(1, type, description, objet, etat);
            reclamationService.ajouter(newReclamation);
            loadReclamations();  // Reload the reclamations list
            clearForm();  // Clear the form fields

            showAlert("Succès", "Réclamation ajoutée avec succès.");
        }
    }

    private void clearForm() {
        typeTextField.clear();
        descriptionTextField.clear();
        objetTextField.clear();
        etatTextField.clear();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
