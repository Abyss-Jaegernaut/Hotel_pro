# 🗄️ Guide de la Base de Données SQLite - Hotel Manager Pro

## Vue d'Ensemble

L'application utilise désormais **SQLite** pour la persistance des données, remplaçant le système de données mockées.

## 📦 Structure de la Base de Données

### Fichier de Base de Données
- **Nom** : `hotelpro.db`
- **Emplacement** : Racine du projet
- **Type** : SQLite 3

### Tables Créées

#### 1. `utilisateurs`
```sql
- id: INTEGER PRIMARY KEY AUTOINCREMENT
- nom_utilisateur: TEXT NOT NULL UNIQUE
- mot_de_passe: TEXT NOT NULL
- role: TEXT NOT NULL (ADMINISTRATEUR | RECEPTIONNISTE)
- nom_complet: TEXT NOT NULL
```

#### 2. `clients`
```sql
- id: INTEGER PRIMARY KEY AUTOINCREMENT
- prenom: TEXT NOT NULL
- nom: TEXT NOT NULL
- email: TEXT
- telephone: TEXT
- adresse: TEXT
```

#### 3. `chambres`
```sql
- numero_chambre: INTEGER PRIMARY KEY
- type: TEXT NOT NULL (SIMPLE | DOUBLE | SUITE)
- statut: TEXT NOT NULL (DISPONIBLE | OCCUPEE | MAINTENANCE)
- prix: REAL NOT NULL
```

#### 4. `reservations`
```sql
- id: INTEGER PRIMARY KEY AUTOINCREMENT
- client_id: INTEGER NOT NULL → clients(id)
- numero_chambre: INTEGER NOT NULL → chambres(numero_chambre)
- date_arrivee: TEXT NOT NULL
- date_depart: TEXT NOT NULL
- statut: TEXT NOT NULL
- montant_total: REAL NOT NULL
```

#### 5. `factures`
```sql
- id: INTEGER PRIMARY KEY AUTOINCREMENT
- reservation_id: INTEGER NOT NULL → reservations(id)
- frais_chambre: REAL NOT NULL
- taxes: REAL NOT NULL
- extras: REAL NOT NULL
- montant_total: REAL NOT NULL
- methode_paiement: TEXT NOT NULL (CARTE | ESPECES | VIREMENT)
- est_payee: INTEGER NOT NULL (0 | 1)
- date_emission: TEXT NOT NULL
```

## 🔧 Classes Utilitaires

### 1. `GestionnaireBD.java`
**Responsabilités** :
- Gestion de la connexion SQLite (singleton)
- Création automatique des tables
- Insertion des utilisateurs par défaut
- Fermeture propre de la connexion

**Méthodes principales** :
- `obtenirConnexion()` : Obtenir la connexion BD
- `initialiserBD()` : Créer les tables si nécessaires
- `fermerConnexion()` : Fermer la connexion

### 2. Classes DAO (Data Access Objects)

#### `UtilisateurDAO.java`
- `authentifier(String, String)` : Authentification
- `obtenirTous()` : Liste des utilisateurs

#### `ChambreDAO.java`
- `ajouter(Chambre)` : Ajouter une chambre
- `modifier(Chambre)` : Modifier une chambre
- `supprimer(int)` : Supprimer par numéro
- `obtenirToutes()` : Liste de toutes les chambres
- `obtenirParNumero(int)` : Récupérer une chambre

