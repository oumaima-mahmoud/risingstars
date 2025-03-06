package controllers.reclamation;

import entite.Reponse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.ReponseService;

import java.io.IOException;

public class ReponseController {

    @FXML
    private TextField tfReclamationId;
    @FXML
    private TextArea taContenu;
    @FXML
    private Button btnAjouterReponse;
    @FXML
    private ListView<Reponse> reponseListView;
    @FXML
    private Button btnSupprimerReponse;
    @FXML
    private Button btnModifierReponse;

    private ReponseService reponseService = new ReponseService();

    @FXML
    public void initialize() {
        loadReponses();

        reponseListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnSupprimerReponse.setDisable(newSelection == null);
            btnModifierReponse.setDisable(newSelection == null);
        });
    }

    public void setIdReclamation(int idReclamation) {
        tfReclamationId.setText(String.valueOf(idReclamation));
        tfReclamationId.setDisable(true);
    }

    @FXML
    private void ajouterReponse() {
        try {
            int idReclamation = Integer.parseInt(tfReclamationId.getText().trim());
            String contenu = taContenu.getText().trim();

            if (contenu.isEmpty()) {
                showAlert("Erreur", "Le contenu de la réponse ne peut pas être vide.");
                return;
            }


            taContenu.clear();
            loadReponses();
        } catch (NumberFormatException e) {
            showAlert("Erreur", "L'ID de la réclamation doit être un nombre valide.");
        }
    }

    @FXML
    private void supprimerReponse() {
        Reponse selectedReponse = reponseListView.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            reponseService.supprimerReponse(selectedReponse.getId());
            loadReponses();
        } else {
            showAlert("Erreur", "Veuillez sélectionner une réponse à supprimer.");
        }
    }

    @FXML
    private void modifierReponse() {
        Reponse selectedReponse = reponseListView.getSelectionModel().getSelectedItem();
        if (selectedReponse == null) {
            showAlert("Erreur", "Veuillez sélectionner une réponse à modifier.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reclamation/EditReponse.fxml"));
            AnchorPane root = loader.load();

            UpdateReponseController updateReponseController = loader.getController();
            updateReponseController.setReponseToEdit(selectedReponse);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Impossible d'ouvrir l'interface de modification.");
            e.printStackTrace();
        }
    }

    public void loadReponses() {
        reponseListView.getItems().clear();
        reponseListView.getItems().addAll(reponseService.getAllReponses());
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
