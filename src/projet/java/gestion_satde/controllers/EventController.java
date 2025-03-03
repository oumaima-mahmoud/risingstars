package gestion_satde.controllers;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import gestion_satde.entities.evenement;
import gestion_satde.services.serviceEvenement;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class EventController implements Initializable {

    // Main Interface Components
    @FXML private VBox EventInterfaces;
    @FXML private VBox eventBox;

    // Add Form Components
    @FXML private Pane AddEventPage;
    @FXML private TextField nomEvent;
    @FXML private TextField typeEvent;
    @FXML private DatePicker dateEvent;
    @FXML private TextField organisateurEvent;
    @FXML private TextField participantsEvent;
    @FXML private TextField stadeIdEvent;

    // Update Form Components
    @FXML private Pane UpdateEventPage;
    @FXML private TextField nomEventUpdate;
    @FXML private TextField typeEventUpdate;
    @FXML private DatePicker dateEventUpdate;
    @FXML private TextField organisateurEventUpdate;
    @FXML private TextField participantsEventUpdate;
    @FXML private TextField stadeIdEventUpdate;
    @FXML
    private VBox eventInterfaces;
    @FXML private TextField searchField;
     serviceEvenement se = new serviceEvenement();
    private int currentEventId;
    private StadeController stadeController;
    private ObservableList<evenement> observableEventList;
    private FilteredList<evenement> filteredEventList;
    public void setStadeController(StadeController stadeController) {
        this.stadeController = stadeController;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the observable list with data from the database
        observableEventList = FXCollections.observableArrayList(se.getAll());

        // Initialize the filtered list
        filteredEventList = new FilteredList<>(observableEventList, b -> true);

        // Initialize the search functionality
        setupSearch();

        // Load the initial data
        loadEvents();
    }
    private void setupSearch() {
        // Bind the search field to the filtered list
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEventList.setPredicate(event -> {
                // If the search field is empty, show all events
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convert the search text to lowercase for case-insensitive comparison
                String lowerCaseFilter = newValue.toLowerCase();

                // Check if the event's name, type, or organizer contains the search text
                if (event.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches the event's name
                } else if (event.getType().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches the event's type
                } else if (event.getOraganisateur().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches the event's organizer
                }

                return false; // No match
            });

            // Update the UI with the filtered list
            updateEventList();
        });
    }
    private void updateEventList() {
        eventBox.getChildren().clear(); // Clear the current list

        // Add the filtered events to the UI
        for (evenement event : filteredEventList) {
            VBox eventCard = createEventCard(event);
            eventBox.getChildren().add(eventCard);
        }
    }
    private void loadEvents() {
        eventBox.getChildren().clear();
        for(evenement event : filteredEventList) {
            VBox eventCard = createEventCard(event);
            eventBox.getChildren().add(eventCard);
        }
    }

    private VBox createEventCard(evenement event) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: #F8F8F8; -fx-padding: 10; -fx-border-radius: 5;");

        Label idLabel = new Label("ID: " + event.getId());
        Label nomLabel = new Label("Nom: " + event.getNom());
        Label typeLabel = new Label("Type: " + event.getType());
        Label dateLabel = new Label("Date: " + event.getDate());
        Label organisateurLabel =new Label("Organisateur : "+event.getOraganisateur());
        Label participantLabel = new Label("Nombre de participant : "+event.getNombre_participant());

        HBox buttons = new HBox(10);
        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Delete");

        // Delete Button Setup
        deleteBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
        deleteBtn.setOnAction(e -> {
            se.supprimer(event.getId());
            showNotification("Événement supprimé", "Événement supprimé avec succès");
            loadEvents();
        });

        // Edit Button Setup
        editBtn.setStyle("-fx-background-color: #4285f4; -fx-text-fill: white;");
        editBtn.setOnAction(e -> showUpdateForm(event));

        buttons.getChildren().addAll(editBtn, deleteBtn);
        card.getChildren().addAll(idLabel, nomLabel, typeLabel, dateLabel,organisateurLabel,participantLabel, buttons);

        return card;
    }

    private void showUpdateForm(evenement event) {
        currentEventId = event.getId();
        // Populate Update Form Fields
        nomEventUpdate.setText(event.getNom());
        typeEventUpdate.setText(event.getType());
        dateEventUpdate.setValue(event.getDate());
        organisateurEventUpdate.setText(event.getOraganisateur());
        participantsEventUpdate.setText(String.valueOf(event.getNombre_participant()));
        stadeIdEventUpdate.setText(String.valueOf(event.getId_stade()));

        // Switch to Update Form
        EventInterfaces.setVisible(false);
        UpdateEventPage.setVisible(true);
    }

    @FXML
    private void handleAddEvent(ActionEvent event) {
        if(validateAddForm()) {
            evenement newEvent = new evenement(
                    nomEvent.getText(),
                    typeEvent.getText(),
                    dateEvent.getValue(),
                    organisateurEvent.getText(),
                    Double.parseDouble(participantsEvent.getText()),
                    Integer.parseInt(stadeIdEvent.getText())
            );

            se.ajouter(newEvent);
            observableEventList.add(newEvent);
            clearAddForm();
            loadEvents();
            showNotification("Succès", "Événement ajouté avec succès");
            showEventList();
        }
    }

    @FXML
    private void handleUpdateEvent(ActionEvent event) {
        if(validateUpdateForm()) {
            evenement updatedEvent = new evenement(
                    nomEventUpdate.getText(),
                    typeEventUpdate.getText(),
                    dateEventUpdate.getValue(),
                    organisateurEventUpdate.getText(),
                    Double.parseDouble(participantsEventUpdate.getText()),
                    Integer.parseInt(stadeIdEventUpdate.getText())
            );

            se.modifier(updatedEvent, currentEventId);
            observableEventList.add(updatedEvent);

            System.out.println(se);
            clearUpdateForm();
            loadEvents();
            showNotification("Succès", "Événement modifié avec succès");
            showEventList();
        }
    }

    // Navigation Methods
    @FXML
    private void showAddForm(ActionEvent event) {
        clearAllForms();
        EventInterfaces.setVisible(false);
        AddEventPage.setVisible(true);
    }
    public void showEventInterface() {
        AddEventPage.setVisible(false);
        EventInterfaces.setVisible(true);
        UpdateEventPage.setVisible(false);
    }
    @FXML
    void showEventList() {
        clearAllForms();
        AddEventPage.setVisible(false);
        UpdateEventPage.setVisible(false);
        EventInterfaces.setVisible(true);

    }
    @FXML
    void showStadeInterface(ActionEvent event) {
        try {
            // Load the StadeInterface.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/StadeInterface.fxml"));
            Parent stadeRoot = loader.load();

            // Get the current stage (window)
            Stage stage = (Stage) eventInterfaces.getScene().getWindow();

            // Set the new scene (StadeInterface)
            Scene scene = new Scene(stadeRoot);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Failed to load StadeInterface.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Validation Methods
    private boolean validateAddForm() {
        return validateForm(nomEvent, typeEvent, dateEvent,
                organisateurEvent, participantsEvent, stadeIdEvent);
    }

    private boolean validateUpdateForm() {
        return validateForm(nomEventUpdate, typeEventUpdate, dateEventUpdate,
                organisateurEventUpdate, participantsEventUpdate, stadeIdEventUpdate);
    }

    private boolean validateForm(TextField nom, TextField type, DatePicker date,
                                 TextField organisateur, TextField participants, TextField stadeId) {
        if(nom.getText().isEmpty() || type.getText().isEmpty() || date.getValue() == null ||
                organisateur.getText().isEmpty() || participants.getText().isEmpty() || stadeId.getText().isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis");
            return false;
        }

        try {
            Integer.parseInt(stadeId.getText());
            Double.parseDouble(participants.getText());
        } catch(NumberFormatException e) {
            showAlert("Erreur de format", "ID Stade et Participants doivent être des nombres");
            return false;
        }

        return true;
    }

    // Form Management
    private void clearAddForm() {
        nomEvent.clear();
        typeEvent.clear();
        dateEvent.setValue(null);
        organisateurEvent.clear();
        participantsEvent.clear();
        stadeIdEvent.clear();
    }

    private void clearUpdateForm() {
        nomEventUpdate.clear();
        typeEventUpdate.clear();
        dateEventUpdate.setValue(null);
        organisateurEventUpdate.clear();
        participantsEventUpdate.clear();
        stadeIdEventUpdate.clear();
    }

    private void clearAllForms() {
        clearAddForm();
        clearUpdateForm();
    }

    // Notification Methods
    private void showAlert(String title, String content) {
        new Alert(Alert.AlertType.ERROR, content).setHeaderText(title);
    }

    private void showNotification(String title, String message) {
        Notifications.create()
                .title(title)
                .text(message)
                .position(Pos.BOTTOM_RIGHT)
                .showInformation();
    }
}