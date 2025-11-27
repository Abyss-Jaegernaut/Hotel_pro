package hotelpro.models;

import javafx.beans.property.*;

public class Client {
    private final IntegerProperty id;
    private final StringProperty prenom;
    private final StringProperty nom;
    private final StringProperty email;
    private final StringProperty telephone;
    private final StringProperty adresse;
    
    public Client(int id, String prenom, String nom, String email, String telephone, String adresse) {
        this.id = new SimpleIntegerProperty(id);
        this.prenom = new SimpleStringProperty(prenom);
        this.nom = new SimpleStringProperty(nom);
        this.email = new SimpleStringProperty(email);
        this.telephone = new SimpleStringProperty(telephone);
        this.adresse = new SimpleStringProperty(adresse);
    }
    
    // ID
    public int getId() {
        return id.get();
    }
    
    public void setId(int valeur) {
        id.set(valeur);
    }
    
    public IntegerProperty idProperty() {
        return id;
    }
    
    // Prénom
    public String getPrenom() {
        return prenom.get();
    }
    
    public void setPrenom(String valeur) {
        prenom.set(valeur);
    }
    
    public StringProperty prenomProperty() {
        return prenom;
    }
    
    // Nom
    public String getNom() {
        return nom.get();
    }
    
    public void setNom(String valeur) {
        nom.set(valeur);
    }
    
    public StringProperty nomProperty() {
        return nom;
    }
    
    // Email
    public String getEmail() {
        return email.get();
    }
    
    public void setEmail(String valeur) {
        email.set(valeur);
    }
    
    public StringProperty emailProperty() {
        return email;
    }
    
    // Téléphone
    public String getTelephone() {
        return telephone.get();
    }
    
    public void setTelephone(String valeur) {
        telephone.set(valeur);
    }
    
    public StringProperty telephoneProperty() {
        return telephone;
    }
    
    // Adresse
    public String getAdresse() {
        return adresse.get();
    }
    
    public void setAdresse(String valeur) {
        adresse.set(valeur);
    }
    
    public StringProperty adresseProperty() {
        return adresse;
    }
    
    public String getNomComplet() {
        return prenom.get() + " " + nom.get();
    }
}
