package org.example; import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Use Parent instead of AnchorPane
        Parent root = FXMLLoader.load(getClass().getResource("/user/home.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(" welcome to TuniStadium");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}