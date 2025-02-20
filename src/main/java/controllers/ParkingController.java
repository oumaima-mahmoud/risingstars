package controllers;

import entities.Parking;
import entities.Reservation;
import entities.Ticket;
import entities.StatutRes;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import Services.ParkingService;
import Services.ReservationService;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class ParkingController {
    @FXML private ListView<Parking> listePlaces;
    @FXML private DatePicker dateDebut;
    @FXML private DatePicker dateFin;

    private ParkingService parkingService = new ParkingService();
    private ReservationService reservationService = new ReservationService();
    private Ticket ticket;

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @FXML
    public void chercherPlaces() {
        if (dateDebut.getValue() == null || dateFin.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Dates manquantes");
            alert.setContentText("Veuillez sélectionner une date de début et une date de fin.");
            alert.showAndWait();
            return;
        }

        List<Parking> places = parkingService.trouverPlacesDisponibles(
                Date.valueOf(dateDebut.getValue()),
                Date.valueOf(dateFin.getValue())
        );
        listePlaces.getItems().setAll(places);
    }

    @FXML
    public void confirmerReservation() {
        Parking parkingSelectionne = listePlaces.getSelectionModel().getSelectedItem();
        if (parkingSelectionne != null) {
            Reservation reservation = new Reservation(
                    0,
                    ticket,
                    parkingSelectionne,
                    StatutRes.EN_ATTENTE,
                    new Date(System.currentTimeMillis())
            );
            reservationService.ajouter(reservation);

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/confirmation.fxml"));
                Parent root = loader.load();
                ConfirmationController confirmationController = loader.getController();
                confirmationController.initialiser(reservation);
                Stage stage = (Stage) listePlaces.getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucune place sélectionnée");
            alert.setContentText("Veuillez sélectionner une place de parking.");
            alert.showAndWait();
        }
    }
}