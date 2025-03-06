package controllers.stade;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import entite.Stade;
import services.serviceStade;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;
import org.json.JSONArray;
import org.json.JSONObject;


public class StadeController implements Initializable {
    @FXML
    private ComboBox<String> sortComboBox;

    @FXML
    private WebView mapView;
    @FXML
    private TextField locationField;
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;
    private String imagePath;
    @FXML
    private VBox EventPage;
    @FXML
    private Pane AddStadePage;
    /*@FXML
    private VBox stadeInterfaces;*/
    @FXML
    private Pane UpdateStadePage;
    @FXML
    private VBox StadeInterfaces;
    @FXML
    private ImageView ima;
    @FXML
    private TextField nomStade;
    @FXML
    private TextField   nomStadeS;
    @FXML
    private TextField capaciteS;
    @FXML
    private TextField capaciteStade;
    @FXML
    private VBox eventBox;
    @FXML
    private VBox EventInterfaces;
    @FXML
    private VBox stadebox;
    @FXML
    private TextField searchField;
    @FXML
    private VBox stadeInterfaces;
    @FXML
            private  VBox eventInterfaces;
    serviceStade ss=new serviceStade();
    public  static  int i;
    List<Stade> stadeList = ss.getAll();
    ObservableList<Stade> Stades = FXCollections.observableArrayList(stadeList);
    private FilteredList<Stade> filteredStadeList = new FilteredList<>(Stades, b -> true);
    private EventController eventController;

    public void setEventController(EventController eventController) {
        this.eventController = eventController;
    }
    @FXML
    void GoToAddStade(ActionEvent event) {
        System.out.println("Ajout d'un événement...");
        AddStadePage.setVisible(true);
        StadeInterfaces.setVisible(false);
       // EventPage.setVisible(false);
        UpdateStadePage.setVisible(false);
    }

    public void showStadeInterface() {
        AddStadePage.setVisible(false);
        StadeInterfaces.setVisible(true);
        EventInterfaces.setVisible(false);
        UpdateStadePage.setVisible(false);
    }
    @FXML
    void GoToStadePage(ActionEvent event) {
        AddStadePage.setVisible(false);
        StadeInterfaces.setVisible(true);
       // EventPage.setVisible(false);
        UpdateStadePage.setVisible(false);


    }


    @FXML
    void goToEventPage(ActionEvent event) {
            try {
                // Load the EventInterface.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/stade/EventInerface.fxml"));
                Parent eventRoot = loader.load();

                // Get the current stage (window)
                Stage stage = (Stage) stadeInterfaces.getScene().getWindow();

                // Set the new scene (EventInterface)
                Scene scene = new Scene(eventRoot);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.err.println("Failed to load EventInterface.fxml: " + e.getMessage());
                e.printStackTrace();
            }
        }


    public void data() {

            stadebox.getChildren().clear(); // Clear previous items

            // Fetch the list of stades from the database
            List<Stade> stadeList = ss.getAll();

            // Convert the list to an observable list
            Stades = FXCollections.observableArrayList(stadeList);

            // Add all stades to the UI
            for (Stade s : Stades) {
                VBox stadeCard = createStadeCard(s);
                stadebox.getChildren().add(stadeCard);

            }

    }


