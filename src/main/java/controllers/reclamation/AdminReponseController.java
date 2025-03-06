package controllers.reclamation;

import entite.Reponse;
import services.ReponseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AdminReponseController {

    @FXML
    private ListView<Reponse> reponseListView;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> filterPriorityComboBox;

    private ReponseService reponseService = new ReponseService();
    private int reclamationId;
    private ObservableList<Reponse> allReponses;

    public void setReclamationId(int reclamationId) {
        this.reclamationId = reclamationId;
        refreshReponseList(); // Load responses when reclamationId is set
    }

    // Refresh method to reload responses
    public void refreshReponseList() {
        List<Reponse> reponses = reponseService.getReponsesByReclamationId(reclamationId);
        allReponses = FXCollections.observableArrayList(reponses);
        reponseListView.setItems(allReponses);
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText().toLowerCase();
        int selectedPriority = filterPriorityComboBox.getSelectionModel().getSelectedIndex();

        List<Reponse> filteredReponses = allReponses.stream()
                .filter(reponse -> reponse.getType().toLowerCase().contains(searchText))
                .filter(reponse -> selectedPriority == 0 || reponse.getPriorite() == selectedPriority)
                .collect(Collectors.toList());

        reponseListView.setItems(FXCollections.observableArrayList(filteredReponses));
    }

    @FXML
    private void handleSortByDate() {
        List<Reponse> sortedReponses = allReponses.stream()
                .sorted((r1, r2) -> r2.getDateReponse().compareTo(r1.getDateReponse())) // Sort by date descending
                .collect(Collectors.toList());

        reponseListView.setItems(FXCollections.observableArrayList(sortedReponses));
    }

    @FXML
    private void addResponse() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamation/AddReponse.fxml"));
            AnchorPane addResponseWindow = loader.load();

            AddResponseController controller = loader.getController();
            controller.setReclamationId(reclamationId);
            controller.setAdminReponseController(this); // Pass this controller for refreshing

            Stage stage = new Stage();
            stage.setTitle("Ajouter une réponse");
            stage.setScene(new Scene(addResponseWindow));
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamation/EditReponse.fxml"));
                AnchorPane updateResponseWindow = loader.load();

                UpdateReponseController controller = loader.getController();
                controller.setReponseToEdit(selectedReponse);
                controller.setAdminReponseController(this); // Pass this controller for refreshing

                Stage stage = new Stage();
                stage.setTitle("Modifier la réponse");
                stage.setScene(new Scene(updateResponseWindow));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Erreur", "Veuillez sélectionner une réponse à modifier.");
        }
    }

    @FXML
    private void deleteResponse() {
        Reponse selectedReponse = reponseListView.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            reponseService.supprimerReponse(selectedReponse.getId());
            refreshReponseList(); // Refresh the list after deletion
        } else {
            showAlert("Erreur", "Veuillez sélectionner une réponse à supprimer.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}