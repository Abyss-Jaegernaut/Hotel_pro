package hotelpro.models;

public enum StatutReservation {
    EN_ATTENTE("En Attente", "#2196F3"),
    CONFIRMEE("Confirmée", "#4CAF50"),
    EN_COURS("En Cours", "#FF9800"),
    TERMINEE("Terminée", "#9E9E9E"),
    ANNULEE("Annulée", "#F44336");
    
    private final String nomAffichage;
    private final String couleur;
    
    StatutReservation(String nomAffichage, String couleur) {
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
