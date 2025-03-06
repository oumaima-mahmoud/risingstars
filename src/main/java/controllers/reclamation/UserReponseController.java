package controllers.reclamation;

import entite.Reponse;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import services.ReponseService;

public class UserReponseController {
    @FXML
    private ListView<Reponse> reponseListView;

    private int reclamationId;

    public void setReclamationId(int reclamationId) {
        this.reclamationId = reclamationId;
        loadResponses();
    }

    private void loadResponses() {
        ReponseService reponseService = new ReponseService();
        reponseListView.getItems().setAll(reponseService.getReponsesByReclamationId(reclamationId));
    }
}