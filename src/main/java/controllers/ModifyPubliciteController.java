package controllers;

import entite.Publicite;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import services.PubliciteService;

public class ModifyPubliciteController {

    @FXML
    private TextField idField;

    @FXML
    private TextField titreField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField mediaUrlField;

    @FXML
    private TextField dateField;

    @FXML
    private TextField statutField;

    private final PubliciteService publiciteService = new PubliciteService();
    private int publiciteId;

    // Method to set the Publicité ID passed from the main controller
    public void setPubliciteId(int publiciteId) {
        this.publiciteId = publiciteId;
        idField.setText(String.valueOf(publiciteId)); // Set the ID in the field
        loadPubliciteData(publiciteId); // Optionally load other data into fields
    }

    // Method to load Publicite data into the fields based on the publiciteId
    private void loadPubliciteData(int publiciteId) {
        Publicite publicite = publiciteService.getById(publiciteId); // Assuming a method in the service that fetches data by ID
        if (publicite != null) {
            titreField.setText(publicite.getTitre());
            descriptionField.setText(publicite.getDescription());
            mediaUrlField.setText(publicite.getMedia_url());
            dateField.setText(publicite.getDate());
            statutField.setText(publicite.getStatut());
        }
    }

    @FXML
    private void handleModifyPublicite() {
        // Handle the modification of the publicite
        String titre = titreField.getText();
        String description = descriptionField.getText();
        String mediaUrl = mediaUrlField.getText();
        String date = dateField.getText();
        String statut = statutField.getText();

        // Assuming you want to update with id_offre = 1 (change if necessary)
        Publicite updatedPublicite = new Publicite(publiciteId, titre, description, mediaUrl, date, statut, 1);

        publiciteService.modifier(updatedPublicite);

        // Close the window after modifying
        idField.getScene().getWindow().hide();
    }
}
