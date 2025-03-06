package controllers.reservation;

import entite.Stade;
import entite.Ticket;
import javafx.stage.Stage;
import services.TicketService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleFloatProperty;
import tools.SceneManager;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class GestionTicketsController {
    @FXML private TableView<Ticket> ticketTable;
    @FXML private TableColumn<Ticket, Integer> idColumn;
    @FXML private TableColumn<Ticket, LocalDateTime> dateColumn;
    @FXML private TableColumn<Ticket, Integer> numPlaceColumn;
    @FXML private TableColumn<Ticket, String> codeBarreColumn;
    @FXML private TableColumn<Ticket, Float> prixColumn;
    // Change this column to display a String (the stadium name)
    @FXML private TableColumn<Ticket, String> stadeColumn;
    @FXML private Button deleteButton;
    @FXML private TextField searchField;
   @FXML  private Button backToMainButton; // Injected from FXML

    private final TicketService ticketService = new TicketService();

    // We'll cache the mapping of stadium IDs to their names
    private Map<Integer, String> stadiumMapping = new HashMap<>();

    @FXML
    public void initialize() {
        // Setup table columns for other fields
        idColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        dateColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
        numPlaceColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumPlace()).asObject());
        codeBarreColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodeBarre()));
        prixColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getPrix()).asObject());

        // Load the mapping of stadiums (ID → Name) from the database using your getAllStade method
        List<Stade> stades = ticketService.getAllStade(null);
        for (Stade s : stades) {
            stadiumMapping.put(s.getId(), s.getNom());
        }
        // Setup the "stade" column to display the stadium name
        stadeColumn.setCellValueFactory(cellData -> {
            int idStade = cellData.getValue().getIdStade();
            String nom = stadiumMapping.getOrDefault(idStade, "N/A");
            return new SimpleStringProperty(nom);
        });

        // Load ticket data
        loadTickets();
    }

    @FXML
    public void loadTickets() {
        List<Ticket> tickets = ticketService.getAll(new Ticket(LocalDateTime.now(), 0, "", 0, 0));

        if (tickets == null || tickets.isEmpty()) {
            System.out.println("Aucun ticket trouvé.");
            ticketTable.setItems(FXCollections.observableArrayList());
            return;
        }

        ObservableList<Ticket> data = FXCollections.observableArrayList(tickets);
        ticketTable.setItems(data);
    }

    @FXML
    public void deleteTicket() {
        Ticket selectedTicket = ticketTable.getSelectionModel().getSelectedItem();
        if (selectedTicket == null) {
            showAlert("Sélection requise", "Veuillez sélectionner un ticket à supprimer.");
            return;
        }

        ticketService.supprimer(selectedTicket.getId());
        loadTickets(); // Refresh list after deletion
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void searchTicket() {
        String keyword = searchField.getText().trim();

        if (keyword.isEmpty()) {
            loadTickets(); // Reload all tickets if search field is empty
            return;
        }

        List<Ticket> filteredTickets = ticketService.searchTickets(keyword);
        ObservableList<Ticket> data = FXCollections.observableArrayList(filteredTickets);
        ticketTable.setItems(data);
    }
    @FXML
    public void goBackToMain() {
        try {
            // Get the current stage
            Stage stage = (Stage) backToMainButton.getScene().getWindow();
            // Switch back to the main window
            SceneManager.switchScene(stage, "/mainwindow.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
