package controllers;

import entite.Utilisateur;
import entite.Role;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import tools.SceneManager;
import tools.SessionManager;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MainController {

    @FXML
    private Button logoutButton; // Injected from FXML

    @FXML
    private Button gestionReclamationButton; // Injected from FXML

    @FXML
    private Button gestionOffreButton; // Injected from FXML
    @FXML
    private Button handleGestionReclamationButton; // Injected from FXML
    @FXML
    private Button gestionPubliciteButton; // Injected from FXML

    @FXML
    private Button gestionStadeButton; // Injected from FXML

    @FXML
    private Button gestionPanierButton; // Injected from FXML
    @FXML
    private Button gestionProduitButton;

    @FXML
    private Button gestionCommandeButton; // Injected from FXML

    @FXML
    private Button gestionReservationButton; // Injected from FXML

    @FXML
    private Button dashboardButton; // Injected from FXML

    @FXML
    private Button editProfileButton; // Injected from FXML

    @FXML
    public void openOffre() {
        openWindow("/publicite/OffreInterface.fxml", "Gestion des Offres");
    }

    @FXML
    public void openPublicite() {
        openWindow("/publicite/main_publicite.fxml", "Gestion des Publicités");
    }

    @FXML
    public void openStade() {
        openWindow("/stade/StadeInterface.fxml", "Gestion des Stades");
    }

    @FXML
    public void openGestionPanier() {
        openWindow("/consommation/AjouterPanier.fxml", "Gestion des Paniers");
    }
    @FXML
    public void openGestionProduit() {
        openWindow("/consommation/ProduitV.fxml", "Gestion des Produits");
    }

    @FXML
    public void openGestionCommande() {
        openWindow("/consommation/AjouterCommande.fxml", "Gestion des Commandes");
    }

    @FXML
    public void openreservation() {
        openWindow("/reservation/TicketClient.fxml", "Gestion des Réservations");
    }

    @FXML
    public void handleLogout() {
        // Clear the session
        SessionManager.logout();

        // Redirect to the login page
        Stage stage = (Stage) logoutButton.getScene().getWindow(); // Get the current stage
        SceneManager.switchScene(stage, "/user/login.fxml");
    }

    @FXML
    public void goToDashboard() {
        openWindow("/user/admin_dashboard.fxml", "Dashboard");
    }

    @FXML
    public void editProfile() {
        openWindow("/user/edit_user.fxml", "Edit Profile");
    }

    private void openWindow(String fxmlFile, String title) {
        try {
            // Use logoutButton to get the current stage
            Stage stage = (Stage) logoutButton.getScene().getWindow(); // Get the current stage
            SceneManager.switchScene(stage, fxmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Optional: Initialize method to customize the interface based on the user's role
    @FXML
    public void initialize() {
        Utilisateur currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            if (currentUser.getRole() == Role.ADMIN) {
                // Show admin-specific options
            } else {
                // Show user-specific options
            }
        }
    }
    @FXML
    public void handleGestionReclamation() {
        Utilisateur currentUser = SessionManager.getCurrentUser();

        if (currentUser != null) {
            Stage stage = (Stage) handleGestionReclamationButton.getScene().getWindow();

            if (currentUser.getRole() == Role.ADMIN) {
                SceneManager.switchScene(stage, "/reclamation/adminReclamation.fxml");
            } else {
                SceneManager.switchScene(stage, "/reclamation/UserReclamation.fxml");
            }
        } else {
            showAlert("Erreur", "Vous devez être connecté pour accéder à cette page.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}