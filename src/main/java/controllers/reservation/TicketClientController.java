package controllers.reservation;

import entite.Stade;
import entite.Ticket;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.TicketService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.collections.FXCollections;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;

public class TicketClientController {

    @FXML
    private ComboBox<Stade> stadiumComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> hourComboBox;
    @FXML
    private ComboBox<String> minuteComboBox;
    @FXML
    private TextField seatNumberField;
    @FXML
    private TextField barcodeField;
    @FXML
    private TextField priceField; // Now read-only
    @FXML
    private Button bookButton;

    private TicketService ticketService = new TicketService();

    @FXML
    public void initialize() {
        // Load stadiums from the DB using your existing method.
        stadiumComboBox.setItems(FXCollections.observableArrayList(ticketService.getAllStade(null)));

        // When a stadium is selected, automatically set the price.
        stadiumComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldStade, newStade) -> {
            if (newStade != null) {
                float computedPrice = computePrice(newStade);
                priceField.setText(String.valueOf(computedPrice));
            }
        });

        // Initialize the DatePicker with the current date.
        datePicker.setValue(LocalDate.now());

        // Populate the hourComboBox with values "00" to "23"
        for (int i = 0; i < 24; i++) {
            String hour = String.format("%02d", i);
            hourComboBox.getItems().add(hour);
        }
        // Set default hour as the current hour.
        hourComboBox.setValue(String.format("%02d", LocalTime.now().getHour()));

        // Populate the minuteComboBox with values "00" to "59"
        for (int i = 0; i < 60; i++) {
            String minute = String.format("%02d", i);
            minuteComboBox.getItems().add(minute);
        }
        // Set default minute as the current minute.
        minuteComboBox.setValue(String.format("%02d", LocalTime.now().getMinute()));
    }

    // Example method to compute the price based on the selected stadium
    private float computePrice(Stade stade) {
        // Adjust these values as needed.
        switch (stade.getNom()) {
            case "Stade Olympique":
                return 10.0f;
            case "Stade de France":
                return 15.0f;
            case "Stade National":
                return 12.0f;
            default:
                return 10.0f;
        }
    }

    @FXML
    public void generateBarcode() {
        String generated = "TICKET-" + new Random().nextInt(999999);
        barcodeField.setText(generated);
        System.out.println("Barcode generated: " + generated);
    }

    @FXML
    public void bookTicket() {
        // Vérification que le stade est sélectionné.
        Stade selectedStade = stadiumComboBox.getSelectionModel().getSelectedItem();
        if (selectedStade == null) {
            showError("Erreur de saisie", "Veuillez sélectionner un stade.");
            return;
        }

        // Vérification que le numéro de siège est renseigné et est un nombre.
        String seatNumberText = seatNumberField.getText().trim();
        if (seatNumberText.isEmpty()) {
            showError("Erreur de saisie", "Veuillez saisir le numéro de siège.");
            return;
        }
        int seatNumber;
        try {
            seatNumber = Integer.parseInt(seatNumberText);
        } catch (NumberFormatException e) {
            showError("Erreur de saisie", "Le numéro de siège doit être un nombre valide.");
            return;
        }

        // Vérification que le prix est renseigné et est un nombre.
        String priceText = priceField.getText().trim();
        if (priceText.isEmpty()) {
            showError("Erreur de saisie", "Le prix est manquant.");
            return;
        }
        float price;
        try {
            price = Float.parseFloat(priceText);
        } catch (NumberFormatException e) {
            showError("Erreur de saisie", "Le prix doit être un nombre valide.");
            return;
        }

        // Vérification que le code barre est renseigné.
        String barcodeText = barcodeField.getText().trim();
        if (barcodeText.isEmpty()) {
            showError("Erreur de saisie", "Veuillez saisir le code barre.");
            return;
        }

        // Vérification que la date est sélectionnée.
        LocalDate selectedDate = datePicker.getValue();
        if (selectedDate == null) {
            showError("Erreur de saisie", "Veuillez sélectionner une date.");
            return;
        }

        // Vérification que l'heure et les minutes sont sélectionnées.
        String selectedHour = hourComboBox.getValue();
        String selectedMinute = minuteComboBox.getValue();
        if (selectedHour == null || selectedMinute == null) {
            showError("Erreur de saisie", "Veuillez sélectionner l'heure et les minutes.");
            return;
        }

        try {
            // Combine the date and time.
            int hour = Integer.parseInt(selectedHour);
            int minute = Integer.parseInt(selectedMinute);
            LocalTime time = LocalTime.of(hour, minute);
            LocalDateTime ticketDate = LocalDateTime.of(selectedDate, time);

            // Création du ticket avec l'ID du stade sélectionné.
            Ticket ticket = new Ticket(ticketDate, seatNumber, barcodeText, price, selectedStade.getId());
            ticketService.ajouter(ticket);

            // Affichage d'un message de succès.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ticket réservé");
            alert.setHeaderText(null);
            alert.setContentText("Ticket réservé avec succès !");
            alert.showAndWait();
            // Now load ClientReservation.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservation/ClientReservation.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Réservation de Parking et Ticket");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            // If it prints "Location is not set", it means getResource(...) was null.
            e.printStackTrace();
            showError("Erreur", "Erreur lors de la réservation du ticket : " + e.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
