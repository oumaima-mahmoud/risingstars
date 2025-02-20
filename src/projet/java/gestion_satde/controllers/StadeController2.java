package gestion_satde.controllers;
import java.util.ArrayList;
import java.util.List;
import gestion_satde.entities.Stade;
import gestion_satde.services.serviceStade;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

//import javax.swing.text.html.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;


public class StadeController2 {
    @FXML
    private TextField nom;

    @FXML
    private TextField capacite;
    @FXML
    private ImageView ima;
    @FXML
    private Button uploadButton;

    private String imagePath;

    @FXML
    private Button btn;
    @FXML
    private VBox vboxContainer;
    @FXML
    private Button bt;
    @FXML
    public void initialize() {

       // add();
        load();

    }
    public void load()
    {

        List<Stade> rs= new ArrayList<>();
        serviceStade ss = new serviceStade();
        rs=ss.getAll();
        for(Stade s : rs) {

            // Create a row using HBox
            HBox row = new HBox(10);
            Label nameLabel = new Label(s.getNom() + " (Capacity: " + s.getcapacite() + ")");
            Button updateButton = new Button("Update");
            Button deleteButton = new Button("Delete");
            ImageView imageView = new ImageView();

            if (s.getImage() != null && !s.getImage().isEmpty()) {
                File file = new File(s.getImage());
                if (file.exists()) {
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                }
            }

            // Set button actions
            deleteButton.setOnAction(e -> {
                ss.supprimer(s.getId());
                vboxContainer.getChildren().clear();// Call delete function
                load(); // Refresh list after deletion
            });

            updateButton.setOnAction(e -> {
                System.out.println("Update stadium with ID: " + s.getId());
                // You can open a new form for updating here
            });

            row.getChildren().addAll(imageView, nameLabel, updateButton, deleteButton);
            vboxContainer.getChildren().add(row);
        }
    }
    /*public void add(){
        btn.setOnAction(event -> {
            try {
                // Create new Stade object
                Stade s = new Stade(nom.getText(), Float.parseFloat(capacite.getText()),imagePath);

                // Call the service method
                serviceStade ss = new serviceStade();
                ss.ajouter(s);

                System.out.println("Stade added successfully!");
            } catch (NumberFormatException e) {
                System.out.println("Error: Capacité must be a number!");
            }
        });

        uploadButton.setOnAction(event -> {
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
        });
    }*/
        }









