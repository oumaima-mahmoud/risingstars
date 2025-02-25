package controllers;

import entities.Parking;
import services.ParkingService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParkingController {

    @FXML
    private TextField nbrPlaceField;
    @FXML
    private DatePicker dateReservationField;
    @FXML
    private TextField heureDebutField;
    @FXML
    private TextField heureFinField;
    @FXML
    private TextField prixField;
    @FXML
    private ComboBox<String> statutComboBox;
    @FXML
    private TableView<Parking> parkingTable;
    @FXML
    private TableColumn<Parking, Integer> idColumn;
    @FXML
    private TableColumn<Parking, Integer> nbrPlaceColumn;
    @FXML
    private TableColumn<Parking, String> dateReservationColumn;
    @FXML
    private TableColumn<Parking, String> heureDebutColumn;
    @FXML
    private TableColumn<Parking, String> heureFinColumn;
    @FXML
    private TableColumn<Parking, Double> prixColumn;
    @FXML
    private TableColumn<Parking, String> statutColumn;

    private ParkingService parkingService = new ParkingService();

    @FXML
    public void initialize() {
        statutComboBox.getItems().addAll("LIBRE", "RESERVE", "OCCUPE");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nbrPlaceColumn.setCellValueFactory(new PropertyValueFactory<>("nbrPlace"));
        dateReservationColumn.setCellValueFactory(new PropertyValueFactory<>("dateReservation"));
        heureDebutColumn.setCellValueFactory(new PropertyValueFactory<>("heureDebut"));
        heureFinColumn.setCellValueFactory(new PropertyValueFactory<>("heureFin"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prix"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        loadParkingData();
    }

    private void loadParkingData() {
        ObservableList<Parking> parkingList = FXCollections.observableArrayList(parkingService.getAllParkings());
        parkingTable.setItems(parkingList);
    }

    @FXML
    private void addParking() {
        try {
            int nbrPlace = Integer.parseInt(nbrPlaceField.getText());
            String dateReservation = dateReservationField.getValue().toString();
            String heureDebut = heureDebutField.getText();
            String heureFin = heureFinField.getText();
            double prix = Double.parseDouble(prixField.getText());
            String statut = statutComboBox.getValue();

            Parking newParking = new Parking(0, nbrPlace, dateReservation, heureDebut, heureFin, prix, statut);
            parkingService.addParking(newParking);

            clearFields();
            loadParkingData();
        } catch (Exception e) {
            showAlert("Erreur", "Veuillez entrer des valeurs valides.");
        }
    }

    @FXML
    private void updateParking() {
        Parking selectedParking = parkingTable.getSelectionModel().getSelectedItem();
        if (selectedParking != null) {
            try {
                selectedParking.setNbrPlace(Integer.parseInt(nbrPlaceField.getText()));
                selectedParking.setDateReservation(dateReservationField.getValue().toString());
                selectedParking.setHeureDebut(heureDebutField.getText());
                selectedParking.setHeureFin(heureFinField.getText());
                selectedParking.setPrix(Double.parseDouble(prixField.getText()));
                selectedParking.setStatut(statutComboBox.getValue());

                parkingService.updateParking(selectedParking);

                clearFields();
                loadParkingData();
            } catch (Exception e) {
                showAlert("Erreur", "Veuillez entrer des valeurs valides.");
            }
        } else {
            showAlert("Sélection requise", "Veuillez sélectionner un parking à modifier.");
        }
    }

    @FXML
    private void deleteParking() {
        Parking selectedParking = parkingTable.getSelectionModel().getSelectedItem();
        if (selectedParking != null) {
            parkingService.deleteParking(selectedParking.getId());
            clearFields();
            loadParkingData();
        } else {
            showAlert("Sélection requise", "Veuillez sélectionner un parking à supprimer.");
        }
    }

    private void clearFields() {
        nbrPlaceField.clear();
        dateReservationField.setValue(null);
        heureDebutField.clear();
        heureFinField.clear();
        prixField.clear();
        statutComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
