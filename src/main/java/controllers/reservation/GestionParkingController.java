package controllers.reservation;

import entite.Parking;
import services.ParkingService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

public class GestionParkingController {
    @FXML private TextField reservationIdField;
    @FXML private TextField nbrPlaceField;
    @FXML private TextField statutField;
    @FXML private DatePicker dateReservationField;
    @FXML private ComboBox<Integer> startHourBox;
    @FXML private ComboBox<Integer> startMinuteBox;
    @FXML private ComboBox<Integer> finishHourBox;
    @FXML private ComboBox<Integer> finishMinuteBox;
    @FXML private TextField prixField;
    @FXML private TableView<Parking> parkingTable;
    @FXML private TableColumn<Parking, Integer> idColumn;
    @FXML private TableColumn<Parking, Integer> reservationIdColumn;
    @FXML private TableColumn<Parking, Integer> nbrPlaceColumn;
    @FXML private TableColumn<Parking, String> statutColumn;
    @FXML private TableColumn<Parking, LocalDate> dateColumn;
    @FXML private TableColumn<Parking, LocalDateTime> heureDebutColumn;
    @FXML private TableColumn<Parking, LocalDateTime> heureFinColumn;
    @FXML private TableColumn<Parking, Float> prixColumn;

    private final ParkingService parkingService = new ParkingService();

    @FXML
    public void initialize() {
        // Populate hour & minute combo boxes
        startHourBox.setItems(FXCollections.observableArrayList(IntStream.range(0, 24).boxed().toList()));
        startMinuteBox.setItems(FXCollections.observableArrayList(IntStream.range(0, 60).boxed().toList()));
        finishHourBox.setItems(FXCollections.observableArrayList(IntStream.range(0, 24).boxed().toList()));
        finishMinuteBox.setItems(FXCollections.observableArrayList(IntStream.range(0, 60).boxed().toList()));

        // Always set statut to "libre" and make it non-editable
        statutField.setText("libre");
        statutField.setEditable(false);

        // Initialize TableView columns
        setupTableColumns();

        // Add listener to load details in the form when a parking is selected
        parkingTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                populateForm(newValue);
            }
        });

        // Load data into the TableView
        loadParkings();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        reservationIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getReservationId()).asObject());
        nbrPlaceColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNbrPlace()).asObject());
        statutColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatut()));
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateReservation().toLocalDate()));
        heureDebutColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getHeureDebut()));
        heureFinColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getHeureFin()));
        prixColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getPrix()).asObject());
    }

    /**
     * Fills the form with the selected parking details.
     */
    private void populateForm(Parking parking) {
        reservationIdField.setText(String.valueOf(parking.getReservationId()));
        nbrPlaceField.setText(String.valueOf(parking.getNbrPlace()));
        // Even though the status is forced to "libre", we display it
        statutField.setText(parking.getStatut());

        // Set the date if available
        if (parking.getDateReservation() != null) {
            dateReservationField.setValue(parking.getDateReservation().toLocalDate());
        } else {
            dateReservationField.setValue(null);
        }

        // Set the start time details
        LocalDateTime debut = parking.getHeureDebut();
        if(debut != null) {
            startHourBox.setValue(debut.getHour());
            startMinuteBox.setValue(debut.getMinute());
        } else {
            startHourBox.setValue(null);
            startMinuteBox.setValue(null);
        }

        // Set the finish time details
        LocalDateTime fin = parking.getHeureFin();
        if(fin != null) {
            finishHourBox.setValue(fin.getHour());
            finishMinuteBox.setValue(fin.getMinute());
        } else {
            finishHourBox.setValue(null);
            finishMinuteBox.setValue(null);
        }

        // Set the price
        prixField.setText(String.valueOf(parking.getPrix()));
    }

    @FXML
    public void addParking() {
        try {
            Parking parking = extractFormData();
            parkingService.ajouter(parking);
            loadParkings(); // Refresh the table
            clearForm();   // Clear the form after adding
        } catch (Exception e) {
            showAlert("Invalid Input", "Please enter valid data!");
        }
    }

    @FXML
    public void updateParking() {
        try {
            Parking selectedParking = parkingTable.getSelectionModel().getSelectedItem();
            if (selectedParking == null) {
                showAlert("Selection Error", "Please select a parking to update.");
                return;
            }

            Parking updatedParking = extractFormData();
            updatedParking.setId(selectedParking.getId()); // Retain the same ID

            parkingService.modifier(updatedParking);
            loadParkings(); // Refresh the table
            clearForm();   // Clear the form after updating
        } catch (Exception e) {
            showAlert("Invalid Input", "Please enter valid data!");
        }
    }

    @FXML
    public void deleteParking() {
        try {
            Parking selectedParking = parkingTable.getSelectionModel().getSelectedItem();
            if (selectedParking == null) {
                showAlert("Selection Error", "Please select a parking to delete.");
                return;
            }

            parkingService.supprimer(selectedParking.getId());
            loadParkings(); // Refresh the table
            clearForm();   // Clear the form after deletion
        } catch (Exception e) {
            showAlert("Deletion Error", "Unable to delete parking.");
        }
    }

    @FXML
    public void loadParkings() {
        List<Parking> parkings = parkingService.getAll(new Parking(0, 0, "", null, null, null, 0));

        if (parkings == null || parkings.isEmpty()) {
            System.out.println("No parkings found in database.");
            parkingTable.setItems(FXCollections.observableArrayList());
            return;
        }

        ObservableList<Parking> data = FXCollections.observableArrayList(parkings);
        parkingTable.setItems(data);
    }

    private Parking extractFormData() {
        int reservationId = Integer.parseInt(reservationIdField.getText());
        int nbrPlace = Integer.parseInt(nbrPlaceField.getText());
        // Force status to "libre" regardless of user input.
        String statut = "libre";
        LocalDate date = dateReservationField.getValue();

        // Construct start time
        LocalTime startTime = LocalTime.of(startHourBox.getValue(), startMinuteBox.getValue());
        LocalDateTime heureDebut = LocalDateTime.of(date, startTime);

        // Construct finish time
        LocalTime finishTime = LocalTime.of(finishHourBox.getValue(), finishMinuteBox.getValue());
        LocalDateTime heureFin = LocalDateTime.of(date, finishTime);

        float prix = Float.parseFloat(prixField.getText());

        return new Parking(reservationId, nbrPlace, statut, date.atStartOfDay(), heureDebut, heureFin, prix);
    }

    private void clearForm() {
        reservationIdField.clear();
        nbrPlaceField.clear();
        statutField.setText("libre");
        dateReservationField.setValue(null);
        startHourBox.setValue(null);
        startMinuteBox.setValue(null);
        finishHourBox.setValue(null);
        finishMinuteBox.setValue(null);
        prixField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
