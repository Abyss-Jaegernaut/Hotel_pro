# 🏨 HOTEL MANAGER PRO - Application JavaFX

Application de gestion hôtelière complète développée en JavaFX avec une interface utilisateur moderne et élégante.

## 📋 Description

Hotel Manager Pro est une application de gestion hôtelière qui permet de gérer efficacement :
- Les chambres et leur disponibilité
- Les clients et leur historique
- Les réservations avec calcul automatique des prix
- La facturation avec gestion des paiements
- Les rapports et statistiques de performance

## 🎯 Fonctionnalités

### 1. Authentification
- Connexion avec rôles (Réceptionniste / Administrateur)
- Comptes de démonstration fournis

### 2. Tableau de Bord
- Statistiques en temps réel
- Taux d'occupation
- Nombre de réservations actives
- Navigation vers tous les modules

### 3. Gestion des Chambres
- CRUD complet sur les chambres
- Types : Simple, Double, Suite
- Statuts : Disponible, Occupée, Maintenance
- Prix personnalisables

### 4. Gestion des Clients
- Enregistrement complet des informations client
- Fonction de recherche (nom, email, téléphone)
- Historique des séjours

### 5. Gestion des Réservations
- Création et modification de réservations
- Sélection client et chambre
- Dates d'arrivée et départ
- Calcul automatique du montant total
- Gestion des statuts (En Attente, Confirmée, En Cours, Terminée, Annulée)

### 6. Facturation
- Génération de factures
- Calcul automatique : Frais chambre + Taxes (10%) + Extras
- Modes de paiement : Carte, Espèces, Virement
- Suivi du statut de paiement

### 7. Rapports et Statistiques
- Graphiques (Pie Chart & Bar Chart)
- Indicateurs de performance
- Revenus par type de chambre
- Export PDF/CSV (mock)

## 🎨 Design

L'application utilise un design moderne avec :
- **Glassmorphism** : Effets de verre translucide
- **Palette de couleurs** : Tons bleus et violets élégants
- **Animations** : Transitions fluides et effets hover
- **Typographie** : Police Inter de Google Fonts
- **Mode sombre** : Interface sombre pour réduire la fatigue oculaire

## 📂 Structure du Projet

```
Hotel Pro/
├── hotelpro/
│   ├── ApplicationPrincipale.java (Point d'entrée)
│   ├── controllers/
│   │   ├── ControleurConnexion.java
│   │   ├── ControleurTableauDeBord.java
│   │   ├── ControleurGestionChambres.java
│   │   ├── ControleurGestionClients.java
│   │   ├── ControleurGestionReservations.java
│   │   ├── ControleurGestionFacturation.java
│   │   └── ControleurRapports.java
│   ├── models/
│   │   ├── Chambre.java
│   │   ├── TypeChambre.java
│   │   ├── StatutChambre.java
│   │   ├── Client.java
│   │   ├── Reservation.java
│   │   ├── StatutReservation.java
│   │   ├── Facture.java
│   │   ├── MethodePaiement.java
│   │   ├── Utilisateur.java
│   │   └── RoleUtilisateur.java
│   ├── views/ (Fichiers FXML)
│   │   ├── connexion.fxml
│   │   ├── tableau-de-bord.fxml
│   │   ├── gestion-chambres.fxml
│   │   ├── gestion-clients.fxml
│   │   ├── gestion-reservations.fxml
│   │   ├── gestion-facturation.fxml
│   │   └── rapports.fxml
│   └── utils/
│       └── GenerateurDonneesMock.java
└── resources/
    └── css/
        └── styles.css
```

## 🔐 Comptes de Démonstration

### Administrateur
- **Nom d'utilisateur** : `admin`
- **Mot de passe** : `admin123`

### Réceptionniste
- **Nom d'utilisateur** : `reception`
- **Mot de passe** : `recep123`

## 🚀 Installation et Exécution

### Prérequis
- Java JDK 17 ou supérieur
- JavaFX SDK 17 ou supérieur

### Compilation et Exécution

1. **Si vous utilisez un IDE (IntelliJ IDEA, Eclipse, NetBeans)** :
   - Ouvrir le projet
   - Configurer JavaFX dans le projet
   - Exécuter `ApplicationPrincipale.java`

2. **En ligne de commande** :
```bash
# Compiler
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml hotelpro/**/*.java

# Exécuter
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml hotelpro.ApplicationPrincipale
```

## 📊 Données Mockées

L'application utilise un générateur de données mockées (`GenerateurDonneesMock.java`) qui crée :
- 12 chambres (Simple, Double, Suite)
- 8 clients avec informations complètes
- 7 réservations avec différents statuts
- 4 factures avec des paiements
- 2 utilisateurs (admin et réceptionniste)

## 🎯 Conformité au Cahier des Charges

L'application répond à toutes les exigences fonctionnelles :
- ✅ Gestion des chambres et de l'inventaire
- ✅ Gestion des clients
- ✅ Gestion du cycle de réservation
- ✅ Gestion de la facturation
- ✅ Reporting et tableaux de bord

## 🔧 Technologies Utilisées

- **Java** : Langage de programmation
- **JavaFX** : Framework UI
- **FXML** : Balisage des interfaces
- **CSS** : Stylisation
- **Properties Pattern** : Pour le binding de données

## 📝 Notes Importantes

- Cette version est une **interface utilisateur uniquement** (pas de backend)
- Les données sont stockées en mémoire (non persistantes)
- Les exports PDF/CSV sont des fonctionnalités de démonstration
- L'application est conçue pour faciliter l'ajout futur d'un backend

## 🎨 Captures d'Écran

L'application propose une interface moderne avec :
- Écran de connexion élégant avec glassmorphism
- Tableau de bord avec statistiques animées
- Tables de données avec tri et filtrage
- Formulaires intuitifs avec validation
- Graphiques interactifs pour les rapports

## 📄 Licence

Projet éducatif - Hotel Manager Pro

---

**Développé avec ❤️ en JavaFX**
