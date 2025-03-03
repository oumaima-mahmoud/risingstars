package gestion_satde.controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class MapViewController {
    @FXML
    private WebView mapView;

    private double latitude;
    private double longitude;

    public void setLocation(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
        System.out.println(longitude + " " + latitude);
        String mapUrl = "https://www.openstreetmap.org/#map=15/" + lat + "/" + lon;
        mapView.getEngine().load(mapUrl);
    }

    @FXML
    private void handleClose() {
        Stage stage = (Stage) mapView.getScene().getWindow();
        stage.close();
    }
}
