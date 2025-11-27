# Guide d'Architecture - Hotel Manager Pro

## 📋 Vue d'ensemble

Ce document explique en détail l'architecture technique de **Hotel Manager Pro**, une application de gestion hôtelière développée avec JavaFX et SQLite.

## 🏗️ Architecture globale

### Pattern architectural
- **MVC (Model-View-Controller)** : Séparation claire entre la logique métier, l'interface utilisateur et le contrôle
- **DAO (Data Access Object)** : Pattern pour l'accès aux données
- **Singleton** : Gestionnaire de base de données unique

### Flux de l'application
```
Utilisateur → Controller → Service/DAO → Base de données → Model → View
```

## 📁 Structure détaillée des packages

### 1. **Package `hotelpro`** - Racine
```
hotelpro/
├── ApplicationPrincipale.java    # Point d'entrée JavaFX
└── Lanceur.java                  # Lanceur alternatif
```

#### `ApplicationPrincipale.java`
- **Rôle** : Point d'entrée de l'application
- **Responsabilités** :
  - Initialiser la base de données
  - Charger la première vue (connexion)
  - Gérer les changements de scène
  - Configurer les styles CSS
- **Méthodes clés** :
  - `start()` : Démarrage JavaFX
  - `changerScene()` : Navigation entre vues
  - `stop()` : Nettoyage des ressources

### 2. **Package `controllers`** - Contrôleurs JavaFX
```
controllers/
├── ControleurConnexion.java           # Authentification
├── ControleurTableauDeBord.java       # Écran principal
├── ControleurGestionChambres.java     # Gestion des chambres
├── ControleurGestionClients.java      # Gestion des clients
├── ControleurGestionReservations.java # Réservations
├── ControleurGestionFacturation.java  # Facturation
├── ControleurRapports.java           # Rapports/statistiques
└── client/
    └── ControleurTableauDeBordClient.java # Interface client
```

#### Architecture des contrôleurs
Chaque contrôleur suit le pattern :
```java
public class ControleurXXX {
    // Composants FXML (@FXML)
    private TextField champXXX;
    private Button boutonXXX;
    
    // Initialisation
    @FXML
    public void initialize() { }
    
    // Gestionnaires d'événements
    @FXML
    private void actionXXX() { }
    
    // Logique métier
    private void traiterXXX() { }
}
```

#### Contrôleur principal : `ControleurConnexion`
- **Authentification** : Validation des identifiants via base de données
- **Rôles** : Redirection selon le type d'utilisateur
- **Accès visiteur** : Mode sans connexion pour les réservations

### 3. **Package `models`** - Modèles de données
```
models/
├── Utilisateur.java              # Utilisateurs et rôles
├── Chambre.java                  # Chambres hôtelières
├── Client.java                   # Informations clients
├── Reservation.java             # Réservations
├── Facture.java                  # Factures
└── RoleUtilisateur.java          # Énumération des rôles
```

#### Structure des modèles
```java
public class XXX {
    // Attributs privés
    private int id;
    private String nom;
    
    // Constructeurs
    public XXX() { }
    public XXX(int id, String nom) { }
    
    // Getters/Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    // Méthodes métier
    public String toString() { }
}
```

#### Modèles clés
- **Utilisateur** : Authentification et rôles (ADMIN, RECEPTIONNISTE, CLIENT)
- **Chambre** : Types, prix, statuts (DISPONIBLE, OCCUPEE, MAINTENANCE)
- **Reservation** : Dates, clients, chambres, statuts
- **Facture** : Calculs automatiques, détails des services

### 4. **Package `utils`** - Utilitaires et DAO
```
utils/
├── GestionnaireBD.java           # Connexion SQLite
├── InitialisateurDonnees.java    # Données de démo
├── UtilisateurDAO.java          # Accès utilisateurs
├── ChambreDAO.java              # Accès chambres
├── ClientDAO.java               # Accès clients
├── ReservationDAO.java          # Accès réservations
└── FactureDAO.java              # Accès factures
```

#### `GestionnaireBD.java` - Singleton
```java
public class GestionnaireBD {
    private static Connection connexion;
    
    public static void initialiserBD() { }
    public static Connection getConnexion() { }
    public static void fermerConnexion() { }
}
```

#### Pattern DAO
Chaque DAO implémente :
- **CRUD** : Create, Read, Update, Delete
- **Requêtes spécifiques** : Recherches, filtres
- **Gestion des transactions** : Commit/Rollback

Exemple :
```java
public class ChambreDAO {
    public static List<Chambre> getAllChambres() { }
    public static Chambre getChambreById(int id) { }
    public static boolean ajouterChambre(Chambre chambre) { }
    public static boolean modifierChambre(Chambre chambre) { }
    public static boolean supprimerChambre(int id) { }
    public static List<Chambre> getChambresDisponibles() { }
}
```

### 5. **Package `views`** - Interfaces FXML
```
views/
├── connexion.fxml                # Écran de connexion
├── tableau-de-bord.fxml          # Tableau de bord admin
├── gestion-chambres.fxml         # Gestion chambres
├── gestion-clients.fxml          # Gestion clients
├── gestion-reservations.fxml     # Gestion réservations
├── gestion-facturation.fxml      # Facturation
├── rapports.fxml                 # Rapports
└── client/
    └── tableau-de-bord-client.fxml # Interface client
```

