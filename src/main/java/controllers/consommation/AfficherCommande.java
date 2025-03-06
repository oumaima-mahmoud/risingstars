package controllers.consommation;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import entite.Commande;
import services.ServiceCommande;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import tools.SceneManager;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.List;

public class AfficherCommande {

    @FXML
    private TableView<Commande> tableview;
    @FXML
    private TableColumn<Commande, Integer> idCommande;
    @FXML
    private TableColumn<Commande, Integer> quantite;
    @FXML
    private TableColumn<Commande, String> dateCommande;
    @FXML
    private TableColumn<Commande, Double> prix;
    @FXML
    private TableColumn<Commande, Integer> idPanier;
    @FXML
    private ImageView qrImageView; // ImageView pour afficher le QR Code
    @FXML
    private Button backToMainButton; // Injected from FXML

    private final ServiceCommande commandeService = new ServiceCommande();
    private boolean ascendant;

    @FXML
    public void initialize() {
        // Initialisation des colonnes de la TableView
        idCommande.setCellValueFactory(new PropertyValueFactory<>("idCommande"));
        quantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        dateCommande.setCellValueFactory(new PropertyValueFactory<>("dateCommande"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        idPanier.setCellValueFactory(new PropertyValueFactory<>("idPanier"));

        chargerCommandes();
    }

    private void chargerCommandes() {
        try {
            List<Commande> commandes = commandeService.afficherCommandes();
            ObservableList<Commande> observableList = FXCollections.observableArrayList(commandes);
            tableview.setItems(observableList);
        } catch (Exception e) {
            afficherAlerte("Erreur de chargement", "Impossible de charger les commandes !\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void rechercher(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/consommation/RechercherCommande.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Recherche Commande");
            stage.show();
        } catch (IOException e) {
            afficherAlerte("Erreur de recherche", "Erreur lors du chargement de la fenêtre de recherche !\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void trierParPrix(ActionEvent event) {
        try {
            List<Commande> commandesTries = commandeService.trierParPrix(ascendant);
            tableview.setItems(FXCollections.observableArrayList(commandesTries));
            ascendant = !ascendant;
        } catch (Exception e) {
            afficherAlerte("Erreur de tri", "Impossible de trier les commandes !\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void statistiquss(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/consommation/statistiquesPdfCommande.fxml"));
            AnchorPane root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Statistiques");
            stage.show();
        } catch (IOException e) {
            afficherAlerte("Erreur de recherche", "Erreur lors du chargement de la fenêtre de recherche !\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    // Méthode pour générer le QR Code
    @FXML
    private void onGenerateQRCode(ActionEvent event) {
        Commande selectedCommande = tableview.getSelectionModel().getSelectedItem();
        if (selectedCommande == null) {
            afficherAlerte("Aucune commande sélectionnée", "Veuillez sélectionner une commande pour générer le QR Code.");
            return;
        }

        String text = "Commande ID: " + selectedCommande.getIdCommande() +
                "\nQuantité: " + selectedCommande.getQuantite() +
                "\nPrix: " + selectedCommande.getPrix();

        int width = 250;
        int height = 250;
        Image qrImage = generateQRCode(text, width, height);
        qrImageView.setImage(qrImage);
    }

    private Image generateQRCode(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bufferedImage = bitMatrixToBufferedImage(bitMatrix);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            return new Image(new ByteArrayInputStream(outputStream.toByteArray()));
        } catch (WriterException | IOException e) {
            afficherAlerte("Erreur QR Code", "Impossible de générer le QR Code !");
            e.printStackTrace();
            return null;
        }
    }

    private static BufferedImage bitMatrixToBufferedImage(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }
        return bufferedImage;
    }

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
