# 🛠️ Guide de Résolution des Erreurs - Hotel Manager Pro

Il semble que votre environnement de développement ne soit pas correctement configuré pour **JavaFX** et **SQLite**, ce qui cause de nombreuses erreurs ("l'appli est rempli d'erreur").

Voici les étapes pour corriger ces problèmes :

## 1. Télécharger les Dépendances Manquantes

### A. SQLite JDBC Driver
Le fichier `sqlite-jdbc-3.44.1.0.jar` est manquant.
1. Téléchargez-le ici : [https://github.com/xerial/sqlite-jdbc/releases](https://github.com/xerial/sqlite-jdbc/releases)
2. Placez le fichier `.jar` dans le dossier racine du projet (`d:\IIT\Java\Hotel Pro`).

### B. JavaFX SDK
Si vous voyez des erreurs sur `javafx.*`, vous devez configurer JavaFX.
1. Téléchargez le SDK JavaFX pour Windows : [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
2. Extrayez-le.
3. Copiez **tous les fichiers .jar** du dossier `lib` du SDK vers le dossier `lib` de votre projet (`d:\IIT\Java\Hotel Pro\lib`).

## 2. Configuration Automatique

J'ai configuré le projet pour qu'il détecte automatiquement les bibliothèques dans le dossier `lib`.

Une fois que vous avez copié :
- `sqlite-jdbc-3.44.1.0.jar`
- Les `.jar` de JavaFX (javafx.controls.jar, javafx.fxml.jar, etc.)

Dans le dossier `lib`, les erreurs devraient disparaître dans VS Code et vous pourrez lancer l'application.

## 3. Exécuter avec le Script de Correction

J'ai créé un fichier `lancer_app.bat` à la racine.
1. Ouvrez ce fichier avec un éditeur de texte.
2. Modifiez la ligne `set JAVAFX_PATH=...` pour pointer vers votre dossier JavaFX.
3. Double-cliquez sur `lancer_app.bat` pour compiler et lancer l'application.

## 4. Corrections de Code Effectuées

J'ai déjà corrigé certains bugs dans le code :
- ✅ **ReservationDAO.java** : Protection contre les plantages si une date est manquante.
- ✅ **ControleurTableauDeBord.java** : Correction d'un bug de division par zéro si aucune chambre n'existe.

---
Si vous avez encore des problèmes, assurez-vous que vous utilisez **Java 17** ou plus récent.
