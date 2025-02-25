package controllers;

import entities.Reservation;
import Services.ReservationService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ReservationController {

    @FXML
    private TextField txtId, txtUserId, txtPlace, txtDate, txtStatut, txtPrixTotal;
    @FXML
    private Button btnAjouter, btnModifier, btnSupprimer, btnRechercher, btnReset;

    private ReservationService reservationService;

    public ReservationController() {
        reservationService = new ReservationService();
    }

    @FXML
    public void initialize() {
        btnAjouter.setOnAction(event -> ajouterReservation());
        btnModifier.setOnAction(event -> modifierReservation());
        btnSupprimer.setOnAction(event -> supprimerReservation());
        btnRechercher.setOnAction(event -> rechercherReservation());
        btnReset.setOnAction(event -> resetForm());
    }

    private void ajouterReservation() {
        try {
            int userId = Integer.parseInt(txtUserId.getText());
            String place = txtPlace.getText();
            LocalDate dateReservation = LocalDate.parse(txtDate.getText());
            String statut = txtStatut.getText();
            double prixTotal = Double.parseDouble(txtPrixTotal.getText());

            Reservation reservation = new Reservation(0, userId, place, dateReservation, statut, prixTotal);
            reservationService.addReservation(reservation);
            showAlert("Succès", "Réservation ajoutée avec succès", Alert.AlertType.INFORMATION);
            resetForm();
        } catch (Exception e) {
            showAlert("Erreur", "Veuillez vérifier vos données", Alert.AlertType.ERROR);
        }
    }

    private void modifierReservation() {
        try {
            int id = Integer.parseInt(txtId.getText());
            int userId = Integer.parseInt(txtUserId.getText());
            String place = txtPlace.getText();
            LocalDate dateReservation = LocalDate.parse(txtDate.getText());
            String statut = txtStatut.getText();
            double prixTotal = Double.parseDouble(txtPrixTotal.getText());

            Reservation reservation = new Reservation(id, userId, place, dateReservation, statut, prixTotal);
            reservationService.updateReservation(reservation);
            showAlert("Succès", "Réservation modifiée avec succès", Alert.AlertType.INFORMATION);
            resetForm();
        } catch (Exception e) {
            showAlert("Erreur", "Veuillez vérifier vos données", Alert.AlertType.ERROR);
        }
    }

    private void supprimerReservation() {
        try {
            int id = Integer.parseInt(txtId.getText());
            reservationService.deleteReservation(id);
            showAlert("Succès", "Réservation supprimée avec succès", Alert.AlertType.INFORMATION);
            resetForm();
        } catch (Exception e) {
            showAlert("Erreur", "Veuillez entrer un ID valide", Alert.AlertType.ERROR);
        }
    }

    private void rechercherReservation() {
        try {
            int id = Integer.parseInt(txtId.getText());
            Reservation reservation = reservationService.getReservationById(id);
            if (reservation != null) {
                txtUserId.setText(String.valueOf(reservation.getUserId()));
                txtPlace.setText(reservation.getPlace());
                txtDate.setText(reservation.getDateReservation().toString());
                txtStatut.setText(reservation.getStatut());
                txtPrixTotal.setText(String.valueOf(reservation.getPrixTotal()));
            } else {
                showAlert("Info", "Aucune réservation trouvée", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showAlert("Erreur", "Veuillez entrer un ID valide", Alert.AlertType.ERROR);
        }
    }

    private void resetForm() {
        txtId.clear();
        txtUserId.clear();
        txtPlace.clear();
        txtDate.clear();
        txtStatut.clear();
        txtPrixTotal.clear();
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
