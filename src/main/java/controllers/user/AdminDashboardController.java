package controllers.user;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import tools.SceneManager;
import entite.Utilisateur;
import services.UtilisateurService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import entite.StatutAbonnement;

import java.sql.SQLException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Comparator;

public class AdminDashboardController {

    @FXML
    private TableView<Utilisateur> userTable;
    @FXML
    private TableColumn<Utilisateur, String> nomColumn;
    @FXML
    private TableColumn<Utilisateur, String> prenomColumn;
    @FXML
    private TableColumn<Utilisateur, String> emailColumn;
    @FXML
    private TableColumn<Utilisateur, String> roleColumn;
    @FXML
    private TableColumn<Utilisateur, String> statutAbonnementColumn;

    @FXML
    private TextField searchField;
    @FXML
    private Button addUserButton;
    @FXML
    private Button editUserButton;
    @FXML
    private Button viewProfileButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Button exportButton;
    @FXML
    private Button logoutButton;
    @FXML

    private Button backToMainButton; // Injected from FXML

    private UtilisateurService utilisateurService = new UtilisateurService();

    @FXML
    private void initialize() {
        try {
            // Populate the user table
            userTable.setItems(FXCollections.observableArrayList(utilisateurService.getAll()));
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors du chargement des utilisateurs.");
        }

        // Bind columns to their corresponding properties
        nomColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        prenomColumn.setCellValueFactory(cellData -> cellData.getValue().prenomProperty());
        emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

        // Bind the role column to the display name of the Role enum
        roleColumn.setCellValueFactory(cellData -> cellData.getValue().getRole().getDisplayNameProperty());

        // Bind the statutAbonnement column to the StatutAbonnement enum
        statutAbonnementColumn.setCellValueFactory(cellData ->
                cellData.getValue().statutAbonnementProperty().asString());

        // Add a comparator for sorting the "statutAbonnement" column
        statutAbonnementColumn.setComparator(Comparator.comparing(statut ->
                StatutAbonnement.valueOf(statut).getDisplayName()));

        // Set default sort order for statutAbonnement column
        statutAbonnementColumn.setSortType(TableColumn.SortType.ASCENDING);
        userTable.getSortOrder().add(statutAbonnementColumn);
    }

    @FXML
    private void searchUser() {
        String searchText = searchField.getText();
        try {
            List<Utilisateur> filteredUsers = utilisateurService.searchUsersByName(searchText);
            userTable.setItems(FXCollections.observableArrayList(filteredUsers));
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la recherche des utilisateurs.");
        }
    }

    @FXML
    private void addUser() {
        try {
            SceneManager.switchScene((Stage) addUserButton.getScene().getWindow(), "/user/add_user.fxml");
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de l'ajout de l'utilisateur.");
        }
    }

    @FXML
    private void editUser() {
        try {
            Utilisateur selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/edit_user.fxml"));
                Parent root = loader.load();

                EditUserController editUserController = loader.getController();
                editUserController.setUser(selectedUser);

                Stage stage = (Stage) editUserButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                showAlert("Erreur", "Veuillez sélectionner un utilisateur à modifier.");
            }
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de la modification de l'utilisateur.");
        }
    }

    @FXML
    private void viewUserProfile() {
        try {
            Utilisateur selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/user_profile.fxml"));
                Parent root = loader.load();

                UserProfileController profileController = loader.getController();
                profileController.setUser(selectedUser);

                Stage stage = (Stage) viewProfileButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                showAlert("Erreur", "Veuillez sélectionner un utilisateur pour voir son profil.");
            }
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de l'affichage du profil utilisateur.");
        }
    }

    @FXML
    private void deleteUser() {
        try {
            Utilisateur selectedUser = userTable.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Supprimer l'utilisateur");
                confirmAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
                confirmAlert.setContentText("Cette action est irréversible.");

                if (confirmAlert.showAndWait().get().getButtonData().isDefaultButton()) {
                    utilisateurService.supprimer(selectedUser.getIdUser());
                    userTable.getItems().remove(selectedUser);
                    showAlert("Succès", "L'utilisateur a été supprimé avec succès.");
                }
            } else {
                showAlert("Erreur", "Veuillez sélectionner un utilisateur à supprimer.");
            }
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de la suppression de l'utilisateur.");
        }
    }

    @FXML
    private void exportToPDF() {
        List<Utilisateur> users = userTable.getItems();
        Document document = new Document();

        try {
            // Change the file path to save the PDF in D:/Downloads
            PdfWriter.getInstance(document, new FileOutputStream("D:/Downloads/users_list.pdf"));
            document.open();

            // Title
            Paragraph title = new Paragraph("Liste des Utilisateurs", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16));
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);

            // Users Data
            for (Utilisateur user : users) {
                Paragraph userData = new Paragraph(
                        "Nom: " + user.getNom() + " | " +
                                "Prénom: " + user.getPrenom() + " | " +
                                "Email: " + user.getEmail() + " | " +
                                "Rôle: " + user.getRole() + " | " +
                                "Statut Abonnement: " + user.getStatutAbonnement(),
                        FontFactory.getFont(FontFactory.HELVETICA, 12)
                );
                document.add(userData);
                document.add(new Paragraph("\n"));
            }

            document.close();
            showAlert("Succès", "Le fichier PDF a été généré avec succès! Il est sauvegardé dans D:/Downloads.");
        } catch (DocumentException | IOException e) {
            showAlert("Erreur", "Erreur lors de l'exportation du fichier PDF.");
        }
    }

    @FXML
    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/login.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            showAlert("Erreur", "Erreur lors de la déconnexion.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
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
