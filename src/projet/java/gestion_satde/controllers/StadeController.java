package gestion_satde.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import gestion_satde.entities.Stade;
import gestion_satde.services.serviceStade;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import org.controlsfx.control.Notifications;

public class StadeController implements Initializable {
    private String imagePath;
    @FXML
    private VBox EventPage;

    @FXML
    private Pane AddStadePage;
    @FXML
    private VBox stadeInterfaces;

    @FXML
    private Pane UpdateStadePage;

   /* @FXML
    private Pane //UpdateProductCard;

    @FXML
    private Button AddProductBtn;
*/


    @FXML
    private VBox StadeInterfaces;

    @FXML
    private Pane ListContainer;

    @FXML
    private Pane ListContainer1;

    @FXML
    private Pane ListContainer11;


    @FXML
    private Pane UpperSection;

    @FXML
    private Pane UpperSection1;

    @FXML
    private Pane UpperSection11;

    @FXML
    private TableColumn<?, ?> actionsColumnA;

    @FXML
    private TableColumn<?, ?> dateColumnP;
    @FXML
    private ImageView ima;
    @FXML
    private DatePicker dateEvent;
    @FXML
    private Button AddImageBtn;
    @FXML
    private TextField imageS;
    @FXML
    private TextField imageEvent;
    @FXML
    private TextField LieuIEventnput;
    @FXML
    private TextField ImageEventInput;

    @FXML
    private TextField nomStade;
    @FXML
    private TextField   nomStadeS;
    @FXML
    private TextField capaciteS;
    @FXML
    private TextField capaciteStade;
    @FXML
    private DatePicker dateEvent2;


    @FXML
    private TextField      TypeEventInput2;
    @FXML
    private TextField  LieuIEventnput2;
    @FXML
    private TextField      ImageEventInput2;
    //@FXML
    //private TableView<?> participantsTableView;


    @FXML
    private VBox eventsVBox;
    @FXML
    private VBox stadebox;


    @FXML
    private TextField typeEvent1;
    serviceStade ss=new serviceStade();
    public  static  int i;
    List<Stade> stadeList = ss.getAll();
    ObservableList<Stade> Stades = FXCollections.observableArrayList(stadeList);
    @FXML
    void GoToAddStade(ActionEvent event) {
        System.out.println("Ajout d'un événement...");
        AddStadePage.setVisible(true);
        StadeInterfaces.setVisible(false);
        EventPage.setVisible(false);
        UpdateStadePage.setVisible(false);
        //UpdateProductCard.setVisible(false);
    }


    @FXML
    void GoToStadePage(ActionEvent event) {
        AddStadePage.setVisible(false);
        StadeInterfaces.setVisible(true);
        EventPage.setVisible(false);
        UpdateStadePage.setVisible(false);
        //UpdateProductCard.setVisible(false);
    }
    @FXML
    void GoToAddEventPage(ActionEvent event) {
        AddStadePage.setVisible(false);
        StadeInterfaces.setVisible(false);
        EventPage.setVisible(false);
        UpdateStadePage.setVisible(false);
        //UpdateProductCard.setVisible(false);
    }

    @FXML
    void goToEventPage(ActionEvent event) {
        AddStadePage.setVisible(false);
        StadeInterfaces.setVisible(false);
        EventPage.setVisible(true);
        UpdateStadePage.setVisible(false);
        //UpdateProductCard.setVisible(false);
    }

    public void data() {
        stadebox.getChildren().clear(); // Clear previous items

        List<Stade> stadeList = ss.getAll(); // Fetch events from database
        System.out.println(stadeList);
        ObservableList<Stade> observableList = FXCollections.observableArrayList(stadeList);

        // Filtering (by default, all elements are visible)
        FilteredList<Stade> filteredData = new FilteredList<>(observableList, b -> true);

        // Sorting list
        SortedList<Stade> sortedList = new SortedList<>(filteredData);

        // Add event cards dynamically inside ListContainer
        for (Stade s : sortedList) {
            System.out.println(s.getId());
            VBox stadeCard = createStadeCard(s);
            stadebox.getChildren().add(stadeCard);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data();
        // Set the default page visible at startup
        AddStadePage.setVisible(false);
        StadeInterfaces.setVisible(true);
        EventPage.setVisible(false);
        UpdateStadePage.setVisible(false);
        //UpdateProductCard.setVisible(false);

        System.out.println("Application started: EventsInterface is set to visible");
    }
    private VBox createStadeCard(Stade s) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: #F4F4F4; -fx-padding: 10px; -fx-border-radius: 10px; -fx-border-color: #CCC;");
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
            EventPage.setVisible(false);
            UpdateStadePage.setVisible(true);
            ////UpdateProductCard.setVisible(false);
        });


        buttonsContainer.getChildren().addAll(editButton,deleteButton);

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
        //idha ken event date tkoun a9al mn date mte3 lyoum
      /*  else if(NoDate())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Oups !");
            alert.setContentText(" Event Date should be greater than today");
            alert.showAndWait();

        }*/
        // idha ken event name ykoun fih ar9am$
        else if(nomStadeS.getText().matches(".*\\d.*"))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Oups !");
            alert.setContentText(" Event Name shouldn't have numbers ");
            alert.showAndWait();

        }

        else {
           // java.sql.Date datee = java.sql.Date.valueOf(dateEvent.getValue());
           // java.sql.Date datee2 = java.sql.Date.valueOf(dateEventInput.getValue());
            Stade s1 = new Stade(nomStade.getText(),Float.parseFloat(capaciteS.getText()),imagePath);
            ss.ajouter(s1);
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
            EventPage.setVisible(false);
            UpdateStadePage.setVisible(false);
            //UpdateProductCard.setVisible(false);

        }
    }
    public void choisirImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", ".png", ".jpg", "*.gif"));

        ImageEventInput.setText(fileChooser.showOpenDialog(null).getAbsolutePath());
    }

    public void choisirImage1(ActionEvent actionEvent) {
       /* FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Images", ".png", ".jpg", "*.gif"));

        ImageEventInput.setText(fileChooser.showOpenDialog(null).getAbsolutePath());*/
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
    private boolean NoDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate myDate = dateEvent.getValue();
        int comparisonResult = myDate.compareTo(currentDate);
        boolean test = true;
        if (comparisonResult < 0) {
            // myDate est antérieure à currentDate
            test = true;
        } else if (comparisonResult > 0) {
            // myDate est postérieure à currentDate
            test = false;
        }
        return test;
    }
    public void OnUpdate(ActionEvent actionEvent) {
        /*java.sql.Date datee = java.sql.Date.valueOf(dateEvent.getValue());
        java.sql.Date datee2 = java.sql.Date.valueOf(dateEventInput.getValue());*/
        System.out.println("edittttt : "+i);

        Stade s2=new Stade(i,nomStadeS.getText(),Float.parseFloat(capaciteStade.getText()),imagePath);
        System.out.println(s2);
        System.out.println(i);
        ss.modifier(s2,i);
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
        EventPage.setVisible(false);
        UpdateStadePage.setVisible(false);
        //UpdateProductCard.setVisible(false);


    }
}
