package controllers;

import entite.Reclamation;
import java.io.IOException;
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
    private Button btnAjouter;
    @FXML
    private Button btnAjouterAutoReponse; // New button for auto-response
    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> typeFilter;
    @FXML
    private ComboBox<String> etatFilter;
    @FXML
    private Button prevPageButton;
    @FXML
    private Button nextPageButton;
    @FXML
    private Label pageLabel;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private Label sentimentLabel;
    @FXML
    private Label autoResponseLabel;
    @FXML
    private Button btnTextToSpeech;
    @FXML
    private Button btnGenerateImage;
    @FXML
    private ImageView imageView; // Add an ImageView to your FXML file




    private ReclamationService reclamationService = new ReclamationService();
    private HuggingFaceAPI huggingFaceAPI = new HuggingFaceAPI();
    private int currentPage = 0;
    private int pageSize = 10;

    @FXML
    public void initialize() {
        // Initialize filters
        typeFilter.getItems().addAll("All", "Application", "Finance", "Commande");
        etatFilter.getItems().addAll("All", "en_attente", "en_cours", "termine");
        typeFilter.setValue("All");
        etatFilter.setValue("All");

        loadReclamationsPaginated();

        // Add a listener to the ListView to analyze sentiment when a reclamation is selected
        reclamationListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnSupprimer.setDisable(newSelection == null);
            btnModifier.setDisable(newSelection == null);
            btnVoirReponses.setDisable(newSelection == null);
            btnAjouterAutoReponse.setDisable(newSelection == null); // Disable auto-response button if no reclamation is selected

            // Analyze sentiment when a reclamation is selected
            if (newSelection != null) {
                // Analyze sentiment
                String sentiment = huggingFaceAPI.analyzeSentiment(newSelection.getDescription());
                sentimentLabel.setText("Sentiment: " + sentiment);

                // Generate and display auto-response
                String autoResponse = huggingFaceAPI.generateAutoResponse(sentiment);
                autoResponseLabel.setText("Auto-Response: " + autoResponse);
            }
        });
    }

    // Load reclamations with pagination
    public void loadReclamationsPaginated() {
        List<Reclamation> reclamations = reclamationService.getReclamationsPaginated(currentPage * pageSize, pageSize);
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
                            " | User ID: " + reclamation.getUserId() +
                            " | Type: " + reclamation.getType() +
                            " | Description: " + reclamation.getDescription() +
                            " | Objet: " + reclamation.getObjet() +
                            " | Etat: " + reclamation.getEtat() +
                            " | Date: " + reclamation.getDateReclamation() +
                            " | Phone: " + reclamation.getPhoneNumber();
                    setText(text);
                    setStyle("-fx-text-fill: #333; -fx-font-size: 14px;");
                }
            }
        });
        pageLabel.setText("Page " + (currentPage + 1));
    }

    // Handle search and filtering
    @FXML
    private void handleSearch(ActionEvent event) {
        String keyword = searchField.getText();
        String type = typeFilter.getValue().equals("All") ? "" : typeFilter.getValue();
        String etat = etatFilter.getValue().equals("All") ? "" : etatFilter.getValue();

        List<Reclamation> reclamations = reclamationService.searchReclamations(keyword, type, etat);
        ObservableList<Reclamation> reclamationList = FXCollections.observableArrayList(reclamations);
        reclamationListView.setItems(reclamationList);
    }

    // Handle pagination (previous page)
    @FXML
    private void handlePrevPage(ActionEvent event) {
        if (currentPage > 0) {
            currentPage--;
            loadReclamationsPaginated();
        }
    }

    // Handle pagination (next page)
    @FXML
    private void handleNextPage(ActionEvent event) {
        currentPage++;
        loadReclamationsPaginated();
    }

    // Refresh the list
    public void refreshReclamationList() {
        loadReclamationsPaginated();
    }

    @FXML
    private void supprimerReclamation(ActionEvent event) {
        Reclamation selectedReclamation = reclamationListView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            reclamationService.supprimer(selectedReclamation.getId());
            loadReclamationsPaginated();
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddReclamation.fxml"));
            Parent root = loader.load();

            AddReclamationController addReclamationController = loader.getController();
            addReclamationController.setAdminController(this);

            Stage stage = new Stage();
            stage.setTitle("Ajouter Réclamation");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir la fenêtre d'ajout.");
        }
    }

    @FXML
    private void ajouterAutoReponse(ActionEvent event) {
        Reclamation selectedReclamation = reclamationListView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            // Analyze sentiment
            String sentiment = huggingFaceAPI.analyzeSentiment(selectedReclamation.getDescription());
            String autoResponse = huggingFaceAPI.generateAutoResponse(sentiment);

            // Save the auto-response to the database
            ReponseService reponseService = new ReponseService();
            reponseService.ajouterAutoReponse(selectedReclamation.getId(), autoResponse, "Auto-Response");

            // Show success message
            showAlert("Succès", "Auto-réponse ajoutée avec succès !");
        } else {
            showAlert("Erreur", "Veuillez sélectionner une réclamation pour ajouter une auto-réponse.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void textToSpeech(String text) {
        try {
            // Build the command
            String command = "say \"" + text + "\"";

            // Execute the command
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor(); // Wait for the speech to finish
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void convertToSpeech(ActionEvent event) {
        Reclamation selectedReclamation = reclamationListView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            // Get the reclamation description
            String description = selectedReclamation.getDescription();

            // Call the text-to-speech method
            textToSpeech(description);

            // Show success message
            showAlert("Succès", "La réclamation a été convertie en audio avec succès !");
        } else {
            showAlert("Erreur", "Veuillez sélectionner une réclamation pour la convertir en audio.");
        }
    }
    @FXML
    private void generateImage(ActionEvent event) {
        Reclamation selectedReclamation = reclamationListView.getSelectionModel().getSelectedItem();
        if (selectedReclamation != null) {
            // Get the reclamation description
            String description = selectedReclamation.getDescription();

            // Define the output file path
            String outputFile = "output.png"; // Save in the project root directory

            // Call the image generation method
            boolean success = huggingFaceAPI.generateImage(description, outputFile);

            if (success) {
                // Load and display the generated image
                Image image = new Image("file:" + outputFile);
                imageView.setImage(image);

                // Show success message
                showAlert("Succès", "L'image a été générée avec succès !");
            } else {
                showAlert("Erreur", "La génération de l'image a échoué. Veuillez réessayer.");
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner une réclamation pour générer une image.");
        }
    }


}