# Script d'installation automatique des dépendances pour Hotel Manager Pro

$ErrorActionPreference = "Stop"
$projectRoot = Get-Location
$libDir = Join-Path $projectRoot "lib"
$tempDir = Join-Path $projectRoot "temp_install"

# URLs
$sqliteUrl = "https://github.com/xerial/sqlite-jdbc/releases/download/3.44.1.0/sqlite-jdbc-3.44.1.0.jar"
$javafxUrl = "https://download2.gluonhq.com/openjfx/17.0.10/openjfx-17.0.10_windows-x64_bin-sdk.zip"

Write-Host "=== Installation des dependances Hotel Manager Pro ===" -ForegroundColor Cyan

# 1. Creation du dossier lib
if (-not (Test-Path $libDir)) {
    New-Item -ItemType Directory -Path $libDir | Out-Null
    Write-Host "Dossier lib cree" -ForegroundColor Green
}

# 2. Creation du dossier temporaire
if (-not (Test-Path $tempDir)) {
    New-Item -ItemType Directory -Path $tempDir | Out-Null
}

# 3. Telechargement SQLite
$sqliteJar = Join-Path $libDir "sqlite-jdbc-3.44.1.0.jar"
if (-not (Test-Path $sqliteJar)) {
    Write-Host "Telechargement du driver SQLite..." -ForegroundColor Yellow
    try {
        Invoke-WebRequest -Uri $sqliteUrl -OutFile $sqliteJar
        Write-Host "SQLite JDBC telecharge" -ForegroundColor Green
    } catch {
        Write-Error "Echec du telechargement de SQLite."
    }
} else {
    Write-Host "Driver SQLite deja present" -ForegroundColor Green
}

# 4. Telechargement JavaFX
$javafxZip = Join-Path $tempDir "javafx.zip"
Write-Host "Telechargement de JavaFX SDK..." -ForegroundColor Yellow

try {
    Invoke-WebRequest -Uri $javafxUrl -OutFile $javafxZip
    Write-Host "JavaFX SDK telecharge" -ForegroundColor Green

    Write-Host "Extraction de JavaFX..." -ForegroundColor Yellow
    Expand-Archive -Path $javafxZip -DestinationPath $tempDir -Force
    
    # Deplacement des JARs
    $extractedLib = Get-ChildItem -Path $tempDir -Recurse -Filter "lib" | Select-Object -First 1
    if ($extractedLib) {
        $jars = Get-ChildItem -Path $extractedLib.FullName -Filter "*.jar"
        foreach ($jar in $jars) {
            $dest = Join-Path $libDir $jar.Name
            if (-not (Test-Path $dest)) {
                Move-Item -Path $jar.FullName -Destination $libDir
            }
        }
        Write-Host "Bibliotheques JavaFX installees dans lib" -ForegroundColor Green
    } else {
        Write-Error "Impossible de trouver le dossier lib dans l'archive JavaFX"
    }

} catch {
    Write-Error "Erreur lors de l'installation de JavaFX : $_"
}

# 5. Nettoyage
if (Test-Path $tempDir) {
    Remove-Item -Path $tempDir -Recurse -Force
    Write-Host "Nettoyage termine" -ForegroundColor Green
}

Write-Host ""
Write-Host "=== Installation terminee avec succes ! ===" -ForegroundColor Cyan
Write-Host "Vous pouvez maintenant lancer l'application avec lancer_app.bat" -ForegroundColor White
