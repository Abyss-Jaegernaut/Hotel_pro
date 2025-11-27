package hotelpro;

import hotelpro.utils.GestionnaireBD;
import hotelpro.utils.InitialisateurDonnees;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class ApplicationPrincipale extends Application {
    
    private static Stage stagePrincipale;
    
    @Override
    public void start(Stage stage) throws Exception {
        // Initialiser la base de données
        System.out.println("=== Initialisation de Hotel Manager Pro ===");
        try {
            GestionnaireBD.initialiserBD();
            InitialisateurDonnees.insererDonneesDemo();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de Démarrage");
            alert.setHeaderText("Impossible d'initialiser la base de données");
            alert.setContentText("Vérifiez que le driver SQLite est présent.\n\nErreur: " + e.getMessage());
            alert.showAndWait();
        }
        System.out.println("===========================================\n");
        
        stagePrincipale = stage;
        
        // Charger la vue de connexion
        Parent root = FXMLLoader.load(getClass().getResource("views/connexion.fxml"));
        Scene scene = new Scene(root, 1200, 700);
        
        // Appliquer le CSS
        scene.getStylesheets().add(getClass().getResource("/resources/css/styles.css").toExternalForm());
        
        stagePrincipale.setTitle("Hotel Manager Pro");
        stagePrincipale.setScene(scene);
        stagePrincipale.setResizable(true);
        stagePrincipale.show();
    }
    
    public static Stage getStagePrincipale() {
        return stagePrincipale;
    }
    
    public static void changerScene(String fxmlPath) throws Exception {
        Parent root = FXMLLoader.load(ApplicationPrincipale.class.getResource(fxmlPath));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(ApplicationPrincipale.class.getResource("/resources/css/styles.css").toExternalForm());
        stagePrincipale.setScene(scene);
    }
    
    @Override
    public void stop() throws Exception {
        // Fermer la connexion à la base de données
        GestionnaireBD.fermerConnexion();
        super.stop();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
