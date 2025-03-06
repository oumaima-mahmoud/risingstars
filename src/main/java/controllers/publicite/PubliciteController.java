package controllers.publicite;

import entite.Publicite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.PubliciteService;

import java.io.IOException;
import java.util.List;

public class PubliciteController {

    @FXML
    private VBox publicitesContainer;

    @FXML
    private Button ajouterPubliciteButton;

    private final PubliciteService publiciteService = new PubliciteService();

    @FXML
    public void initialize() {
        loadPublicites();

        // Add event handler for the "Ajouter Publicité" button
        ajouterPubliciteButton.setOnAction(event -> openAddPubliciteWindow());
    }

    private void loadPublicites() {
        publicitesContainer.getChildren().clear(); // Clear existing content

        // Pass a new Publicite object to get all publicites from the database
        List<Publicite> publicites = publiciteService.getAll(new Publicite(0, "", "", "", "", "", 0));

        if (publicites == null || publicites.isEmpty()) {
            Label noDataLabel = new Label("Aucune publicité disponible.");
            publicitesContainer.getChildren().add(noDataLabel);
        } else {
            // Add each publicite to the container dynamically
            for (Publicite publicite : publicites) {
                VBox publiciteBox = createPubliciteBox(publicite);
                publicitesContainer.getChildren().add(publiciteBox);
            }
        }
    }

    private VBox createPubliciteBox(Publicite publicite) {
        VBox box = new VBox(5);
        box.getStyleClass().add("publicite-box");

        Label idLabel = new Label("ID Publicité: " + publicite.getId_publicite());
        Label titreLabel = new Label("Titre: " + publicite.getTitre());
        Label descriptionLabel = new Label("Description: " + publicite.getDescription());
        Label mediaLabel = new Label("Media URL: " + publicite.getMedia_url());
        Label dateLabel = new Label("Date: " + publicite.getDate());
        Label statutLabel = new Label("Statut: " + publicite.getStatut());
        Label annonceurLabel = new Label("ID Offre: " + publicite.getId_offre());  // Corrected to match field

        box.getChildren().addAll(idLabel, titreLabel, descriptionLabel, mediaLabel, dateLabel, statutLabel, annonceurLabel);

        // Add functionality for modifying the specific publicite
        Button modifyButton = new Button("Modifier");
        modifyButton.setOnAction(event -> openModifyPubliciteWindow(publicite.getId_publicite()));
        box.getChildren().add(modifyButton);

        // Add functionality for deleting the specific publicite
        Button deleteButton = new Button("Supprimer");
        deleteButton.setOnAction(event -> deletePublicite(publicite.getId_publicite()));
        box.getChildren().add(deleteButton);

        return box;
    }

    private void openAddPubliciteWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/publicite/add_publicite.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Ajouter une Publicité");
            stage.setScene(new Scene(root));
            stage.show();

            // After adding, refresh the list once the window is closed
            stage.setOnHiding(e -> loadPublicites());  // Reload publicites when the add window is closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openModifyPubliciteWindow(int publiciteId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/publicite/modify_publicite.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Modifier une Publicité");
            stage.setScene(new Scene(root));
            stage.show();

            // Pass the ID to the ModifyPubliciteController
            ModifyPubliciteController controller = loader.getController();
            controller.setPubliciteId(publiciteId);  // Pass the specific publiciteId to the modify window

            // After modifying, refresh the list once the window is closed
            stage.setOnHiding(e -> loadPublicites());  // Reload publicites when the modify window is closed
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deletePublicite(int publiciteId) {
        // Call the service method to delete the publicite
        publiciteService.supprimer(publiciteId);
        loadPublicites(); // Reload the list of publicites after deletion
    }
}
