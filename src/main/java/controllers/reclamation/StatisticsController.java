package controllers.reclamation;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import services.ReclamationService;
import java.util.Map;
import javafx.application.Platform;

public class StatisticsController {

    @FXML
    private PieChart pieChart; // Reference to the PieChart in the FXML

    // Method to initialize and display statistics
    public void initializeStatistics() {
        ReclamationService reclamationService = new ReclamationService();

        // Get statistics by etat
        Map<String, Integer> reclamationsByEtat = reclamationService.getReclamationsByEtat();

        // Clear any existing data in the pie chart
        pieChart.getData().clear();

        // Calculate the total number of reclamations
        int totalReclamations = reclamationsByEtat.values().stream().mapToInt(Integer::intValue).sum();

        // Add data to the pie chart and calculate percentages
        for (Map.Entry<String, Integer> entry : reclamationsByEtat.entrySet()) {
            String etat = entry.getKey();
            int count = entry.getValue();
            double percentage = (totalReclamations > 0) ? (count * 100.0 / totalReclamations) : 0;

            // Create a PieChart.Data object with the etat and count
            PieChart.Data data = new PieChart.Data(etat, count);

            // Add the data to the pie chart
            pieChart.getData().add(data);

            // Customize the label to show the percentage
            data.nameProperty().bind(
                    javafx.beans.binding.Bindings.concat(
                            etat, " (", String.format("%.1f", percentage), "%)"
                    )
            );
        }

        // Customize the pie chart appearance
        pieChart.setTitle("Réclamations par État");
        pieChart.setLegendVisible(true);
        pieChart.setClockwise(true);
        pieChart.setLabelsVisible(true);

        // Apply slice colors after the chart is rendered
        Platform.runLater(() -> {
            for (PieChart.Data data : pieChart.getData()) {
                switch (data.getName()) {
                    case "en_attente":
                        data.getNode().setStyle("-fx-pie-color: #008080;"); // Teal
                        break;
                    case "en_cours":
                        data.getNode().setStyle("-fx-pie-color: #ff4d4d;"); // Red
                        break;
                    case "termine":
                        data.getNode().setStyle("-fx-pie-color: #00bfae;"); // Light Teal
                        break;
                }
            }
        });

        // Add tooltips to pie chart slices
        for (PieChart.Data data : pieChart.getData()) {
            Tooltip tooltip = new Tooltip(String.format("%s: %.1f%% (%d)", data.getName(), (data.getPieValue() / totalReclamations * 100), (int) data.getPieValue()));
            Tooltip.install(data.getNode(), tooltip);
        }
    }

    // Method to close the window
    @FXML
    private void closeWindow() {
        Stage stage = (Stage) pieChart.getScene().getWindow();
        stage.close();
    }
}