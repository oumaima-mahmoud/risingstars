package controllers;

import entities.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import Services.UtilisateurService;

public class DashboardController {
    @FXML private ListView<Utilisateur> usersList;

    private final UtilisateurService userService = new UtilisateurService();
    private final ObservableList<Utilisateur> users = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadUsers();
    }

    private void loadUsers() {
        users.setAll(userService.getAll());
        usersList.setItems(users);
    }

    @FXML
    private void handleAdd() {
        openForm("/views/user-form.fxml", null);
    }

    @FXML
    private void handleEdit() {
        Utilisateur selected = usersList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            openForm("/views/user-form.fxml", selected);
        }
    }

    @FXML
    private void handleDelete() {
        Utilisateur selected = usersList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            userService.supprimer(selected.getIdUser());
            loadUsers();
        }
    }

    private void openForm(String fxmlPath, Utilisateur user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            UserFormController controller = loader.getController();
            controller.setUser(user);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadUsers(); // Rafraîchir après fermeture
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}