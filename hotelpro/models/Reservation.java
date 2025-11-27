package hotelpro.models;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Reservation {
    private final IntegerProperty id;
    private final ObjectProperty<Client> client;
    private final ObjectProperty<Chambre> chambre;
    private final ObjectProperty<LocalDate> dateArrivee;
    private final ObjectProperty<LocalDate> dateDepart;
    private final ObjectProperty<StatutReservation> statut;
    private final DoubleProperty montantTotal;
    
    public Reservation(int id, Client client, Chambre chambre, LocalDate dateArrivee, LocalDate dateDepart, StatutReservation statut) {
        this.id = new SimpleIntegerProperty(id);
        this.client = new SimpleObjectProperty<>(client);
        this.chambre = new SimpleObjectProperty<>(chambre);
        this.dateArrivee = new SimpleObjectProperty<>(dateArrivee);
        this.dateDepart = new SimpleObjectProperty<>(dateDepart);
        this.statut = new SimpleObjectProperty<>(statut);
        this.montantTotal = new SimpleDoubleProperty(calculerTotal());
    }
    
    private double calculerTotal() {
        if (dateArrivee.get() != null && dateDepart.get() != null && chambre.get() != null) {
            long jours = java.time.temporal.ChronoUnit.DAYS.between(dateArrivee.get(), dateDepart.get());
            return jours * chambre.get().getPrix();
        }
        return 0.0;
    }
    
    // ID
    public int getId() {
        return id.get();
    }
    
    public IntegerProperty idProperty() {
        return id;
    }
    
    // Client
    public Client getClient() {
        return client.get();
    }
    
    public void setClient(Client valeur) {
        client.set(valeur);
    }
    
    public ObjectProperty<Client> clientProperty() {
        return client;
    }
    
    // Chambre
    public Chambre getChambre() {
        return chambre.get();
    }
    
    public void setChambre(Chambre valeur) {
        chambre.set(valeur);
        montantTotal.set(calculerTotal());
    }
    
    public ObjectProperty<Chambre> chambreProperty() {
        return chambre;
    }
    
    // Date d'Arrivée
    public LocalDate getDateArrivee() {
        return dateArrivee.get();
    }
    
    public void setDateArrivee(LocalDate valeur) {
        dateArrivee.set(valeur);
        montantTotal.set(calculerTotal());
    }
    
    public ObjectProperty<LocalDate> dateArriveeProperty() {
        return dateArrivee;
    }
    
    // Date de Départ
    public LocalDate getDateDepart() {
        return dateDepart.get();
    }
    
    public void setDateDepart(LocalDate valeur) {
        dateDepart.set(valeur);
        montantTotal.set(calculerTotal());
    }
    
    public ObjectProperty<LocalDate> dateDepartProperty() {
        return dateDepart;
    }
    
    // Statut
    public StatutReservation getStatut() {
        return statut.get();
    }
    
    public void setStatut(StatutReservation valeur) {
        statut.set(valeur);
    }
    
    public ObjectProperty<StatutReservation> statutProperty() {
        return statut;
    }
    
    // Montant Total
    public double getMontantTotal() {
        return montantTotal.get();
    }
    
    public DoubleProperty montantTotalProperty() {
        return montantTotal;
    }
}
