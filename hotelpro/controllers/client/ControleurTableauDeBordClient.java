package hotelpro.controllers.client;

import hotelpro.models.Chambre;
import hotelpro.models.StatutChambre;
import hotelpro.utils.ChambreDAO;
import hotelpro.utils.GestionnaireBD;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.List;

public class ControleurTableauDeBordClient {

    @FXML private FlowPane conteneurChambres;
    @FXML private DatePicker dateDebut;
    @FXML private DatePicker dateFin;
    @FXML private Label labelStatus;



    public void initialize() {
        dateDebut.setValue(LocalDate.now());
        dateFin.setValue(LocalDate.now().plusDays(1));
        chargerChambres();
    }

    @FXML
    private void rechercher() {
        chargerChambres();
    }

    private void chargerChambres() {
        conteneurChambres.getChildren().clear();
        try {
            List<Chambre> chambres = ChambreDAO.obtenirToutes(); // Idéalement filtrer par disponibilité
            
            for (Chambre chambre : chambres) {
                if (chambre.getStatut() == StatutChambre.DISPONIBLE) {
                    conteneurChambres.getChildren().add(creerCarteChambre(chambre));
                }
            }
            
            if (conteneurChambres.getChildren().isEmpty()) {
                labelStatus.setText("Aucune chambre disponible pour ces dates.");
            } else {
                labelStatus.setText(conteneurChambres.getChildren().size() + " chambres trouvées.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            labelStatus.setText("Erreur lors du chargement des chambres.");
        }
    }

    private VBox creerCarteChambre(Chambre chambre) {
        VBox carte = new VBox(10);
        carte.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        carte.setPrefWidth(250);

        Label typeLabel = new Label(chambre.getType().getNomAffichage());
        typeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label prixLabel = new Label(String.format("%.2f € / nuit", chambre.getPrix()));
        prixLabel.setStyle("-fx-text-fill: #2563EB; -fx-font-weight: bold;");

        Label descLabel = new Label(chambre.getType().getDescription());
        descLabel.setWrapText(true);

        javafx.scene.control.Button reserverBtn = new javafx.scene.control.Button("Réserver");
        reserverBtn.getStyleClass().add("button-primary");
        reserverBtn.setMaxWidth(Double.MAX_VALUE);
        reserverBtn.setOnAction(e -> reserverChambre(chambre));

        carte.getChildren().addAll(typeLabel, prixLabel, descLabel, reserverBtn);
        return carte;
    }

    private void reserverChambre(Chambre chambre) {
        // Logique de réservation à implémenter
        System.out.println("Réservation pour la chambre " + chambre.getNumeroChambre());
        // Ouvrir formulaire de réservation
    }
}
