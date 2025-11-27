package hotelpro.models;

public enum TypeChambre {
    SIMPLE("Simple", 80.0, "Chambre simple avec lit simple"),
    DOUBLE("Double", 120.0, "Chambre double avec lit double"),
    SUITE("Suite", 250.0, "Suite luxueuse avec salon");
    
    private final String nomAffichage;
    private final double prixBase;
    private final String description;
    
    TypeChambre(String nomAffichage, double prixBase, String description) {
        this.nomAffichage = nomAffichage;
        this.prixBase = prixBase;
        this.description = description;
    }
    
    public String getNomAffichage() {
        return nomAffichage;
    }
    
    public double getPrixBase() {
        return prixBase;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return nomAffichage;
    }
}
