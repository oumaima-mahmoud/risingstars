package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class MainController extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // loader feha l url du fichier xml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/user-form.fxml"));
        try {
            // loader pour charger le fichier
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            // nthabet menha settitle
            primaryStage.setTitle("Connexion");
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (IOException e){
            e.printStackTrace(); // Pour afficher l'erreur dans la console
        }
            }


    }
