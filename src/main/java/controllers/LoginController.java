package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cbRole;


    @FXML
    private TextField idmail;

    @FXML
    private PasswordField idmdp;

    @FXML
    void handleLogin(ActionEvent event) {

    }

    @FXML
    void initialize() {
    }

}
