# 🚀 Guide de Démarrage - Hotel Manager Pro avec SQLite

## ⚠️ Important : Première Exécution

La base de données SQLite **n'existe pas encore** car elle est créée automatiquement lors du **premier lancement** de l'application.

## 📝 Prérequis

Avant d'exécuter l'application, assurez-vous d'avoir :

1. **Java JDK 17+** installé
2. **JavaFX SDK** configuré
3. **SQLite JDBC Driver** ajouté au projet

### Ajouter le Driver SQLite JDBC

Vous devez ajouter la dépendance SQLite JDBC à votre projet :

#### Option 1 : Maven (pom.xml)
```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.44.1.0</version>
</dependency>
```

#### Option 2 : Gradle (build.gradle)
```gradle
dependencies {
    implementation 'org.xerial:sqlite-jdbc:3.44.1.0'
}
```

#### Option 3 : Téléchargement Manuel
1. Télécharger : https://github.com/xerial/sqlite-jdbc/releases
2. Télécharger `sqlite-jdbc-3.44.1.0.jar`
3. Ajouter au classpath du projet

## 🎯 Lancer l'Application

### Avec un IDE (IntelliJ IDEA, Eclipse, NetBeans)

1. **Ouvrir le projet** dans votre IDE
2. **Configurer JavaFX** :
   - Ajouter le SDK JavaFX au projet
   - Configurer les VM options :
   ```
   --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
   ```
3. **Clic droit** sur `ApplicationPrincipale.java`
4. **Run 'ApplicationPrincipale.main()'**

### En Ligne de Commande

```bash
# Depuis le répertoire "Hotel Pro"
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp ".;sqlite-jdbc-3.44.1.0.jar" hotelpro/**/*.java

java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -cp ".;sqlite-jdbc-3.44.1.0.jar" hotelpro.ApplicationPrincipale
```

## ✨ Que se Passe-t-il au Premier Lancement ?

Au démarrage, vous verrez ces logs dans la console :

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

**À ce moment**, le fichier `hotelpro.db` sera créé dans le répertoire racine du projet !

## 📂 Localisation de la Base de Données

Après le premier lancement :
```
Hotel Pro/
├── hotelpro/
├── resources/
├── hotelpro.db    👈 FICHIER CRÉÉ ICI
├── README.md
└── GUIDE_BD.md
```

## 🔍 Vérifier la Création de la BD

### 1. Via l'Explorateur de Fichiers
- Ouvrir le dossier `d:\IIT\Java\Hotel Pro`
- Chercher le fichier `hotelpro.db`

### 2. Via la Console
```bash
# Windows PowerShell
Get-ChildItem -Path "d:\IIT\Java\Hotel Pro" -Filter "*.db"

# ou CMD
dir "d:\IIT\Java\Hotel Pro\*.db"
```

### 3. Via un Outil SQLite
- Télécharger **DB Browser for SQLite** : https://sqlitebrowser.org/
- Ouvrir le fichier `hotelpro.db`
- Voir les 5 tables et leurs données

## ❌ Résolution de Problèmes

### Erreur : "No suitable driver found"
**Cause** : Le driver SQLite JDBC n'est pas dans le classpath

**Solution** :
1. Télécharger `sqlite-jdbc-3.44.1.0.jar`
2. Le placer dans le dossier du projet
3. L'ajouter au classpath lors de la compilation/exécution

### Erreur : "SQLException: unable to open database file"
**Cause** : Problème de permissions d'écriture

**Solution** :
1. Vérifier les droits d'écriture sur le dossier
2. Exécuter l'IDE/terminal en tant qu'administrateur

### Le fichier .db n'apparaît pas
**Causes possibles** :
1. L'application n'a pas été lancée jusqu'au bout
2. Une exception a été levée avant la création
3. Le fichier est créé ailleurs

**Solution** :
1. Vérifier les logs de console
2. Chercher les erreurs SQLException
3. Vérifier le répertoire de travail actuel

## 📦 Structure Minimale du Projet pour SQLite

```
Hotel Pro/
├── hotelpro/
│   ├── ApplicationPrincipale.java
│   ├── controllers/ (7 fichiers)
│   ├── models/ (10 fichiers)
│   ├── views/ (7 fichiers FXML)
│   └── utils/
│       ├── GestionnaireBD.java         👈 GÉRÉ LA CONNEXION
│       ├── InitialisateurDonnees.java  👈 INSÈRE LES DONNÉES
│       ├── UtilisateurDAO.java
│       ├── ChambreDAO.java
│       ├── ClientDAO.java
│       ├── ReservationDAO.java
│       └── FactureDAO.java
├── resources/css/
├── sqlite-jdbc-3.44.1.0.jar           👈 REQUIS !
└── hotelpro.db                         👈 CRÉÉ AU LANCEMENT
```

## 🎓 Exemple Complet : Configuration IntelliJ IDEA

1. **File → Project Structure → Libraries**
2. **+ → Java**
3. Sélectionner `sqlite-jdbc-3.44.1.0.jar`
4. **Run → Edit Configurations**
5. Ajouter dans VM options :
   ```
   --module-path "C:\path\to\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml
   ```
6. **Run 'ApplicationPrincipale'**

## 📞 Support

Si le fichier `hotelpro.db` ne se crée toujours pas :
1. Vérifier que `GestionnaireBD.java` est bien compilé
2. Vérifier que le driver SQLite est dans le classpath
3. Lancer en mode debug et mettre un breakpoint dans `GestionnaireBD.initialiserBD()`
4. Vérifier les exceptions dans la console

---

**La base de données se créera au premier lancement de l'application** ✅
