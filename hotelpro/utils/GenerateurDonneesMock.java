package hotelpro.utils;

import hotelpro.models.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GenerateurDonneesMock {
    
    private static List<Chambre> chambres;
    private static List<Client> clients;
    private static List<Reservation> reservations;
    private static List<Facture> factures;
    private static List<Utilisateur> utilisateurs;
    
    static {
        initialiserDonnees();
    }
    
    private static void initialiserDonnees() {
        // Initialiser les Chambres
        chambres = new ArrayList<>();
        chambres.add(new Chambre(101, TypeChambre.SIMPLE, StatutChambre.DISPONIBLE));
        chambres.add(new Chambre(102, TypeChambre.SIMPLE, StatutChambre.OCCUPEE));
        chambres.add(new Chambre(103, TypeChambre.SIMPLE, StatutChambre.DISPONIBLE));
        chambres.add(new Chambre(201, TypeChambre.DOUBLE, StatutChambre.DISPONIBLE));
        chambres.add(new Chambre(202, TypeChambre.DOUBLE, StatutChambre.OCCUPEE));
        chambres.add(new Chambre(203, TypeChambre.DOUBLE, StatutChambre.MAINTENANCE));
        chambres.add(new Chambre(301, TypeChambre.SUITE, StatutChambre.DISPONIBLE));
        chambres.add(new Chambre(302, TypeChambre.SUITE, StatutChambre.OCCUPEE));
        chambres.add(new Chambre(303, TypeChambre.SUITE, StatutChambre.DISPONIBLE));
        chambres.add(new Chambre(104, TypeChambre.SIMPLE, StatutChambre.DISPONIBLE));
        chambres.add(new Chambre(204, TypeChambre.DOUBLE, StatutChambre.DISPONIBLE));
        chambres.add(new Chambre(304, TypeChambre.SUITE, StatutChambre.MAINTENANCE));
        
        // Initialiser les Clients
        clients = new ArrayList<>();
        clients.add(new Client(1, "Jean", "Dupont", "jean.dupont@email.com", "0612345678", "12 Rue de Paris, 75001 Paris"));
        clients.add(new Client(2, "Marie", "Martin", "marie.martin@email.com", "0623456789", "45 Avenue des Champs, 69001 Lyon"));
        clients.add(new Client(3, "Pierre", "Bernard", "pierre.bernard@email.com", "0634567890", "8 Boulevard Victor Hugo, 33000 Bordeaux"));
        clients.add(new Client(4, "Sophie", "Dubois", "sophie.dubois@email.com", "0645678901", "23 Rue de la République, 13001 Marseille"));
        clients.add(new Client(5, "Lucas", "Thomas", "lucas.thomas@email.com", "0656789012", "67 Rue Nationale, 59000 Lille"));
        clients.add(new Client(6, "Emma", "Robert", "emma.robert@email.com", "0667890123", "91 Avenue Jean Jaurès, 31000 Toulouse"));
        clients.add(new Client(7, "Antoine", "Richard", "antoine.richard@email.com", "0678901234", "34 Rue du Commerce, 44000 Nantes"));
        clients.add(new Client(8, "Camille", "Petit", "camille.petit@email.com", "0689012345", "56 Boulevard Clemenceau, 06000 Nice"));
        
        // Initialiser les Utilisateurs
        utilisateurs = new ArrayList<>();
        utilisateurs.add(new Utilisateur(1, "admin", "admin123", RoleUtilisateur.ADMINISTRATEUR, "Administrateur Principal"));
        utilisateurs.add(new Utilisateur(2, "reception", "recep123", RoleUtilisateur.RECEPTIONNISTE, "Marie Réception"));
        
        // Initialiser les Réservations
        reservations = new ArrayList<>();
        reservations.add(new Reservation(1, clients.get(0), chambres.get(1), 
            LocalDate.now().minusDays(2), LocalDate.now().plusDays(3), StatutReservation.EN_COURS));
        reservations.add(new Reservation(2, clients.get(1), chambres.get(4), 
            LocalDate.now().minusDays(1), LocalDate.now().plusDays(4), StatutReservation.EN_COURS));
        reservations.add(new Reservation(3, clients.get(2), chambres.get(7), 
            LocalDate.now(), LocalDate.now().plusDays(2), StatutReservation.EN_COURS));
        reservations.add(new Reservation(4, clients.get(3), chambres.get(0), 
            LocalDate.now().plusDays(5), LocalDate.now().plusDays(8), StatutReservation.CONFIRMEE));
        reservations.add(new Reservation(5, clients.get(4), chambres.get(3), 
            LocalDate.now().plusDays(7), LocalDate.now().plusDays(10), StatutReservation.CONFIRMEE));
        reservations.add(new Reservation(6, clients.get(5), chambres.get(6), 
            LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), StatutReservation.TERMINEE));
        reservations.add(new Reservation(7, clients.get(6), chambres.get(8), 
            LocalDate.now().plusDays(3), LocalDate.now().plusDays(6), StatutReservation.EN_ATTENTE));
        
        // Initialiser les Factures
        factures = new ArrayList<>();
        factures.add(new Facture(1, reservations.get(0), 50.0, MethodePaiement.CARTE));
        factures.add(new Facture(2, reservations.get(1), 75.0, MethodePaiement.CARTE));
        factures.add(new Facture(3, reservations.get(2), 120.0, MethodePaiement.ESPECES));
        factures.add(new Facture(4, reservations.get(5), 30.0, MethodePaiement.VIREMENT));
        factures.get(3).setEstPayee(true);
    }
    
    public static List<Chambre> getChambres() {
        return new ArrayList<>(chambres);
    }
    
    public static List<Client> getClients() {
        return new ArrayList<>(clients);
    }
    
    public static List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }
    
    public static List<Facture> getFactures() {
        return new ArrayList<>(factures);
    }
    
    public static List<Utilisateur> getUtilisateurs() {
        return new ArrayList<>(utilisateurs);
    }
    
    public static void ajouterChambre(Chambre chambre) {
        chambres.add(chambre);
    }
    
    public static void ajouterClient(Client client) {
        clients.add(client);
    }
    
    public static void ajouterReservation(Reservation reservation) {
        reservations.add(reservation);
    }
    
    public static void ajouterFacture(Facture facture) {
        factures.add(facture);
    }
    
    public static Utilisateur authentifierUtilisateur(String nomUtilisateur, String motDePasse) {
        for (Utilisateur utilisateur : utilisateurs) {
            if (utilisateur.getNomUtilisateur().equals(nomUtilisateur) && utilisateur.getMotDePasse().equals(motDePasse)) {
                return utilisateur;
            }
        }
        return null;
    }
}
