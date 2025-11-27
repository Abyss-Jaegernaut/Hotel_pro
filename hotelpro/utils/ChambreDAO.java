package hotelpro.utils;

import hotelpro.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChambreDAO {
    
    public static void ajouter(Chambre chambre) throws SQLException {
        String sql = "INSERT INTO chambres (numero_chambre, type, statut, prix) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, chambre.getNumeroChambre());
            pstmt.setString(2, chambre.getType().name());
            pstmt.setString(3, chambre.getStatut().name());
            pstmt.setDouble(4, chambre.getPrix());
            
            pstmt.executeUpdate();
        }
    }
    
    public static void modifier(Chambre chambre) throws SQLException {
        String sql = "UPDATE chambres SET type = ?, statut = ?, prix = ? WHERE numero_chambre = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, chambre.getType().name());
            pstmt.setString(2, chambre.getStatut().name());
            pstmt.setDouble(3, chambre.getPrix());
            pstmt.setInt(4, chambre.getNumeroChambre());
            
            pstmt.executeUpdate();
        }
    }
    
    public static void supprimer(int numeroChambre) throws SQLException {
        String sql = "DELETE FROM chambres WHERE numero_chambre = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, numeroChambre);
            pstmt.executeUpdate();
        }
    }
    
    public static List<Chambre> obtenirToutes() throws SQLException {
        List<Chambre> chambres = new ArrayList<>();
        String sql = "SELECT * FROM chambres";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Chambre chambre = new Chambre(
                    rs.getInt("numero_chambre"),
                    TypeChambre.valueOf(rs.getString("type")),
                    StatutChambre.valueOf(rs.getString("statut"))
                );
                chambre.setPrix(rs.getDouble("prix"));
                chambres.add(chambre);
            }
        }
        return chambres;
    }
    
    public static Chambre obtenirParNumero(int numero) throws SQLException {
        String sql = "SELECT * FROM chambres WHERE numero_chambre = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, numero);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Chambre chambre = new Chambre(
                        rs.getInt("numero_chambre"),
                        TypeChambre.valueOf(rs.getString("type")),
                        StatutChambre.valueOf(rs.getString("statut"))
                    );
                    chambre.setPrix(rs.getDouble("prix"));
                    return chambre;
                }
            }
        }
        return null;
    }
}
