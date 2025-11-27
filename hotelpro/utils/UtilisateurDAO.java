package hotelpro.utils;

import hotelpro.models.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {
    
    public static Utilisateur authentifier(String nomUtilisateur, String motDePasse) throws SQLException {
        String sql = "SELECT * FROM utilisateurs WHERE nom_utilisateur = ? AND mot_de_passe = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nomUtilisateur);
            pstmt.setString(2, motDePasse);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom_utilisateur"),
                        rs.getString("mot_de_passe"),
                        RoleUtilisateur.valueOf(rs.getString("role")),
                        rs.getString("nom_complet")
                    );
                }
            }
        }
        return null;
    }
    
    public static List<Utilisateur> obtenirTous() throws SQLException {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                utilisateurs.add(new Utilisateur(
                    rs.getInt("id"),
                    rs.getString("nom_utilisateur"),
                    rs.getString("mot_de_passe"),
                    RoleUtilisateur.valueOf(rs.getString("role")),
                    rs.getString("nom_complet")
                ));
            }
        }
        return utilisateurs;
    }
}
