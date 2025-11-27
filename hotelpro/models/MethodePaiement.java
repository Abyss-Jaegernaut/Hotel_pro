package hotelpro.models;

public enum MethodePaiement {
    CARTE("Carte Bancaire"),
    ESPECES("Espèces"),
    VIREMENT("Virement");
    
    private final String nomAffichage;
    
    MethodePaiement(String nomAffichage) {
        this.nomAffichage = nomAffichage;
    }
    
    public String getNomAffichage() {
        return nomAffichage;
    }
    
    @Override
    public String toString() {
        return nomAffichage;
    }
}
