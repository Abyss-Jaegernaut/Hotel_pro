@echo off
setlocal

echo === Lancement de Hotel Manager Pro ===

REM Chemins
set MODULE_PATH=lib_modules
set LIB_PATH=lib
set SQLITE_JAR=lib\sqlite-jdbc-3.44.1.0.jar

REM Lancement
java --module-path %MODULE_PATH% --add-modules javafx.controls,javafx.fxml -Djava.library.path=%LIB_PATH% --enable-native-access=ALL-UNNAMED -cp ".;%SQLITE_JAR%;%LIB_PATH%\slf4j-api-2.0.9.jar;%LIB_PATH%\slf4j-simple-2.0.9.jar" hotelpro.Lanceur

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERREUR] L'application a rencontre une erreur.
    pause
    exit /b 1
)


