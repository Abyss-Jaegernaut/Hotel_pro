package hotelpro.controllers;

import hotelpro.ApplicationPrincipale;
import hotelpro.models.Utilisateur;
import hotelpro.utils.UtilisateurDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.sql.SQLException;

public class ControleurConnexion {
    
    @FXML
    private TextField champNomUtilisateur;
    
    @FXML
    private PasswordField champMotDePasse;
    
    @FXML
    private Label labelErreur;
    
    @FXML
    private Button boutonConnexion;
    
    @FXML
    private ComboBox<String> comboRole;
    
    @FXML
    private VBox conteneurConnexion;
    
    private static Utilisateur utilisateurConnecte;
    
    @FXML
    public void initialize() {
        // Initialiser le ComboBox avec les rôles (pour démonstration)
        comboRole.getItems().addAll("Réceptionniste", "Administrateur");
        comboRole.setValue("Réceptionniste");
        
        labelErreur.setVisible(false);
        
        // Ajouter l'effet de focus
        champNomUtilisateur.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                champMotDePasse.requestFocus();
            }
        });
        
        champMotDePasse.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                seConnecter();
            }
        });
    }
    
    @FXML
    private void seConnecter() {
        String nomUtilisateur = champNomUtilisateur.getText();
        String motDePasse = champMotDePasse.getText();
        
        if (nomUtilisateur.isEmpty() || motDePasse.isEmpty()) {
            afficherErreur("Veuillez remplir tous les champs");
            return;
        }
        
        // Authentification via base de données
        Utilisateur utilisateur = null;
        try {
            utilisateur = UtilisateurDAO.authentifier(nomUtilisateur, motDePasse);
        } catch (SQLException e) {
            e.printStackTrace();
            afficherErreur("Erreur de connexion à la base de données");
            return;
        }
        
        if (utilisateur != null) {
            utilisateurConnecte = utilisateur;
            try {
                // Rediriger selon le rôle
                if (utilisateur.getRole() == hotelpro.models.RoleUtilisateur.CLIENT) {
                    ApplicationPrincipale.changerScene("views/client/tableau-de-bord-client.fxml");
                } else {
                    ApplicationPrincipale.changerScene("views/tableau-de-bord.fxml");
                }
            } catch (Exception e) {
                e.printStackTrace();
                afficherErreur("Erreur lors du chargement du tableau de bord");
            }
        } else {
            afficherErreur("Nom d'utilisateur ou mot de passe incorrect");
        }
    }

    @FXML
    public void accesVisiteur() {
        try {
            ApplicationPrincipale.changerScene("views/client/tableau-de-bord-client.fxml");
        } catch (Exception e) {
            e.printStackTrace();
            afficherErreur("Erreur lors du chargement de l'interface client");
        }
    }
    
    private void afficherErreur(String message) {
        labelErreur.setText(message);
        labelErreur.setVisible(true);
        
        // Animation de shake (optionnel)
        conteneurConnexion.setStyle("-fx-translate-x: 5px;");
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(
            new javafx.animation.KeyFrame(javafx.util.Duration.millis(100), e -> conteneurConnexion.setStyle("-fx-translate-x: -5px;")),
            new javafx.animation.KeyFrame(javafx.util.Duration.millis(200), e -> conteneurConnexion.setStyle("-fx-translate-x: 5px;")),
            new javafx.animation.KeyFrame(javafx.util.Duration.millis(300), e -> conteneurConnexion.setStyle("-fx-translate-x: 0px;"))
        );
        timeline.play();
    }
    
    public static Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }
}
