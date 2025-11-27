@echo off
setlocal

REM ==========================================
REM CONFIGURATION
REM Modifiez ces chemins selon votre installation
REM ==========================================

REM Chemin vers le dossier lib local (recommandé)
set LIB_PATH=lib

REM Si vous n'utilisez pas le dossier lib local, modifiez ces chemins :
set JAVAFX_PATH=%LIB_PATH%
set SQLITE_JAR=%LIB_PATH%\sqlite-jdbc-3.44.1.0.jar

REM ==========================================

echo === Compilation de Hotel Manager Pro ===

if not exist "%JAVAFX_PATH%" (
    echo [ERREUR] Le dossier JavaFX n'a pas ete trouve : %JAVAFX_PATH%
    echo Veuillez modifier ce fichier lancer_app.bat pour definir le bon chemin.
    pause
    exit /b 1
)

if not exist "%SQLITE_JAR%" (
    echo [ERREUR] Le fichier %SQLITE_JAR% n'a pas ete trouve.
    echo Veuillez le telecharger et le placer dans ce dossier.
    pause
    exit /b 1
)

javac --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -cp ".;%SQLITE_JAR%" -d . hotelpro/models/*.java hotelpro/utils/*.java hotelpro/controllers/*.java hotelpro/ApplicationPrincipale.java hotelpro/Lanceur.java

if %ERRORLEVEL% NEQ 0 (
    echo [ERREUR] La compilation a echoue.
    pause
    exit /b 1
)

echo.
echo === Lancement de l'application ===
echo.

java --module-path "%JAVAFX_PATH%" --add-modules javafx.controls,javafx.fxml -Djava.library.path="%LIB_PATH%" --enable-native-access=ALL-UNNAMED -cp ".;%SQLITE_JAR%" hotelpro.Lanceur

pause
