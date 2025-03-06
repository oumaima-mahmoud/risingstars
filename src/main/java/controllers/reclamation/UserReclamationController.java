package controllers.reclamation;

import entite.Reclamation;
import java.io.IOException;
import services.PDFExportService;
import services.ReclamationService;
import services.ReponseService;
import services.HuggingFaceAPI;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;
import tools.SceneManager;
import tools.SessionManager;
import java.util.stream.Collectors;

public class UserReclamationController {

    @FXML
    private ListView<Reclamation> reclamationListView;
    @FXML
    private Button btnSupprimer;
    @FXML
    private Button btnModifier;
    @FXML
    private Button btnVoirReponses;
    @FXML
    private Button btnAjouter;
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> typeFilter;
    @FXML
    private ComboBox<String> etatFilter;
    @FXML
    private Label sentimentLabel;
    @FXML
    private Button backToMainButton;

    private ReclamationService reclamationService = new ReclamationService();

    @FXML
    public void initialize() {
        // Initialize filters
        typeFilter.getItems().addAll("All", "Application", "Finance", "Commande");
        etatFilter.getItems().addAll("All", "en_attente", "en_cours", "termine");
        typeFilter.setValue("All");
        etatFilter.setValue("All");

        // Load reclamations for the logged-in user
        loadUserReclamations();

        // Add a listener to the ListView to enable/disable buttons
        reclamationListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnSupprimer.setDisable(newSelection == null);
            btnModifier.setDisable(newSelection == null);
            btnVoirReponses.setDisable(newSelection == null);
        });
    }

    // Load reclamations for the logged-in user
    private void loadUserReclamations() {
        int userId = SessionManager.getCurrentUser().getIdUser();        List<Reclamation> reclamations = reclamationService.getReclamationsByUserId(userId);
        ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList(reclamations);
        reclamationListView.setItems(reclamationList);

        reclamationListView.setCellFactory(listView -> new javafx.scene.control.ListCell<Reclamation>() {
            @Override
            protected void updateItem(Reclamation reclamation, boolean empty) {
                super.updateItem(reclamation, empty);
                if (empty || reclamation == null) {
                    setText(null);
                } else {
                    String text = "ID: " + reclamation.getId() +
                            " | Type: " + reclamation.getType() +
                            " | Description: " + reclamation.getDescription() +
                            " | Objet: " + reclamation.getObjet() +
                            " | Etat: " + reclamation.getEtat() +
                            " | Date: " + reclamation.getDateReclamation();
                    setText(text);
                    setStyle("-fx-text-fill: #333; -fx-font-size: 14px;");
                }
            }
        });
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String keyword = searchField.getText();
        String type = typeFilter.getValue().equals("All") ? "" : typeFilter.getValue();
        String etat = etatFilter.getValue().equals("All") ? "" : etatFilter.getValue();

        int userId = SessionManager.getCurrentUser().getIdUser();
        List<Reclamation> reclamations = reclamationService.searchReclamations(keyword, type, etat)
                .stream()
                .filter(rec -> rec.getUserId() == userId) // Filter by user ID
                .collect(Collectors.toList());

        ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList(reclamations);
        reclamationListView.setItems(reclamationList);
    }

    @FXML
    private void supprimerReclamation(ActionEvent event) {
        Reclamation selectedReclamation = reclamationListView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            reclamationService.supprimer(selectedReclamation.getId());
            loadUserReclamations();
        } else {
            showAlert("Erreur", "Veuillez sélectionner une réclamation à supprimer.");
        }
    }

    @FXML
    private void remplirModifierform(ActionEvent event) {
        Reclamation selectedReclamation = reclamationListView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamation/ModifierReclamation.fxml"));
                Parent root = loader.load();

                ModifierReclamationController modifierController = loader.getController();
                modifierController.setReclamation(selectedReclamation);
                modifierController.setUserController(this);

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamation/UserReponse.fxml"));
                Parent root = loader.load();

                // Cast the controller to UserReponseController
                UserReponseController reponseController = loader.getController();
                reponseController.setReclamationId(selectedReclamation.getId());

                Stage stage = new Stage();
                stage.setTitle("Réponses");
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamation/AddReclamation.fxml"));
            Parent root = loader.load();

            AddReclamationController addReclamationController = loader.getController();
            addReclamationController.setUserController(this);

            Stage stage = new Stage();
            stage.setTitle("Ajouter Réclamation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir la fenêtre d'ajout.");
        }
    }

    @FXML
    public void goBackToMain() {
        try {
            Stage stage = (Stage) backToMainButton.getScene().getWindow();
            SceneManager.switchScene(stage, "/mainwindow.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}