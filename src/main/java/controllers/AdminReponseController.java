package controllers;

import entite.Reponse;
import services.ReponseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class AdminReponseController {

    @FXML
    private ListView<Reponse> reponseListView;

    private ReponseService reponseService = new ReponseService();
    private int reclamationId;

    public void setReclamationId(int reclamationId) {
        this.reclamationId = reclamationId;
        loadReponses();
    }

    public void loadReponses() {
        List<Reponse> reponses = reponseService.getReponsesByReclamationId(reclamationId);
        ObservableList<Reponse> observableReponses = FXCollections.observableArrayList(reponses);
        reponseListView.setItems(observableReponses);
    }

    @FXML
    private void addResponse() {
        // Load the AddResponse FXML
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddReponse.fxml"));
            AnchorPane addResponseWindow = loader.load();

            // Get the controller and set the reclamationId
            AddResponseController controller = loader.getController();
            controller.setReclamationId(reclamationId);

            // Create the scene and stage for the new window
            Scene scene = new Scene(addResponseWindow);
            Stage stage = new Stage();
            stage.setTitle("Ajouter une réponse");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modifyResponse() {
        Reponse selectedReponse = reponseListView.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            try {
                // Load the UpdateReponse FXML
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditReponse.fxml"));
                AnchorPane updateResponseWindow = loader.load();

                // Get the controller and set the reponseToEdit
                UpdateReponseController controller = loader.getController();
                controller.setReponseToEdit(selectedReponse);
                controller.setAdminReponseController(this); // Pass AdminReponseController

                // Create the scene and stage for the update window
                Scene scene = new Scene(updateResponseWindow);
                Stage stage = new Stage();
                stage.setTitle("Modifier la réponse");
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner une réponse à modifier.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void deleteResponse() {
        Reponse selectedReponse = reponseListView.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            reponseService.supprimerReponse(selectedReponse.getId());
            loadReponses(); // Refresh the list after deletion
        } else {
            showAlert("Erreur", "Veuillez sélectionner une réponse à supprimer.");
        }
    }

}
