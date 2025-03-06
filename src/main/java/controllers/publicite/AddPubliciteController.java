package controllers.publicite;

import entite.Publicite;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import services.PubliciteService;

import java.time.LocalDate;

public class AddPubliciteController {

    @FXML
    private TextField titreField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField mediaUrlField;

    @FXML
    private DatePicker datePicker; // Use DatePicker for selecting date

    @FXML
    private TextField statutField;

    private final PubliciteService publiciteService = new PubliciteService();

    @FXML
    private void handleAddPublicite() {
        String titre = titreField.getText();
        String description = descriptionField.getText();
        String mediaUrl = mediaUrlField.getText();
        LocalDate selectedDate = datePicker.getValue(); // Get LocalDate from DatePicker
        String statut = statutField.getText();

        if (selectedDate == null) {
            System.out.println("Veuillez sélectionner une date.");
            return;
        }

        String date = selectedDate.toString(); // Convert LocalDate to String (SQL-compatible)

        // Use id_offre = 1 as the default offer ID
        Publicite newPublicite = new Publicite(titre, description, mediaUrl, date, statut, 1); // id_offre = 1
        publiciteService.ajouter(newPublicite);

        // Optionally, show a success message and close the window
        System.out.println("Publicité ajoutée avec succès!");
        titreField.getScene().getWindow().hide();
    }
}
