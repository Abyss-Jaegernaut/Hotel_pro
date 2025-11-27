package hotelpro.controllers;

import hotelpro.ApplicationPrincipale;
import hotelpro.models.*;
import hotelpro.utils.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.sql.SQLException;
import java.util.List;

public class ControleurTableauDeBord {
    
    @FXML
    private Label labelBienvenue;
    
    @FXML
    private Label labelNombreChambres;
    
    @FXML
    private Label labelChambresDisponibles;
    
    @FXML
    private Label labelTauxOccupation;
    
    @FXML
    private Label labelReservationsActives;
    
    @FXML
    private VBox conteneurPrincipal;
    
    private Utilisateur utilisateurConnecte;
    
    @FXML
    public void initialize() {
        utilisateurConnecte = ControleurConnexion.getUtilisateurConnecte();
        
        if (utilisateurConnecte != null) {
            labelBienvenue.setText("Bienvenue, " + utilisateurConnecte.getNomComplet());
        }
        
        chargerStatistiques();
    }
    
    private void chargerStatistiques() {
        try {
            List<Chambre> chambres = ChambreDAO.obtenirToutes();
            List<Reservation> reservations = ReservationDAO.obtenirToutes();
            
            if (chambres == null) chambres = List.of();
            if (reservations == null) reservations = List.of();
        
            int nombreTotal = chambres.size();
            long disponibles = chambres.stream()
                .filter(c -> c.getStatut() == StatutChambre.DISPONIBLE)
                .count();
            
            // Calculer le taux d'occupation basé sur les chambres NON disponibles
            long nonDisponibles = nombreTotal - disponibles;
            double tauxOccupation = nombreTotal > 0 ? (double) nonDisponibles / nombreTotal * 100 : 0.0;
            
            long reservationsActives = reservations.stream()
                .filter(r -> r.getStatut() == StatutReservation.EN_COURS || 
                            r.getStatut() == StatutReservation.CONFIRMEE)
                .count();
            
            labelNombreChambres.setText(String.valueOf(nombreTotal));
            labelChambresDisponibles.setText(String.valueOf(disponibles));
            labelTauxOccupation.setText(String.format("%.1f%%", tauxOccupation));
            labelReservationsActives.setText(String.valueOf(reservationsActives));
        } catch (Exception e) {
            e.printStackTrace();
            // Valeurs par défaut en cas d'erreur
            labelNombreChambres.setText("0");
            labelChambresDisponibles.setText("0");
            labelTauxOccupation.setText("0.0%");
            labelReservationsActives.setText("0");
        }
    }
    
    @FXML
    private void allerVersChambres() {
        try {
            ApplicationPrincipale.changerScene("views/gestion-chambres.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void allerVersClients() {
        try {
            ApplicationPrincipale.changerScene("views/gestion-clients.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void allerVersReservations() {
        try {
            ApplicationPrincipale.changerScene("views/gestion-reservations.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void allerVersFacturation() {
        try {
            ApplicationPrincipale.changerScene("views/gestion-facturation.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void allerVersRapports() {
        try {
            ApplicationPrincipale.changerScene("views/rapports.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void seDeconnecter() {
        try {
            ApplicationPrincipale.changerScene("views/connexion.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
