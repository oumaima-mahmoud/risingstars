package controllers;

import entities.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Services.TicketService;

import java.io.IOException;
import java.sql.Date;

public class TicketController {
    @FXML private ComboBox<String> typeEvenement;
    @FXML private DatePicker dateEvenement;
    @FXML private RadioButton ouiVoiture;
    @FXML private RadioButton nonVoiture;

    private TicketService ticketService = new TicketService();

    @FXML
    public void passerEtapeSuivante(ActionEvent event) {
        // Vérifier si une date et un type d'événement sont sélectionnés
        if (typeEvenement.getValue() == null || dateEvenement.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Champs manquants");
            alert.setContentText("Veuillez sélectionner un type d'événement et une date.");
            alert.showAndWait();
            return;
        }

        // Créer un ticket
        Ticket ticket = new Ticket(
                0, // ID auto-généré
                Date.valueOf(dateEvenement.getValue()),
                typeEvenement.getValue(),
                50.0, // Prix fixe pour l'exemple
                ouiVoiture.isSelected() // Vérifie si l'utilisateur a une voiture
        );
        ticketService.ajouter(ticket);

        // Rediriger vers la page suivante
        try {
            FXMLLoader loader;
            if (ouiVoiture.isSelected()) {
                // Rediriger vers la page de réservation de parking
                loader = new FXMLLoader(getClass().getResource("/views/reservation_parking.fxml"));
            } else {
                // Rediriger directement vers la page de confirmation
                loader = new FXMLLoader(getClass().getResource("/views/confirmation.fxml"));
            }
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}