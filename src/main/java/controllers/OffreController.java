package controllers;

import entite.Offre;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.OffreService;

import java.io.IOException;
import java.util.List;

public class OffreController {

    @FXML
    private VBox offresContainer;

    @FXML
    private Button ajouterOffreButton;

    @FXML
    private Button refreshOffresButton;

    private final OffreService offreService = new OffreService();

    @FXML
    public void initialize() {
        loadOffres();

        // Add event handler for the "Ajouter Offre" button
        ajouterOffreButton.setOnAction(event -> openAddOffreWindow());

        // Add event handler for the "Refresh" button
        refreshOffresButton.setOnAction(event -> loadOffres());
    }

    private void loadOffres() {
        offresContainer.getChildren().clear(); // Clear existing content

        List<Offre> offres = offreService.getAll(new Offre(0, 0, 0.0, "", "", ""));

        if (offres == null || offres.isEmpty()) {
            Label noDataLabel = new Label("Aucune offre disponible.");
            offresContainer.getChildren().add(noDataLabel);
        } else {
            // Add each offre to the container dynamically
            for (Offre offre : offres) {
                VBox offreBox = createOffreBox(offre);
                offresContainer.getChildren().add(offreBox);
            }
        }
    }

    private VBox createOffreBox(Offre offre) {
        VBox box = new VBox(5);
        box.getStyleClass().add("offre-box");

        Label idLabel = new Label("Offre ID: " + offre.getId_offre());
        Label montantLabel = new Label("Montant: " + offre.getMontant() + " €");
        Label conditionsLabel = new Label("Conditions: " + offre.getConditions());
        Label contactLabel = new Label("Contact: " + offre.getContact());
        Label dateLabel = new Label("Date: " + offre.getDate_proposition());

        box.getChildren().addAll(idLabel, montantLabel, conditionsLabel, contactLabel, dateLabel);

        // Add functionality for modifying the specific offre
        Button modifyButton = new Button("Modifier");
        modifyButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white;");
        modifyButton.setOnAction(event -> openModifyOffreWindow(offre));  // Pass the selected Offre
        box.getChildren().add(modifyButton);

        // Add functionality for deleting the specific offre
        Button deleteButton = new Button("Supprimer");
        deleteButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white;");
        deleteButton.setOnAction(event -> deleteOffre(offre.getId_offre()));  // Pass the ID to delete the offre
        box.getChildren().add(deleteButton);

        return box;
    }

    private void openAddOffreWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_offre.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter une Offre");
            stage.setScene(new Scene(root));
            stage.show();

            // After adding, refresh the list once the window is closed
            stage.setOnHiding(e -> loadOffres());  // Reload offres when the add window is closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openModifyOffreWindow(Offre selectedOffre) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modify_offre.fxml"));
            Parent root = loader.load();
            ModifyOffreController controller = loader.getController();
            controller.setOffre(selectedOffre);  // Pass the selected offer to the modify controller
            Stage stage = new Stage();
            stage.setTitle("Modifier une Offre");
            stage.setScene(new Scene(root));
            stage.show();

            // After modifying, refresh the list once the window is closed
            stage.setOnHiding(e -> loadOffres());  // Reload offres when the modify window is closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteOffre(int offreId) {
        // Call the service method to delete the offre
        offreService.supprimer(offreId);
        loadOffres(); // Reload the list of offres after deletion
    }
}
