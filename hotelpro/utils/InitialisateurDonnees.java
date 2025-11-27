package hotelpro.utils;

import hotelpro.models.*;
import java.sql.SQLException;
import java.time.LocalDate;

public class InitialisateurDonnees {
    
    public static void insererDonneesDemo() {
        try {
            // Vérifier si des données existent déjà
            if (!ClientDAO.obtenirTous().isEmpty()) {
                System.out.println("✓ Des données existent déjà dans la base");
                return;
            }
            
            System.out.println("Insertion des données de démonstration...");
            
            // Ajouter des chambres
            ChambreDAO.ajouter(new Chambre(101, TypeChambre.SIMPLE, StatutChambre.DISPONIBLE));
            ChambreDAO.ajouter(new Chambre(102, TypeChambre.SIMPLE, StatutChambre.OCCUPEE));
            ChambreDAO.ajouter(new Chambre(103, TypeChambre.SIMPLE, StatutChambre.DISPONIBLE));
            ChambreDAO.ajouter(new Chambre(201, TypeChambre.DOUBLE, StatutChambre.DISPONIBLE));
            ChambreDAO.ajouter(new Chambre(202, TypeChambre.DOUBLE, StatutChambre.OCCUPEE));
            ChambreDAO.ajouter(new Chambre(203, TypeChambre.DOUBLE, StatutChambre.MAINTENANCE));
            ChambreDAO.ajouter(new Chambre(301, TypeChambre.SUITE, StatutChambre.DISPONIBLE));
            ChambreDAO.ajouter(new Chambre(302, TypeChambre.SUITE, StatutChambre.OCCUPEE));
            ChambreDAO.ajouter(new Chambre(303, TypeChambre.SUITE, StatutChambre.DISPONIBLE));
            ChambreDAO.ajouter(new Chambre(104, TypeChambre.SIMPLE, StatutChambre.DISPONIBLE));
            ChambreDAO.ajouter(new Chambre(204, TypeChambre.DOUBLE, StatutChambre.DISPONIBLE));
            ChambreDAO.ajouter(new Chambre(304, TypeChambre.SUITE, StatutChambre.MAINTENANCE));
            System.out.println("✓ 12 chambres ajoutées");
            
            // Ajouter des clients
            int client1Id = ClientDAO.ajouter(new Client(0, "Jean", "Dupont", "jean.dupont@email.com", "0612345678", "12 Rue de Paris, 75001 Paris"));
            int client2Id = ClientDAO.ajouter(new Client(0, "Marie", "Martin", "marie.martin@email.com", "0623456789", "45 Avenue des Champs, 69001 Lyon"));
            int client3Id = ClientDAO.ajouter(new Client(0, "Pierre", "Bernard", "pierre.bernard@email.com", "0634567890", "8 Boulevard Victor Hugo, 33000 Bordeaux"));
            int client4Id = ClientDAO.ajouter(new Client(0, "Sophie", "Dubois", "sophie.dubois@email.com", "0645678901", "23 Rue de la République, 13001 Marseille"));
            int client5Id = ClientDAO.ajouter(new Client(0, "Lucas", "Thomas", "lucas.thomas@email.com", "0656789012", "67 Rue Nationale, 59000 Lille"));
            int client6Id = ClientDAO.ajouter(new Client(0, "Emma", "Robert", "emma.robert@email.com", "0667890123", "91 Avenue Jean Jaurès, 31000 Toulouse"));
            int client7Id = ClientDAO.ajouter(new Client(0, "Antoine", "Richard", "antoine.richard@email.com", "0678901234", "34 Rue du Commerce, 44000 Nantes"));
            int client8Id = ClientDAO.ajouter(new Client(0, "Camille", "Petit", "camille.petit@email.com", "0689012345", "56 Boulevard Clemenceau, 06000 Nice"));
            System.out.println("✓ 8 clients ajoutés");
            
            // Récupérer les clients et chambres pour créer des réservations
            Client client1 = ClientDAO.obtenirParId(client1Id);
            Client client2 = ClientDAO.obtenirParId(client2Id);
            Client client3 = ClientDAO.obtenirParId(client3Id);
            Client client4 = ClientDAO.obtenirParId(client4Id);
            Client client5 = ClientDAO.obtenirParId(client5Id);
            Client client6 = ClientDAO.obtenirParId(client6Id);
            Client client7 = ClientDAO.obtenirParId(client7Id);
            
            Chambre ch102 = ChambreDAO.obtenirParNumero(102);
            Chambre ch202 = ChambreDAO.obtenirParNumero(202);
            Chambre ch302 = ChambreDAO.obtenirParNumero(302);
            Chambre ch101 = ChambreDAO.obtenirParNumero(101);
            Chambre ch201 = ChambreDAO.obtenirParNumero(201);
            Chambre ch301 = ChambreDAO.obtenirParNumero(301);
            Chambre ch303 = ChambreDAO.obtenirParNumero(303);
            
            // Ajouter des réservations
            int res1Id = ReservationDAO.ajouter(new Reservation(0, client1, ch102, LocalDate.now().minusDays(2), LocalDate.now().plusDays(3), StatutReservation.EN_COURS));
            int res2Id = ReservationDAO.ajouter(new Reservation(0, client2, ch202, LocalDate.now().minusDays(1), LocalDate.now().plusDays(4), StatutReservation.EN_COURS));
            int res3Id = ReservationDAO.ajouter(new Reservation(0, client3, ch302, LocalDate.now(), LocalDate.now().plusDays(2), StatutReservation.EN_COURS));
            int res4Id = ReservationDAO.ajouter(new Reservation(0, client4, ch101, LocalDate.now().plusDays(5), LocalDate.now().plusDays(8), StatutReservation.CONFIRMEE));
            int res5Id = ReservationDAO.ajouter(new Reservation(0, client5, ch201, LocalDate.now().plusDays(7), LocalDate.now().plusDays(10), StatutReservation.CONFIRMEE));
            int res6Id = ReservationDAO.ajouter(new Reservation(0, client6, ch301, LocalDate.now().minusDays(10), LocalDate.now().minusDays(5), StatutReservation.TERMINEE));
            int res7Id = ReservationDAO.ajouter(new Reservation(0, client7, ch303, LocalDate.now().plusDays(3), LocalDate.now().plusDays(6), StatutReservation.EN_ATTENTE));
            System.out.println("✓ 7 réservations ajoutées");
            
            // Ajouter des factures
            Reservation res1 = ReservationDAO.obtenirParId(res1Id);
            Reservation res2 = ReservationDAO.obtenirParId(res2Id);
            Reservation res3 = ReservationDAO.obtenirParId(res3Id);
            Reservation res6 = ReservationDAO.obtenirParId(res6Id);
            
            Facture facture1 = new Facture(0, res1, 50.0, MethodePaiement.CARTE);
            FactureDAO.ajouter(facture1);
            
            Facture facture2 = new Facture(0, res2, 75.0, MethodePaiement.CARTE);
            FactureDAO.ajouter(facture2);
            
            Facture facture3 = new Facture(0, res3, 120.0, MethodePaiement.ESPECES);
            FactureDAO.ajouter(facture3);
            
            Facture facture4 = new Facture(0, res6, 30.0, MethodePaiement.VIREMENT);
            facture4.setEstPayee(true);
            int facture4Id = FactureDAO.ajouter(facture4);
            // Mettre à jour le statut payé
            Facture f4 = FactureDAO.obtenirParId(facture4Id);
            f4.setEstPayee(true);
            FactureDAO.modifier(f4);
            
            System.out.println("✓ 4 factures ajoutées");
            System.out.println("✓ Données de démonstration insérées avec succès !");
            
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'insertion des données de démonstration:");
            e.printStackTrace();
        }
    }
}
