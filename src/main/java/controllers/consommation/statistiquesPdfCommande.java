package controllers.consommation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.chart.PieChart;
import services.ServiceCommande;  // Importation correcte de ServiceCommande
import java.util.Map;

public class statistiquesPdfCommande {

    @FXML
    private VBox vbox;  // Conteneur pour afficher les éléments visuels (par exemple, un graphique)

    @FXML
    private Button btnAfficherStatistiques;  // Bouton pour afficher les statistiques

    private ServiceCommande serviceCommande = new ServiceCommande();  // Initialisation du service pour calculer les statistiques

    @FXML
    public void afficher(ActionEvent event) {
        try {
            // Obtenir les statistiques par pourcentage des quantités des commandes
            Map<String, Double> pourcentages = serviceCommande.obtenirStatistiquePourcentageQuantiteCommande();

            // Afficher un message si les données sont disponibles
            if (pourcentages.isEmpty()) {
                showAlert("Aucune statistique disponible", "Il n'y a pas de données à afficher.");
            } else {
                // Réinitialiser le contenu de vbox (enlever les éléments précédents)
                vbox.getChildren().clear();

                // Créer un PieChart
                PieChart pieChart = new PieChart();

                // Ajouter chaque catégorie avec son pourcentage comme une tranche dans le PieChart
                for (Map.Entry<String, Double> entry : pourcentages.entrySet()) {
                    String categorie = entry.getKey();  // Par exemple, catégorie "Petite quantité (<5)"
                    double pourcentage = entry.getValue();  // Pourcentage des commandes pour cette catégorie

                    // Créer la donnée de la tranche (avec libellé et pourcentage)
                    PieChart.Data data = new PieChart.Data(categorie + ": " + String.format("%.1f", pourcentage) + "%", pourcentage);

                    // Ajouter la donnée au PieChart
                    pieChart.getData().add(data);

                    // Vérifier si le Node est non-null avant d'appliquer le style
                    if (data.getNode() != null) {
                        data.getNode().setStyle("-fx-pie-color: " + getColorForCategorie(categorie));  // Utilisation de la couleur par catégorie
                    }
                }

                // Ajouter le PieChart à la VBox
                vbox.getChildren().add(pieChart);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur est survenue lors du calcul des statistiques.");
        }
    }

    // Méthode pour associer une couleur spécifique à chaque catégorie de quantités
    private String getColorForCategorie(String categorie) {
        switch (categorie) {
            case "Petite quantité (<5)":
                return "#1E90FF";  // Bleu pour "Petite quantité"
            case "Quantité moyenne (5-10)":
                return "#FFD700";  // Jaune pour "Quantité moyenne"
            case "Grande quantité (>10)":
                return "#32CD32";  // Vert pour "Grande quantité"
            default:
                return "#808080";  // Gris par défaut si la catégorie est inconnue
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
            serviceCommande.exportToPDF(); // Passez le chemin du fichier à la méthode

            // Afficher un message de succès
            Alert alert = new Alert(AlertType.INFORMATION); // Correction du type d'Alert
            alert.setTitle("Exportation réussie");
            alert.setHeaderText(null);
            alert.setContentText("Le PDF a été exporté et ouvert avec succès !");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();

            // Afficher un message d'erreur en cas d'exception
            Alert alert = new Alert(AlertType.ERROR); // Correction du type d'Alert
            alert.setTitle("Erreur d'exportation");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur est survenue lors de l'exportation du PDF.");
            alert.showAndWait();
        }
    }
}
