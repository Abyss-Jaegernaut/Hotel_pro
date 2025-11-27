package hotelpro.utils;

import hotelpro.models.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    
    public static int ajouter(Client client) throws SQLException {
        String sql = "INSERT INTO clients (prenom, nom, email, telephone, adresse) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, client.getPrenom());
            pstmt.setString(2, client.getNom());
            pstmt.setString(3, client.getEmail());
            pstmt.setString(4, client.getTelephone());
            pstmt.setString(5, client.getAdresse());
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }
    
    public static void modifier(Client client) throws SQLException {
        String sql = "UPDATE clients SET prenom = ?, nom = ?, email = ?, telephone = ?, adresse = ? WHERE id = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, client.getPrenom());
            pstmt.setString(2, client.getNom());
            pstmt.setString(3, client.getEmail());
            pstmt.setString(4, client.getTelephone());
            pstmt.setString(5, client.getAdresse());
            pstmt.setInt(6, client.getId());
            
            pstmt.executeUpdate();
        }
    }
    
    public static void supprimer(int id) throws SQLException {
        String sql = "DELETE FROM clients WHERE id = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
    
    public static List<Client> obtenirTous() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clients.add(new Client(
                    rs.getInt("id"),
                    rs.getString("prenom"),
                    rs.getString("nom"),
                    rs.getString("email"),
                    rs.getString("telephone"),
                    rs.getString("adresse")
                ));
            }
        }
        return clients;
    }
    
    public static Client obtenirParId(int id) throws SQLException {
        String sql = "SELECT * FROM clients WHERE id = ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                        rs.getInt("id"),
                        rs.getString("prenom"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("adresse")
                    );
                }
            }
        }
        return null;
    }
    
    public static List<Client> rechercher(String recherche) throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients WHERE prenom LIKE ? OR nom LIKE ? OR email LIKE ? OR telephone LIKE ?";
        
        try (Connection conn = GestionnaireBD.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String pattern = "%" + recherche + "%";
            pstmt.setString(1, pattern);
            pstmt.setString(2, pattern);
            pstmt.setString(3, pattern);
            pstmt.setString(4, pattern);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    clients.add(new Client(
                        rs.getInt("id"),
                        rs.getString("prenom"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("adresse")
                    ));
                }
            }
        }
        return clients;
    }
}
