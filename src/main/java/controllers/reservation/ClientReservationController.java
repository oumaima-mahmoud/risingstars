package controllers.reservation;

import entite.Reservation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.ReservationService;
import tools.DataSource;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ClientReservationController {
    @FXML
    private TextField placeField;
    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private TextField idUserField;
    @FXML
    private DatePicker dateReservationField;
    @FXML
    private TextField prixTotalField;
    @FXML
    private ComboBox<String> idTicketDropdown; // Show barcode instead of ID
    @FXML
    private ComboBox<Integer> idParkingDropdown;
    @FXML
    private Label statusLabel;

    private final ReservationService reservationService = new ReservationService();
    private final Connection cnx = DataSource.getInstance().getConnection();

    private final Map<String, Integer> ticketMap = new HashMap<>(); // Barcode -> ID
    private final Map<String, Float> ticketPriceMap = new HashMap<>(); // Barcode -> Price
    private final Map<String, LocalDate> ticketDateMap = new HashMap<>(); // Barcode -> Date

    @FXML
    public void initialize() {
        // Load ticket and parking data
        loadTickets();
        loadParkingSpots();

        // Add listener to parking selection to update price
        idParkingDropdown.setOnAction(event -> handleParkingSelection());
    }

    private void loadTickets() {
        String query = "SELECT id, code_barre, prix, date FROM ticket";
        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String barcode = rs.getString("code_barre");
                int id = rs.getInt("id");
                float price = rs.getFloat("prix");
                LocalDate date = rs.getDate("date").toLocalDate();

                ticketMap.put(barcode, id);
                ticketPriceMap.put(barcode, price);
                ticketDateMap.put(barcode, date);
                idTicketDropdown.getItems().add(barcode);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des tickets : " + e.getMessage());
        }
    }

    private void loadParkingSpots() {
        String query = "SELECT id FROM parking";
        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                idParkingDropdown.getItems().add(rs.getInt("id"));
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement des parkings : " + e.getMessage());
        }
    }

    @FXML
    public void handleTicketSelection() {
        String selectedBarcode = idTicketDropdown.getValue();
        if (selectedBarcode != null) {
            float totalPrice = ticketPriceMap.get(selectedBarcode); // Base ticket price
            LocalDate reservationDate = ticketDateMap.get(selectedBarcode);

            prixTotalField.setText(String.valueOf(totalPrice));
            dateReservationField.setValue(reservationDate);
        }
    }

    @FXML
    public void handleParkingSelection() {
        String selectedBarcode = idTicketDropdown.getValue();
        Integer selectedParking = idParkingDropdown.getValue();
        if (selectedBarcode != null) {
            float basePrice = ticketPriceMap.get(selectedBarcode);
            float totalPrice = (selectedParking != null) ? basePrice + 5 : basePrice; // Add 5 DT if parking is selected
            prixTotalField.setText(String.valueOf(totalPrice));
        }
    }
    @FXML
    public void handleAddReservation() {
        // Contrôle de saisie : vérification des champs obligatoires
        if (placeField.getText().trim().isEmpty()) {
            statusLabel.setText("Veuillez saisir le nombre de places !");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        if (idUserField.getText().trim().isEmpty()) {
            statusLabel.setText("Veuillez saisir l'ID de l'utilisateur !");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        if (idTicketDropdown.getValue() == null) {
            statusLabel.setText("Veuillez sélectionner un ticket !");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        if (dateReservationField.getValue() == null) {
            statusLabel.setText("Veuillez sélectionner une date de réservation !");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        // Vérification des champs numériques
        int place;
        try {
            place = Integer.parseInt(placeField.getText().trim());
        } catch (NumberFormatException ex) {
            statusLabel.setText("Le nombre de places doit être un entier !");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        int idUser;
        try {
            idUser = Integer.parseInt(idUserField.getText().trim());
        } catch (NumberFormatException ex) {
            statusLabel.setText("L'ID utilisateur doit être un entier !");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            LocalDate dateReservation = dateReservationField.getValue();
            float prixTotal = Float.parseFloat(prixTotalField.getText());
            String selectedTicketBarcode = idTicketDropdown.getValue();
            int idTicket = ticketMap.get(selectedTicketBarcode);
            Integer idParking = idParkingDropdown.getValue(); // Nullable

            // Création et ajout de la réservation
            Reservation reservation = new Reservation(place, idUser, dateReservation, "en attente", prixTotal, idTicket, idParking);
            reservationService.ajouter(reservation);
            statusLabel.setText("Réservation ajoutée avec succès !");
            statusLabel.setStyle("-fx-text-fill: green;");

            // Chargement et affichage du QR Code
            int reservationId = (int) reservation.getId();
            String qrPath = "qr_codes/reservation_" + reservationId + ".png";
            File qrFile = new File(qrPath);
            if (qrFile.exists()) {
                qrCodeImageView.setImage(new Image(qrFile.toURI().toString()));
            } else {
                statusLabel.setText("QR Code non trouvé !");
                statusLabel.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            statusLabel.setText("Erreur lors de l'ajout de la réservation !");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }
}
