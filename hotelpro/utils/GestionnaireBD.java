package hotelpro.utils;

import java.sql.*;

public class GestionnaireBD {
    
    private static final String URL_BD = "jdbc:sqlite:hotelpro.db";
    private static Connection connexion = null;
    
    // Obtenir la connexion singleton
    public static Connection obtenirConnexion() throws SQLException {
        if (connexion == null || connexion.isClosed()) {
            try {
                Class.forName("org.sqlite.JDBC");
                connexion = DriverManager.getConnection(URL_BD);
                System.out.println("✓ Connexion à la base de données établie");
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver SQLite non trouvé", e);
            }
        }
        return connexion;
    }
    
    // Initialiser la base de données (créer les tables)
    public static void initialiserBD() throws SQLException {
        Connection conn = obtenirConnexion();
        
        // Table Utilisateurs
        String sqlUtilisateurs = """
            CREATE TABLE IF NOT EXISTS utilisateurs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nom_utilisateur TEXT NOT NULL UNIQUE,
                mot_de_passe TEXT NOT NULL,
                role TEXT NOT NULL,
                nom_complet TEXT NOT NULL
            )
        """;
        
        // Table Clients
        String sqlClients = """
            CREATE TABLE IF NOT EXISTS clients (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                prenom TEXT NOT NULL,
                nom TEXT NOT NULL,
                email TEXT,
                telephone TEXT,
                adresse TEXT
            )
        """;
        
        // Table Chambres
        String sqlChambres = """
            CREATE TABLE IF NOT EXISTS chambres (
                numero_chambre INTEGER PRIMARY KEY,
                type TEXT NOT NULL,
                statut TEXT NOT NULL,
                prix REAL NOT NULL
            )
        """;
        
        // Table Réservations
        String sqlReservations = """
            CREATE TABLE IF NOT EXISTS reservations (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                client_id INTEGER NOT NULL,
                numero_chambre INTEGER NOT NULL,
                date_arrivee TEXT NOT NULL,
                date_depart TEXT NOT NULL,
                statut TEXT NOT NULL,
                montant_total REAL NOT NULL,
                FOREIGN KEY (client_id) REFERENCES clients(id),
                FOREIGN KEY (numero_chambre) REFERENCES chambres(numero_chambre)
            )
        """;
        
        // Table Factures
        String sqlFactures = """
            CREATE TABLE IF NOT EXISTS factures (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                reservation_id INTEGER NOT NULL,
                frais_chambre REAL NOT NULL,
                taxes REAL NOT NULL,
                extras REAL NOT NULL,
                montant_total REAL NOT NULL,
                methode_paiement TEXT NOT NULL,
                est_payee INTEGER NOT NULL,
                date_emission TEXT NOT NULL,
                FOREIGN KEY (reservation_id) REFERENCES reservations(id)
            )
        """;
        
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUtilisateurs);
            stmt.execute(sqlClients);
            stmt.execute(sqlChambres);
            stmt.execute(sqlReservations);
            stmt.execute(sqlFactures);
            System.out.println("✓ Tables créées avec succès");
        }
        
        // Insérer les utilisateurs par défaut si la table est vide
        insererUtilisateursParDefaut();
    }
    
    private static void insererUtilisateursParDefaut() throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM utilisateurs";
        try (Statement stmt = obtenirConnexion().createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {
            if (rs.next() && rs.getInt(1) == 0) {
                String insertSql = """
                    INSERT INTO utilisateurs (nom_utilisateur, mot_de_passe, role, nom_complet) VALUES
                    ('admin', 'admin123', 'ADMINISTRATEUR', 'Administrateur Principal'),
                    ('reception', 'recep123', 'RECEPTIONNISTE', 'Marie Réception')
                """;
                try (Statement insertStmt = obtenirConnexion().createStatement()) {
                    insertStmt.execute(insertSql);
                    System.out.println("✓ Utilisateurs par défaut créés");
                }
            }
        }
    }
    
    // Fermer la connexion
    public static void fermerConnexion() {
        try {
            if (connexion != null && !connexion.isClosed()) {
                connexion.close();
                System.out.println("✓ Connexion fermée");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
