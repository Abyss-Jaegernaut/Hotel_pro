package hotelpro.models;

public enum RoleUtilisateur {
    RECEPTIONNISTE("Réceptionniste"),
    ADMINISTRATEUR("Administrateur"),
    CLIENT("Client");
    
    private final String nomAffichage;
    
    RoleUtilisateur(String nomAffichage) {
        this.nomAffichage = nomAffichage;
    }
    
    public String getNomAffichage() {
        return nomAffichage;
    }
}
