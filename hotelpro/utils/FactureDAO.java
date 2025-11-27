package hotelpro.utils;

import hotelpro.models.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FactureDAO {
    
    public static int ajouter(Facture facture) throws SQLException {
        String sql = "INSERT INTO factures (reservation_id, frais_chambre, taxes, extras, montant_total, methode_paiement, est_payee, date_emission) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, facture.getReservation().getId());
            pstmt.setDouble(2, facture.getFraisChambre());
            pstmt.setDouble(3, facture.getTaxes());
            pstmt.setDouble(4, facture.getExtras());
            pstmt.setDouble(5, facture.getMontantTotal());
            pstmt.setString(6, facture.getMethodePaiement().name());
            pstmt.setInt(7, facture.getEstPayee() ? 1 : 0);
            pstmt.setString(8, facture.getDateEmission().toString());
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }
    
    public static void modifier(Facture facture) throws SQLException {
        String sql = "UPDATE factures SET reservation_id = ?, frais_chambre = ?, taxes = ?, extras = ?, montant_total = ?, methode_paiement = ?, est_payee = ?, date_emission = ? WHERE id = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, facture.getReservation().getId());
            pstmt.setDouble(2, facture.getFraisChambre());
            pstmt.setDouble(3, facture.getTaxes());
            pstmt.setDouble(4, facture.getExtras());
            pstmt.setDouble(5, facture.getMontantTotal());
            pstmt.setString(6, facture.getMethodePaiement().name());
            pstmt.setInt(7, facture.getEstPayee() ? 1 : 0);
            pstmt.setString(8, facture.getDateEmission().toString());
            pstmt.setInt(9, facture.getId());
            
            pstmt.executeUpdate();
        }
    }
    
    public static void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM factures WHERE id = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
    public static List<Facture> obtenirToutes() throws SQLException {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM factures";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Reservation reservation = ReservationDAO.obtenirParId(rs.getInt("reservation_id"));
                
                if (reservation != null) {
                    Facture facture = new Facture(
                        rs.getInt("id"),
                        reservation,
                        rs.getDouble("extras"),
                        MethodePaiement.valueOf(rs.getString("methode_paiement"))
                    );
                    facture.setEstPayee(rs.getInt("est_payee") == 1);
                    factures.add(facture);
                }
            }
        }
        return factures;
    }
    
    public static Facture obtenirParId(int id) throws SQLException {
        String sql = "SELECT * FROM factures WHERE id = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Reservation reservation = ReservationDAO.obtenirParId(rs.getInt("reservation_id"));
                    
                    if (reservation != null) {
                        Facture facture = new Facture(
                            rs.getInt("id"),
                            reservation,
                            rs.getDouble("extras"),
                            MethodePaiement.valueOf(rs.getString("methode_paiement"))
                        );
                        facture.setEstPayee(rs.getInt("est_payee") == 1);
                        return facture;
                    }
                }
            }
        }
        return null;
    }
}
