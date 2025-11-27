package hotelpro.models;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Facture {
    private final IntegerProperty id;
    private final ObjectProperty<Reservation> reservation;
    private final DoubleProperty fraisChambre;
    private final DoubleProperty taxes;
    private final DoubleProperty extras;
    private final DoubleProperty montantTotal;
    private final ObjectProperty<MethodePaiement> methodePaiement;
    private final BooleanProperty estPayee;
    private final ObjectProperty<LocalDate> dateEmission;
    
    public Facture(int id, Reservation reservation, double extras, MethodePaiement methodePaiement) {
        this.id = new SimpleIntegerProperty(id);
        this.reservation = new SimpleObjectProperty<>(reservation);
        this.fraisChambre = new SimpleDoubleProperty(reservation.getMontantTotal());
        this.taxes = new SimpleDoubleProperty(reservation.getMontantTotal() * 0.10); // 10% taxe
        this.extras = new SimpleDoubleProperty(extras);
        this.montantTotal = new SimpleDoubleProperty(calculerTotal());
        this.methodePaiement = new SimpleObjectProperty<>(methodePaiement);
        this.estPayee = new SimpleBooleanProperty(false);
        this.dateEmission = new SimpleObjectProperty<>(LocalDate.now());
    }
    
    private double calculerTotal() {
        return fraisChambre.get() + taxes.get() + extras.get();
    }
    
    // ID
    public int getId() {
        return id.get();
    }
    
    public IntegerProperty idProperty() {
        return id;
    }
    
    // Réservation
    public Reservation getReservation() {
        return reservation.get();
    }
    
    public ObjectProperty<Reservation> reservationProperty() {
        return reservation;
    }
    
    // Frais Chambre
    public double getFraisChambre() {
        return fraisChambre.get();
    }
    
    public DoubleProperty fraisChambreProperty() {
        return fraisChambre;
    }
    
    // Taxes
    public double getTaxes() {
        return taxes.get();
    }
    
    public DoubleProperty taxesProperty() {
        return taxes;
    }
    
    // Extras
    public double getExtras() {
        return extras.get();
    }
    
    public void setExtras(double valeur) {
        extras.set(valeur);
        montantTotal.set(calculerTotal());
    }
    
    public DoubleProperty extrasProperty() {
        return extras;
    }
    
    // Montant Total
    public double getMontantTotal() {
        return montantTotal.get();
    }
    
    public DoubleProperty montantTotalProperty() {
        return montantTotal;
    }
    
    // Méthode de Paiement
    public MethodePaiement getMethodePaiement() {
        return methodePaiement.get();
    }
    
    public void setMethodePaiement(MethodePaiement valeur) {
        methodePaiement.set(valeur);
    }
    
    public ObjectProperty<MethodePaiement> methodePaiementProperty() {
        return methodePaiement;
    }
    
    // Est Payée
    public boolean getEstPayee() {
        return estPayee.get();
    }
    
    public void setEstPayee(boolean valeur) {
        estPayee.set(valeur);
    }
    
    public BooleanProperty estPayeeProperty() {
        return estPayee;
    }
    
    // Date d'Émission
    public LocalDate getDateEmission() {
        return dateEmission.get();
    }
    
    public ObjectProperty<LocalDate> dateEmissionProperty() {
        return dateEmission;
    }
}