#### Structure FXML
```xml
<?xml version="1.0" encoding="UTF-8"?>
<?import javafx.scene.*?>
<?import javafx.scene.control.*?>

<StackPane xmlns="http://javafx.com/javafx"
           xmlns:fx="http://javafx.com/fxml"
           fx:controller="hotelpro.controllers.ControleurXXX">
    <!-- Layout et composants -->
</StackPane>
```

#### Liaison Controller-FXML
- **fx:controller** : Associe le contrôleur à la vue
- **@FXML** : Lie les composants JavaFX aux champs du contrôleur
- **onAction** : Associe les événements aux méthodes

### 6. **Package `resources`** - Ressources
```
resources/
└── css/
    └── styles.css               # Styles de l'application
```

#### Architecture CSS
```css
/* Variables CSS */
.root {
    -primary-color: #6366F1;
    -bg-primary: #0F172A;
}

/* Composants */
.button { }
.text-field { }
.table-view { }

/* Thèmes */
.background { }
.glass-pane { }
```

## 🗄️ Architecture des données

### Base de données SQLite
```sql
-- Tables principales
CREATE TABLE utilisateurs (
    id INTEGER PRIMARY KEY,
    nom_utilisateur TEXT UNIQUE,
    mot_de_passe TEXT,
    role TEXT,
    nom_complet TEXT
);

CREATE TABLE chambres (
    id INTEGER PRIMARY KEY,
    numero INTEGER,
    type TEXT,
    prix_par_nuit DECIMAL,
    statut TEXT,
    description TEXT
);

CREATE TABLE clients (
    id INTEGER PRIMARY KEY,
    nom TEXT,
    prenom TEXT,
    email TEXT,
    telephone TEXT
);

CREATE TABLE reservations (
    id INTEGER PRIMARY KEY,
    id_client INTEGER,
    id_chambre INTEGER,
    date_arrivee DATE,
    date_depart DATE,
    statut TEXT,
    montant_total DECIMAL
);

CREATE TABLE factures (
    id INTEGER PRIMARY KEY,
    id_reservation INTEGER,
    date_facture DATE,
    montant_total DECIMAL,
    statut_paiement TEXT
);
```

### Flux de données
1. **Initialisation** : `GestionnaireBD.initialiserBD()`
2. **Données demo** : `InitialisateurDonnees.insererDonneesDemo()`
3. **Accès** : Pattern DAO via classes `*DAO.java`
4. **Transactions** : Gestion automatique des commit/rollback

## 🔄 Flux d'exécution

### Démarrage de l'application
```
main() → launch() → start() → 
initialiserBD() → insererDonneesDemo() → 
charger connexion.fxml → afficher interface
```

### Processus de connexion
```
1. Utilisateur saisit identifiants
2. ControleurConnexion.seConnecter()
3. UtilisateurDAO.authentifier()
4. Base de données → validation
5. Redirection selon rôle :
   - ADMIN → tableau-de-bord.fxml
   - RECEPTION → tableau-de-bord.fxml  
   - CLIENT → tableau-de-bord-client.fxml
```

### Cycle de vie d'une réservation
```
1. Client sélectionne dates
2. Vérification disponibilité (ChambreDAO)
3. Création réservation (ReservationDAO)
4. Mise à jour statut chambre (ChambreDAO)
5. Génération facture (FactureDAO)
6. Confirmation client
```

## 🎨 Architecture de l'interface

### Pattern de navigation
- **Scenes** : Une scène par écran principal
- **Controllers** : Un contrôleur par vue FXML
- **CSS global** : Styles unifiés via `styles.css`

### Composants réutilisables
- **Tables** : `TableView` personnalisées avec CSS
- **Formulaires** : Champs stylisés et validés
- **Dialogues** : Alertes et confirmations modernes
- **Navigation** : Menu et breadcrumbs cohérents

### Thème et design
- **Glassmorphism** : Effets de transparence
- **Colors system** : Variables CSS cohérentes
- **Responsive** : Adaptation aux tailles d'écran
- **Animations** : Transitions fluides entre écrans

## 🔧 Architecture technique

### Dépendances
```
Java 17+                    # Langage
├── JavaFX 17+             # Interface graphique
│   ├── javafx.controls    # Composants UI
│   ├── javafx.fxml        # FXML
│   └── javafx.graphics    # Graphiques
└── SQLite JDBC 3.44.1.0   # Base de données
```

### Build et déploiement
- **Compilation** : `javac` avec modules JavaFX
- **Exécution** : `java` avec classpath SQLite
- **Packaging** : Possible création de JAR exécutable
- **Dépendances** : Librairies externes dans `lib/`

### Gestion des erreurs
- **Exceptions** : Try-catch dans les contrôleurs
- **Validation** : Vérifications en amont
- **Feedback** : Messages utilisateur clairs
- **Logs** : Console pour le développement

## 🚀 Évolution et maintenance

### Points d'extension
- **Nouveaux rôles** : Ajout dans `RoleUtilisateur`
- **Nouvelles fonctionnalités** : Contrôleur + Vue + DAO
- **Rapports avancés** : Extensions dans `ControleurRapports`
- **Multi-hôtels** : Modification de l'architecture DAO

### Bonnes pratiques
- **Séparation des responsabilités** : MVC strict
- **Cohérence du code** : Style uniforme
- **Tests unitaires** : Possibilité d'ajout JUnit
- **Documentation** : Commentaires clairs

---

Cette architecture permet une maintenance aisée, des évolutions contrôlées et une compréhension rapide du système pour les développeurs.
