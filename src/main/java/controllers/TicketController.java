package controllers;

import entities.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import Services.TicketService;

import java.sql.SQLException;
import java.util.List;

public class TicketController {
    @FXML private TextField tfReservationId, tfNumeroPlace, tfPrix;
    @FXML private TableView<Ticket> tableTickets;
    @FXML private TableColumn<Ticket, Integer> colId, colReservationId, colNumeroPlace;
    @FXML private TableColumn<Ticket, String> colDate, colCodeBarre;
    @FXML private TableColumn<Ticket, Double> colPrix;

    private final TicketService ticketService = new TicketService();
    private ObservableList<Ticket> ticketsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        colReservationId.setCellValueFactory(cellData -> cellData.getValue().reservationIdProperty().asObject());
        colNumeroPlace.setCellValueFactory(cellData -> cellData.getValue().numeroDePlaceProperty().asObject());
        colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty().asString());
        colCodeBarre.setCellValueFactory(cellData -> cellData.getValue().codeBarreProperty());
        colPrix.setCellValueFactory(cellData -> cellData.getValue().prixProperty().asObject());

        loadTickets();
    }

    private void loadTickets() {
        try {
            List<Ticket> tickets = ticketService.getAllTickets();
            ticketsList.setAll(tickets);
            tableTickets.setItems(ticketsList);
        } catch (SQLException e) {
            showAlert("Erreur", "Impossible de charger les tickets !");
        }
    }

    @FXML
    private void ajouterTicket() {
        try {
            String codeBarre = ticketService.genererCodeBarre(
                    Integer.parseInt(tfReservationId.getText()),
                    Integer.parseInt(tfNumeroPlace.getText())
            );
            Ticket ticket = new Ticket(
                    Integer.parseInt(tfReservationId.getText()),
                    new java.util.Date(),
                    Integer.parseInt(tfNumeroPlace.getText()),
                    codeBarre,
                    Double.parseDouble(tfPrix.getText())
            );
            ticketService.ajouterTicket(ticket);
            loadTickets();
            showAlert("Succès", "Ticket ajouté avec succès !");
        } catch (Exception e) {
            showAlert("Erreur", "Veuillez remplir tous les champs correctement.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
