package hotelpro.models;

public enum StatutChambre {
    DISPONIBLE("Disponible", "#4CAF50"),
    OCCUPEE("Occupée", "#F44336"),
    MAINTENANCE("Maintenance", "#FF9800");
    
    private final String nomAffichage;
    private final String couleur;
    
    StatutChambre(String nomAffichage, String couleur) {
        this.nomAffichage = nomAffichage;
        this.couleur = couleur;
    }
    
    public String getNomAffichage() {
        return nomAffichage;
    }
    
    public String getCouleur() {
        return couleur;
    }
    
    @Override
    public String toString() {
        return nomAffichage;
    }
}
