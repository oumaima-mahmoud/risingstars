package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.chart.PieChart;
import services.ServicePanier;  // Corriger l'importation de ServicePanier
import java.util.Map;

public class statistiquesPdf {

    @FXML
    private VBox vbox;  // Conteneur pour afficher les éléments visuels (par exemple, un graphique)

    @FXML
    private Button btnAfficherStatistiques;  // Bouton pour afficher les statistiques

    private ServicePanier servicePanier = new ServicePanier();  // Initialisation du service pour calculer les statistiques

    @FXML
    public void afficher(ActionEvent event) {
        // Obtenir les pourcentages des états des paniers
        Map<String, Double> pourcentages = servicePanier.obtenirStatistiquePourcentageEtatPanier();

        // Afficher un message si les données sont disponibles
        if (pourcentages.isEmpty()) {
            showAlert("Aucune statistique disponible", "Il n'y a pas de données à afficher.");
        } else {
            // Réinitialiser le contenu de vbox (enlever les éléments précédents)
            vbox.getChildren().clear();

            // Créer un PieChart
            PieChart pieChart = new PieChart();

            // Ajouter chaque état comme une tranche dans le PieChart
            for (Map.Entry<String, Double> entry : pourcentages.entrySet()) {
                String etat = entry.getKey();
                double percentage = entry.getValue();

                // Créer la donnée de la tranche (avec libellé et pourcentage)
                PieChart.Data data = new PieChart.Data(etat + ": " + String.format("%.1f", percentage) + "%", percentage);

                // Ajouter la donnée au PieChart (avant d'appliquer un style)
                pieChart.getData().add(data);

                // Vérifier si le Node est non-null avant d'appliquer le style
                if (data.getNode() != null) {
                    data.getNode().setStyle("-fx-pie-color: " + getColorForEtat(etat));  // Utilisation de la couleur par état
                }
            }

            // Ajouter le PieChart à la VBox
            vbox.getChildren().add(pieChart);
        }
    }

    // Méthode pour associer une couleur spécifique à chaque état du panier
    private String getColorForEtat(String etat) {
        switch (etat) {
            case "validé":
                return "#008000";  // Vert pour "validé"
            case "en cours":
                return "#FFA500";  // Orange pour "en cours"
            case "annulé":
                return "#FF0000";  // Rouge pour "annulé"
            default:
                return "#808080";  // Gris par défaut si l'état est inconnu
        }
    }

    // Méthode pour afficher une alerte si aucune statistique n'est disponible
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);  // Pas de titre pour l'entête
        alert.setContentText(message);  // Message d'alerte
        alert.showAndWait();  // Afficher l'alerte et attendre que l'utilisateur la ferme
    }
    @FXML
    private void exportPDF(ActionEvent event) {
        try {
            // Définir le chemin du fichier PDF (assurez-vous qu'il existe et que le fichier est accessible)
            String filePath = "C:\\IntelliJ IDEA 2024.2.1\\Consommation\\fichier.pdf"; // Remplacez ceci par le chemin réel du fichier PDF à ouvrir

            // Appeler la méthode exportToPDF avec le chemin du fichier
            servicePanier.exportToPDF(); // Passez le chemin du fichier à la méthode

            // Afficher un message de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION); // Correction du type d'Alert
            alert.setTitle("Exportation réussie");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF a été exporté et ouvert avec succès !");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();

            // Afficher un message d'erreur en cas d'exception
            Alert alert = new Alert(Alert.AlertType.ERROR); // Correction du type d'Alert
            alert.setTitle("Erreur d'exportation");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'exportation du PDF.");
            alert.showAndWait();
        }
    }



}

