package controllers.user;

import entite.Utilisateur;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import tools.SceneManager;

public class UserProfileController {

    @FXML
    private Button backButton;

    @FXML
    private Label nomLabel;
    @FXML
    private Label prenomLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label roleLabel;

    private Utilisateur selectedUser;

    // Method to set the selected user to display in the profile
    public void setUser(Utilisateur user) {
        this.selectedUser = user;
        // Set profile labels with the user's information
        nomLabel.setText(user.getNom());
        prenomLabel.setText(user.getPrenom());
        emailLabel.setText(user.getEmail());
        roleLabel.setText(user.getRole().toString());  // Assuming Role is an enum
    }

    // Method to handle going back to the Admin Dashboard
    @FXML
    private void goBack() {
        // Switch back to the admin dashboard screen
        Stage stage = (Stage) backButton.getScene().getWindow();
        SceneManager.switchScene(stage, "/user/admin_dashboard.fxml");
    }
}
