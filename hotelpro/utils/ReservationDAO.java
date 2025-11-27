package hotelpro.utils;

import hotelpro.models.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    
    public static int ajouter(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservations (client_id, numero_chambre, date_arrivee, date_depart, statut, montant_total) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, reservation.getClient().getId());
            pstmt.setInt(2, reservation.getChambre().getNumeroChambre());
            pstmt.setString(3, reservation.getDateArrivee() != null ? reservation.getDateArrivee().toString() : LocalDate.now().toString());
            pstmt.setString(4, reservation.getDateDepart() != null ? reservation.getDateDepart().toString() : LocalDate.now().plusDays(1).toString());
            pstmt.setString(5, reservation.getStatut().name());
            pstmt.setDouble(6, reservation.getMontantTotal());
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }
    
    public static void modifier(Reservation reservation) throws SQLException {
        String sql = "UPDATE reservations SET client_id = ?, numero_chambre = ?, date_arrivee = ?, date_depart = ?, statut = ?, montant_total = ? WHERE id = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, reservation.getClient().getId());
            pstmt.setInt(2, reservation.getChambre().getNumeroChambre());
            pstmt.setString(3, reservation.getDateArrivee().toString());
            pstmt.setString(4, reservation.getDateDepart().toString());
            pstmt.setString(5, reservation.getStatut().name());
            pstmt.setDouble(6, reservation.getMontantTotal());
            pstmt.setInt(7, reservation.getId());
            
            pstmt.executeUpdate();
        }
    }
    
    public static void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM reservations WHERE id = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
    public static List<Reservation> obtenirToutes() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Client client = ClientDAO.obtenirParId(rs.getInt("client_id"));
                Chambre chambre = ChambreDAO.obtenirParNumero(rs.getInt("numero_chambre"));
                
                if (client != null && chambre != null) {
                    Reservation reservation = new Reservation(
                        rs.getInt("id"),
                        client,
                        chambre,
                        LocalDate.parse(rs.getString("date_arrivee")),
                        LocalDate.parse(rs.getString("date_depart")),
                        StatutReservation.valueOf(rs.getString("statut"))
                    );
                    reservations.add(reservation);
                }
            }
        }
        return reservations;
    }
    
    public static Reservation obtenirParId(int id) throws SQLException {
        String sql = "SELECT * FROM reservations WHERE id = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Client client = ClientDAO.obtenirParId(rs.getInt("client_id"));
                    Chambre chambre = ChambreDAO.obtenirParNumero(rs.getInt("numero_chambre"));
                    
                    if (client != null && chambre != null) {
                        return new Reservation(
                            rs.getInt("id"),
                            client,
                            chambre,
                            LocalDate.parse(rs.getString("date_arrivee")),
                            LocalDate.parse(rs.getString("date_depart")),
                            StatutReservation.valueOf(rs.getString("statut"))
                        );
                    }
                }
            }
        }
        return null;
    }
}
