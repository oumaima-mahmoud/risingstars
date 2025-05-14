package controllers.consommation;

import javafx.scene.control.*;
import entite.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import services.ServiceProduit;
import javafx.stage.FileChooser;
import java.io.File;
import javafx.scene.Node;
import javafx.event.ActionEvent;

import java.util.List;

public class ProduitController {
    @FXML
    private TableView<Produit> tableProduits;

    @FXML
    private TableColumn<Produit, String> categorie;

    @FXML
    private TableColumn<Produit, Integer> idProduit;

    @FXML
    private TableColumn<Produit, String> image;

    @FXML
    private TableColumn<Produit, String> nom;

    @FXML
    private TableColumn<Produit, Float> prixProduit;
    @FXML
    private TextField nomField;

    @FXML
    private TextField prixField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField categorieField;

    @FXML
    private TextField imageField;
    @FXML
    private TableColumn<Produit, Integer> stock;
    private final ServiceProduit serviceProduit = new ServiceProduit();
    private Produit selectedProduit = null;
    @FXML
    public void initialize() {
        // Configuration des colonnes
        idProduit.setCellValueFactory(new PropertyValueFactory<>("idProduit"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prixProduit.setCellValueFactory(new PropertyValueFactory<>("prixProduit"));
        stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        //image.setCellValueFactory(new PropertyValueFactory<>("image"));
        image.setCellValueFactory(new PropertyValueFactory<>("image"));

        image.setCellFactory(new Callback<TableColumn<Produit, String>, TableCell<Produit, String>>() {
            @Override
            public TableCell<Produit, String> call(TableColumn<Produit, String> param) {
                return new TableCell<Produit, String>() {
                    private final ImageView imageView = new ImageView();

                    @Override
                    protected void updateItem(String imagePath, boolean empty) {
                        super.updateItem(imagePath, empty);
                        if (empty || imagePath == null || imagePath.isEmpty()) {
                            setGraphic(null);
                        } else {
                            try {
                                Image img = new Image("file:" + imagePath, 60, 60, true, true);
                                imageView.setImage(img);
                                setGraphic(imageView);
                            } catch (Exception e) {
                                setGraphic(null); // image invalide ou introuvable
                            }
                        }
                    }
                };
            }
        });


        // Listener de sélection dans le tableau
        tableProduits.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selectedProduit = newSel;
                remplirChamps(selectedProduit);
            }
        });
        // Charger les données
        loadProduits();
    }
    private void remplirChamps(Produit produit) {
        nomField.setText(produit.getNom());
        prixField.setText(String.valueOf(produit.getPrixProduit()));
        stockField.setText(String.valueOf(produit.getStock()));
        categorieField.setText(produit.getCategorie());
        imageField.setText(produit.getImage());
    }
    private void loadProduits() {
        List<Produit> produits = serviceProduit.getAllProduits();
        ObservableList<Produit> observableProduits = FXCollections.observableArrayList(produits);
        tableProduits.setItems(observableProduits);
    }

    @FXML
    public void supprimerProduit() {
        if (selectedProduit != null) {
            serviceProduit.supprimerProduit(selectedProduit.getIdProduit());
            loadProduits();

        } else {
            showAlert("Aucun produit sélectionné", "Veuillez sélectionner un produit à supprimer.");
        }
    }
    private void showAlert(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // ou ERROR selon le cas
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    public void ajouterProduit() {
        try {
            // Vérifier si les champs ne sont pas vides
            if (nomField.getText().isEmpty() || prixField.getText().isEmpty() ||
                    stockField.getText().isEmpty() || categorieField.getText().isEmpty() || imageField.getText().isEmpty()) {
                showAlert("Erreur", "Tous les champs doivent être remplis.");
                return;
            }

            // Vérifier que le nom est une chaîne valide
            String nom = nomField.getText();
            if (!nom.matches("[a-zA-Z\\s]+")) {
                showAlert("Erreur", "Le nom doit être une chaîne de caractères valide.");
                return;
            }

            // Vérifier que le prix est un float valide
            float prix;
            try {
                prix = Float.parseFloat(prixField.getText());
            } catch (NumberFormatException e) {
                showAlert("Erreur", "Le prix doit être un nombre valide.");
                return;
            }

            // Vérifier que le stock est un entier valide
            int stock;
            try {
                stock = Integer.parseInt(stockField.getText());
            } catch (NumberFormatException e) {
                showAlert("Erreur", "Le stock doit être un entier valide.");
                return;
            }

            // Vérifier que la catégorie est une chaîne valide
            String categorie = categorieField.getText();
            if (categorie.isEmpty()) {
                showAlert("Erreur", "La catégorie doit être une chaîne valide.");
                return;
            }

            // Si toutes les validations sont passées, créer et ajouter le produit
            String image = imageField.getText();
            Produit produit = new Produit(0, nom, prix, stock, categorie, image,1);
            serviceProduit.ajouterProduit(produit);
            loadProduits();
            clearForm();
            showAlert("Succès", "Produit ajouté avec succès.");

        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite : " + e.getMessage());
        }
    }
    @FXML
    public void modifierProduit() {
        if (selectedProduit != null) {
            try {
                selectedProduit.setNom(nomField.getText());
                selectedProduit.setPrixProduit(Float.parseFloat(prixField.getText()));
                selectedProduit.setStock(Integer.parseInt(stockField.getText()));
                selectedProduit.setCategorie(categorieField.getText());
                selectedProduit.setImage(imageField.getText());

                serviceProduit.modifierProduit(selectedProduit);
                loadProduits();
                clearForm();
                showAlert("Succès", "Produit modifié avec succès.");
            } catch (Exception e) {
                showAlert("Erreur", "Vérifiez les champs : " + e.getMessage());
            }
        } else {
            showAlert("Aucun produit sélectionné", "Veuillez sélectionner un produit à modifier.");
        }
    }

    private void clearForm() {
        nomField.clear();
        prixField.clear();
        stockField.clear();
        categorieField.clear();
        imageField.clear();
        selectedProduit = null;
    }
    @FXML
    private void clearFormm() {
        nomField.clear();
        prixField.clear();
        stockField.clear();
        categorieField.clear();
        imageField.clear();
        selectedProduit = null;
    }
    @FXML
    public void parcourirImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imageField.setText(selectedFile.getAbsolutePath());
        }
    }
}
