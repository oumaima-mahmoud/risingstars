package controllers.reservation;

import entite.Reservation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import services.QRCodeGenerator;
import services.ReservationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;

import java.io.File;
import java.util.List;

public class AdminReservationController {

    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private TableColumn<Reservation, Integer> userColumn;
    @FXML
    private TableColumn<Reservation, Integer> placeColumn;
    @FXML
    private TableColumn<Reservation, Integer> ticketColumn;
    @FXML
    private TableColumn<Reservation, Integer> parkingColumn;
    @FXML
    private TableColumn<Reservation, Float> prixColumn;
    @FXML
    private TableColumn<Reservation, String> statutColumn;
    @FXML
    private TableColumn<Reservation, ImageView> qrCodeColumn;
    @FXML
    private TableColumn<Reservation, Void> actionColumn;

    private final ReservationService reservationService = new ReservationService();
    private static final String QR_CODE_DIR = "qr_codes/";

    @FXML
    public void initialize() {
        // Ensure QR Code directory exists
        File qrCodeFolder = new File(QR_CODE_DIR);
        if (!qrCodeFolder.exists()) {
            qrCodeFolder.mkdir();
        }

        // Load reservations into the table
        loadReservations();
    }

    private void loadReservations() {
        List<Reservation> reservations = reservationService.getAlll();
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList(reservations);

        userColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
        ticketColumn.setCellValueFactory(new PropertyValueFactory<>("idTicket"));
        parkingColumn.setCellValueFactory(new PropertyValueFactory<>("idParking"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixTotal"));
        statutColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));

        // QR Code Column - Display Image
        qrCodeColumn.setCellFactory(param -> new TableCell<>() {
            private final ImageView qrImageView = new ImageView();

            @Override
            protected void updateItem(ImageView item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= reservationList.size()) {
                    setGraphic(null);
                } else {
                    Reservation reservation = reservationList.get(getIndex());
                    String qrCodePath = QR_CODE_DIR + "reservation_" + reservation.getId() + ".png";
                    File qrFile = new File(qrCodePath);

                    if (!qrFile.exists()) {
                        generateQRCodeForReservation(reservation);
                    }

                    qrImageView.setImage(new Image(qrFile.toURI().toString()));
                    qrImageView.setFitWidth(60);
                    qrImageView.setFitHeight(60);
                    setGraphic(qrImageView);
                }
            }
        });

        // Action Column - Modify Status and Delete
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final ComboBox<String> statusComboBox = new ComboBox<>();
            private final Button deleteButton = new Button("🗑️ Supprimer");

            {
                statusComboBox.getItems().addAll("en attente", "validee", "refusee");
                statusComboBox.setStyle("-fx-background-radius: 8; -fx-padding: 5; -fx-font-size: 14px;");
                statusComboBox.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    String newStatus = statusComboBox.getValue();
                    reservation.setStatut(newStatus);
                    reservationService.modifier(reservation);
                    loadReservations();
                });

                deleteButton.setStyle("-fx-background-color: #ff007f; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5 15; -fx-background-radius: 8;");
                deleteButton.setOnAction(event -> {
                    Reservation reservation = getTableView().getItems().get(getIndex());
                    handleDeleteReservation(new ActionEvent());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(10, statusComboBox, deleteButton);
                    setGraphic(hBox);
                }
            }
        });

        reservationTable.setItems(reservationList);
    }

    private void generateQRCodeForReservation(Reservation reservation) {
        String reservationData = "Reservation ID: " + reservation.getId() +
                "\nUser ID: " + reservation.getIdUser() +
                "\nDate: " + reservation.getDateReservation() +
                "\nStatut: " + reservation.getStatut();
        String filePath = QR_CODE_DIR + "reservation_" + reservation.getId() + ".png";
        QRCodeGenerator.generateQRCode(reservationData, filePath);
    }

    @FXML
    private void handleDeleteReservation(ActionEvent event) {
        Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            reservationService.supprimer(selectedReservation.getIdTicket()); // Using ticket ID as identifier
            loadReservations(); // Refresh table
        }
    }
}
