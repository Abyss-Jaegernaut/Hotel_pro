package hotelpro.controllers;

import hotelpro.ApplicationPrincipale;
import hotelpro.models.*;
import hotelpro.utils.*;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Label;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ControleurRapports {
    
    @FXML
    private PieChart graphiqueStatuts;
    
    @FXML
    private BarChart<String, Number> graphiqueRevenus;
    
    @FXML
    private Label labelRevenuTotal;
    
    @FXML
    private Label labelNombreReservations;
    
    @FXML
    private Label labelTauxMoyen;
    
    @FXML
    private Label labelChiffreAffaires;
    
    @FXML
    public void initialize() {
        chargerStatistiques();
        genererGraphiques();
    }
    
    private void chargerStatistiques() {
        try {
            List<Reservation> reservations = ReservationDAO.obtenirToutes();
            List<Facture> factures = FactureDAO.obtenirToutes();
            List<Chambre> chambres = ChambreDAO.obtenirToutes();
        
        // Calculer le revenu total
        double revenuTotal = factures.stream()
            .filter(Facture::getEstPayee)
            .mapToDouble(Facture::getMontantTotal)
            .sum();
        labelRevenuTotal.setText(String.format("%.2f €", revenuTotal));
        
        // Nombre de réservations
        labelNombreReservations.setText(String.valueOf(reservations.size()));
        
        // Taux d'occupation moyen
        long chambresOccupees = chambres.stream()
            .filter(c -> c.getStatut() == StatutChambre.OCCUPEE)
            .count();
        double tauxOccupation = (double) chambresOccupees / chambres.size() * 100;
        labelTauxMoyen.setText(String.format("%.1f%%", tauxOccupation));
        
        // Chiffre d'affaires potentiel
        double chiffreAffaires = reservations.stream()
            .filter(r -> r.getStatut() == StatutReservation.EN_COURS || 
                        r.getStatut() == StatutReservation.CONFIRMEE)
            .mapToDouble(Reservation::getMontantTotal)
            .sum();
            labelChiffreAffaires.setText(String.format("%.2f €", chiffreAffaires));
        } catch (SQLException e) {
            e.printStackTrace();
            labelRevenuTotal.setText("--");
            labelNombreReservations.setText("--");
            labelTauxMoyen.setText("--");
            labelChiffreAffaires.setText("--");
        }
    }
    
    private void genererGraphiques() {
        try {
            List<Chambre> chambres = ChambreDAO.obtenirToutes();
            List<Reservation> reservations = ReservationDAO.obtenirToutes();
        
        // Graphique en camembert - Statut des chambres
        Map<StatutChambre, Long> statutsCount = chambres.stream()
            .collect(Collectors.groupingBy(Chambre::getStatut, Collectors.counting()));
        
        graphiqueStatuts.getData().clear();
        statutsCount.forEach((statut, count) -> {
            PieChart.Data slice = new PieChart.Data(
                statut.getNomAffichage() + " (" + count + ")", 
                count
            );
            graphiqueStatuts.getData().add(slice);
        });
        graphiqueStatuts.setLegendVisible(true);
        
        // Graphique en barres - Revenus par type de chambre
        Map<TypeChambre, Double> revenuParType = reservations.stream()
            .collect(Collectors.groupingBy(
                r -> r.getChambre().getType(),
                Collectors.summingDouble(Reservation::getMontantTotal)
            ));
        
        CategoryAxis xAxis = (CategoryAxis) graphiqueRevenus.getXAxis();
        NumberAxis yAxis = (NumberAxis) graphiqueRevenus.getYAxis();
        yAxis.setLabel("Revenu (€)");
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Revenus par Type");
        
        revenuParType.forEach((type, revenu) -> {
            series.getData().add(new XYChart.Data<>(type.getNomAffichage(), revenu));
        });
        
            graphiqueRevenus.getData().clear();
            graphiqueRevenus.getData().add(series);
            graphiqueRevenus.setLegendVisible(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void exporterPDF() {
        // Mock - juste afficher une alerte
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Export PDF");
        alert.setHeaderText("Fonctionnalité de démonstration");
        alert.setContentText("Les rapports seraient exportés en PDF ici.");
        alert.showAndWait();
    }
    
    @FXML
    private void exporterCSV() {
        // Mock - juste afficher une alerte
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Export CSV");
        alert.setHeaderText("Fonctionnalité de démonstration");
        alert.setContentText("Les données seraient exportées en CSV ici.");
        alert.showAndWait();
    }
    
    @FXML
    private void retourTableauDeBord() {
        try {
            ApplicationPrincipale.changerScene("views/tableau-de-bord.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
