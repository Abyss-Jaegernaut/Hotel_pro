package hotelpro.models;

import javafx.beans.property.*;

public class Chambre {
    private final IntegerProperty numeroChambre;
    private final ObjectProperty<TypeChambre> type;
    private final ObjectProperty<StatutChambre> statut;
    private final DoubleProperty prix;
    
    public Chambre(int numeroChambre, TypeChambre type, StatutChambre statut) {
        this.numeroChambre = new SimpleIntegerProperty(numeroChambre);
        this.type = new SimpleObjectProperty<>(type);
        this.statut = new SimpleObjectProperty<>(statut);
        this.prix = new SimpleDoubleProperty(type.getPrixBase());
    }
    
    // Numéro de Chambre
    public int getNumeroChambre() {
        return numeroChambre.get();
    }
    
    public void setNumeroChambre(int valeur) {
        numeroChambre.set(valeur);
    }
    
    public IntegerProperty numeroChambreProperty() {
        return numeroChambre;
    }
    
    // Type
    public TypeChambre getType() {
        return type.get();
    }
    
    public void setType(TypeChambre valeur) {
        type.set(valeur);
        setPrix(valeur.getPrixBase());
    }
    
    public ObjectProperty<TypeChambre> typeProperty() {
        return type;
    }
    
    // Statut
    public StatutChambre getStatut() {
        return statut.get();
    }
    
    public void setStatut(StatutChambre valeur) {
        statut.set(valeur);
    }
    
    public ObjectProperty<StatutChambre> statutProperty() {
        return statut;
    }
    
    // Prix
    public double getPrix() {
        return prix.get();
    }
    
    public void setPrix(double valeur) {
        prix.set(valeur);
    }
    
    public DoubleProperty prixProperty() {
        return prix;
    }
}