    @FXML
    public void handleSearchLocation() {
        String query = locationField.getText();
        if (query.isEmpty()) return;

        try {
            String encodedQuery = URLEncoder.encode(query, "UTF-8");
            String apiUrl = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedQuery;

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "GestionStade/1.0");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONArray results = new JSONArray(response.toString());
            if (results.length() > 0) {
                JSONObject firstResult = results.getJSONObject(0);
                double lat = firstResult.getDouble("lat");
                double lon = firstResult.getDouble("lon");

                latitudeField.setText(String.valueOf(lat));
                longitudeField.setText(String.valueOf(lon));
            } else {
                latitudeField.setText("Non trouvé");
                longitudeField.setText("Non trouvé");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setupSorting();
        Stades = FXCollections.observableArrayList(ss.getAll());
        data();
        setupSearch();
        // Set the default page visible at startup
        AddStadePage.setVisible(false);
        StadeInterfaces.setVisible(true);
        //EventPage.setVisible(false);
        UpdateStadePage.setVisible(false);

        System.out.println("Application started: EventsInterface is set to visible");

    }
    private void setupSorting() {
        SortedList<Stade> sortedStades = new SortedList<>(filteredStadeList);

        // Listen for sorting option changes
        sortComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Sort by Name")) {
                sortedStades.setComparator(Comparator.comparing(Stade::getNom));
            } else if (newValue.equals("Sort by Capacity")) {
                sortedStades.setComparator(Comparator.comparing(Stade::getcapacite).reversed()); // Descending order
            }
            updateStadeList(sortedStades); // Refresh UI
        });

        // Default sort by name
        sortedStades.setComparator(Comparator.comparing(Stade::getNom));
        updateStadeList(sortedStades);
    }


    private void setupSearch() {
        // Initialize the filtered list with the observable list
        FilteredList<Stade> filteredStadeList = new FilteredList<>(Stades, b -> true);

        // Bind the search field to the filtered list
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredStadeList.setPredicate(stade -> {
                // If the search field is empty, show all stades
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convert the search text to lowercase for case-insensitive comparison
                String lowerCaseFilter = newValue.toLowerCase();

                // Check if the stade's name or capacity contains the search text
                if (stade.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches the stade's name
                } else if (String.valueOf(stade.getcapacite()).contains(lowerCaseFilter)) {
                    return true; // Filter matches the stade's capacity
                }

                return false; // No match
            });

            // Update the UI with the filtered list
            updateStadeList(filteredStadeList);
        });
    }

    private void updateStadeList(ObservableList<Stade> stadeList) {
        stadebox.getChildren().clear(); // Clear the current list

        // Add the filtered stades to the UI
        for (Stade s : stadeList) {
            VBox stadeCard = createStadeCard(s);
            stadebox.getChildren().add(stadeCard);
        }
    }
    private void openMap(double latitude, double longitude) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/stade/Mapview.fxml"));
            Parent root = loader.load();

            MapViewController controller = loader.getController();
            controller.setLocation(latitude, longitude);

            Stage stage = new Stage();
            stage.setTitle("Stadium Location");
            stage.setScene(new Scene(root, 600, 450));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private VBox createStadeCard(Stade s) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: #F4F4F4; -fx-padding: 50px; -fx-border-radius: 50px; -fx-border-color: #CCC;");
        card.setAlignment(Pos.CENTER_LEFT);

        Label idLabel = new Label("ID: " + s.getId());
        idLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");


        Label nameLabel = new Label("Nom: " + s.getNom());
        nameLabel.setStyle("-fx-font-size: 14px;");


        Label capaciteLabel = new Label("Capacite: " + s.getcapacite());
        capaciteLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #555;");



        System.out.println(s.getImage());
        ImageView imageView = new ImageView();
        if (s.getImage() != null && !s.getImage().isEmpty()) {
            try {
                File file = new File(s.getImage());
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString()); // Convertir chemin local en URL valide
                    imageView.setImage(image);

                } else {
                    System.out.println("⚠ Fichier image introuvable: " + s.getImage());
                }
            } catch (Exception ex) {
                System.out.println("⚠ Erreur lors du chargement de l'image: " + s.getImage());
                ex.printStackTrace();
            }
        } else {
            System.out.println("⚠ L'URL de l'image est vide pour l'événement ID: " + s.getId());
        }

        imageView.setFitWidth(80);
        imageView.setFitHeight(50);

        // 📌 WebView pour afficher la carte du stade
        Button showMapButton = new Button("Show Map");
        System.out.println(s.getLatitude() + " " + s.getLongitude());
        showMapButton.setOnAction(e -> openMap(s.getLatitude(), s.getLongitude()));



        HBox buttonsContainer = new HBox(10);
        buttonsContainer.setAlignment(Pos.CENTER_LEFT);

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> {
            this.ss.supprimer(s.getId());
            Notifications.create()
                    .title("Événement supprimé")
                    .text("L'événement a été supprimé avec succès")
                    .position(Pos.BOTTOM_RIGHT)
                    .showConfirm();
            data(); // Refresh list after deletion
        });

        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: blue; -fx-text-fill: white;");
        editButton.setOnAction(e -> {
            System.out.println("Editing event: " + s.getId());
            nomStadeS.setText(s.getNom());
            capaciteS.setText(String.valueOf(s.getcapacite()));

            imagePath=(s.getImage());
            i=s.getId();
            AddStadePage.setVisible(false);
            StadeInterfaces.setVisible(false);
           // EventPage.setVisible(false);
            UpdateStadePage.setVisible(true);

        });


        buttonsContainer.getChildren().addAll(editButton,deleteButton,showMapButton);

        card.getChildren().addAll(idLabel, nameLabel, capaciteLabel, imageView, buttonsContainer);
        return card;
    }

    public void addStade(ActionEvent actionEvent) {
        // idha ken ykhali les champs ferghin
        if (nomStadeS.getText().isEmpty() || capaciteS.getText().isEmpty()   ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Error!");
            alert.setContentText("Fields cannot be empty");

            alert.showAndWait();
        }

        else {
            Stade s1 = new Stade(nomStadeS.getText(),Float.parseFloat(capaciteS.getText()),imagePath,Float.parseFloat(latitudeField.getText()),Float.parseFloat(longitudeField.getText()));
            ss.ajouter(s1);
            Stades.add(s1);

            nomStadeS.clear();
            capaciteS.clear();

            Notifications notificationBuilder = Notifications.create()
                    .title("stade ajouté ")
                    .text("votre stade a été ajouté avec succes")
                    .graphic(null)

                    .position(Pos.BOTTOM_RIGHT)
                    .onAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            System.out.println("ajouté avec succes");
                        }
                    });
            notificationBuilder.showConfirm();
            data();
            AddStadePage.setVisible(false);
            StadeInterfaces.setVisible(true);
           // EventPage.setVisible(false);
            UpdateStadePage.setVisible(false);

        }
    }

    public void choisirImage1(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        // Show the file chooser dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {

            Image img = new Image(selectedFile.toURI().toString());
            ima.setImage(img);

            // Save the image path
            imagePath = selectedFile.getAbsolutePath();
            System.out.println("Image uploaded: " + imagePath);
        }
    }

    public void OnUpdate(ActionEvent actionEvent) {

        System.out.println("edittttt : "+i);

        Stade s2=new Stade(i,nomStade.getText(),Float.parseFloat(capaciteStade.getText()),imagePath);
        System.out.println(s2);
        System.out.println(i);
        ss.modifier(s2,i);
        Stades.add(s2);
        nomStade.clear();
        capaciteStade.clear();


        Notifications notificationBuilder = Notifications.create()
                .title("Evenement modifié ")
                .text("votre Evenement a été modifié avec succes")
                .graphic(null)

                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("modifié avec succes");
                    }
                });
        notificationBuilder.showConfirm();
        data();
        AddStadePage.setVisible(false);
        StadeInterfaces.setVisible(true);
       // EventPage.setVisible(false);
        UpdateStadePage.setVisible(false);



    }
}