#### `ClientDAO.java`
- `ajouter(Client)` : Ajouter (retourne l'ID)
- `modifier(Client)` : Modifier
- `supprimer(int)` : Supprimer par ID
- `obtenirTous()` : Liste de tous les clients
- `obtenirParId(int)` : Récupérer un client
- `rechercher(String)` : Recherche multi-critères

#### `ReservationDAO.java`
- `ajouter(Reservation)` : Ajouter (retourne l'ID)
- `modifier(Reservation)` : Modifier
- `supprimer(int)` : Supprimer par ID
- `obtenirToutes()` : Liste avec clients et chambres
- `obtenirParId(int)` : Récupérer une réservation

#### `FactureDAO.java`
- `ajouter(Facture)` : Ajouter (retourne l'ID)
- `modifier(Facture)` : Modifier
- `supprimer(int)` : Supprimer par ID
- `obtenirToutes()` : Liste avec réservations complètes
- `obtenirParId(int)` : Récupérer une facture

### 3. `InitialisateurDonnees.java`
**Responsabilité** : Insérer des données de démonstration au premier lancement

**Données insérées** :
- 12 chambres (différents types et statuts)
- 8 clients avec informations complètes
- 7 réservations (passées, en cours, futures)
- 4 factures (dont 1 payée)

## ⚙️ Fonctionnement

### Au Démarrage de l'Application

1. `ApplicationPrincipale.start()` est appelée
2. `GestionnaireBD.initialiserBD()` :
   - Crée la connexion SQLite
   - Crée les 5 tables si elles n'existent pas
   - Insère 2 utilisateurs par défaut (admin, reception)
3. `InitialisateurDonnees.insererDonneesDemo()` :
   - Vérifie si des données existent
   - Si vide, insère les données de démonstration
4. L'application est prête à l'emploi

### À la Fermeture

1. `ApplicationPrincipale.stop()` est appelée
2. `GestionnaireBD.fermerConnexion()` ferme proprement la BD

## 🔄 Persistence des Données

**Toutes les opérations sont persistantes** :
- ✅ Ajout d'une chambre → Enregistré dans `chambres`
- ✅ Modification d'un client → Mis à jour dans `clients`
- ✅ Suppression d'une réservation → Supprimé de `reservations`
- ✅ Création de facture → Enregistré dans `factures`

**Les données survivent entre les sessions** : Le fichier `hotelpro.db` conserve tout l'historique.

## 🛠️ Gestion de la Base de Données

### Réinitialiser la BD
Pour repartir à zéro avec les données de démonstration :
1. Fermer l'application
2. Supprimer le fichier `hotelpro.db`
3. Relancer l'application (se recrée automatiquement)

### Explorer la BD Manuellement
Utiliser un outil SQLite comme :
- **DB Browser for SQLite** (gratuit, multi-plateformes)
- **SQLiteStudio** (gratuit, open-source)

Ouvrir le fichier `hotelpro.db` pour :
- Consulter les tables
- Exécuter des requêtes SQL
- Exporter des données

### Sauvegarder les Données
Simplement copier le fichier `hotelpro.db`

## 📊 Exemples de Requêtes SQL

### Consulter toutes les chambres disponibles
```sql
SELECT * FROM chambres WHERE statut = 'DISPONIBLE';
```

### Trouver les réservations en cours
```sql
SELECT r.*, c.prenom, c.nom 
FROM reservations r
JOIN clients c ON r.client_id = c.id
WHERE r.statut = 'EN_COURS';
```

### Calculer le revenu total
```sql
SELECT SUM(montant_total) as revenu_total
FROM factures
WHERE est_payee = 1;
```

### Rechercher un client par nom
```sql
SELECT * FROM clients 
WHERE nom LIKE '%Dupont%' OR prenom LIKE '%Dupont%';
```

## ⚠️ Remarques Importantes

1. **Contraintes de Clés Étrangères** : SQLite n'applique pas strictement les FK par défaut, mais les DAOs gèrent les relations
2. **Dates** : Stockées au format texte ISO (YYYY-MM-DD) pour compatibilité avec LocalDate
3. **Boolean** : SQLite n'a pas de type boolean, on utilise INTEGER (0/1)
4. **Auto-Increment** : Les IDs sont générés automatiquement pour clients, réservations, factures

## 🚀 Avantages de l'Intégration SQLite

- ✅ **Persistence** : Les données ne sont plus perdues à la fermeture
- ✅ **Performance** : Requêtes SQL optimisées
- ✅ **Fiabilité** : Base de données ACID
- ✅ **Portabilité** : Un seul fichier `.db` facile à déplacer
- ✅ **Sans serveur** : Pas besoin d'installer un SGBD externe
- ✅ **Évolutivité** : Peut gérer des milliers d'enregistrements

## 📝 Logs de Démarrage

L'application affiche des logs au démarrage :
```
=== Initialisation de Hotel Manager Pro ===
✓ Connexion à la base de données établie
✓ Tables créées avec succès
✓ Utilisateurs par défaut créés
Insertion des données de démonstration...
✓ 12 chambres ajoutées
✓ 8 clients ajoutés
✓ 7 réservations ajoutées
✓ 4 factures ajoutées
✓ Données de démonstration insérées avec succès !
===========================================
```

---

**Base de données SQLite intégrée et fonctionnelle** ✅
