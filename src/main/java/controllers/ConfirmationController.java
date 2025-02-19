package controllers;

import entities.Reservation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ConfirmationController {
    @FXML private Label statutReservation;
    @FXML private Label detailsTicket;
    @FXML private Label detailsParking;

    @FXML
    public void initialiser(Reservation reservation) {
        // Afficher le statut de la réservation
        statutReservation.setText("Statut : " + reservation.getStatut());

        // Afficher les détails du ticket
        detailsTicket.setText("Ticket : " + reservation.getTicket().getTypeEvenement() +
                " (" + reservation.getTicket().getDate() + ")");

        // Afficher les détails du parking (si applicable)
        if (reservation.getParking() != null) {
            detailsParking.setText("Parking : " + reservation.getParking().getNumeroPlace() +
                    " (" + reservation.getParking().getDateDebut() + " - " + reservation.getParking().getDateFin() + ")");
        } else {
            detailsParking.setText("Parking : Non requis");
        }
    }

    @FXML
    public void retourAccueil() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/reservation_ticket.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) statutReservation.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}