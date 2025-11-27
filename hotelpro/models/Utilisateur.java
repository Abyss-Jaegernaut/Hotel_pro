package hotelpro.models;

public class Utilisateur {
    private int id;
    private String nomUtilisateur;
    private String motDePasse;
    private RoleUtilisateur role;
    private String nomComplet;
    
    public Utilisateur(int id, String nomUtilisateur, String motDePasse, RoleUtilisateur role, String nomComplet) {
        this.id = id;
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
        this.role = role;
        this.nomComplet = nomComplet;
    }
    
    // Getters et Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNomUtilisateur() {
        return nomUtilisateur;
    }
    
    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }
    
    public String getMotDePasse() {
        return motDePasse;
    }
    
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
    
    public RoleUtilisateur getRole() {
        return role;
    }
    
    public void setRole(RoleUtilisateur role) {
        this.role = role;
    }
    
    public String getNomComplet() {
        return nomComplet;
    }
    
    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }
}
