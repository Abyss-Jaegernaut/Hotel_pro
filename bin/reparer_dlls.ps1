# Script de réparation des DLLs JavaFX
$ErrorActionPreference = "Stop"
$projectRoot = Get-Location
$libDir = Join-Path $projectRoot "lib"
$tempDir = Join-Path $projectRoot "temp_fix"
$javafxUrl = "https://download2.gluonhq.com/openjfx/17.0.10/openjfx-17.0.10_windows-x64_bin-sdk.zip"

Write-Host "=== Reparation des fichiers manquants (DLLs) ===" -ForegroundColor Cyan

if (-not (Test-Path $tempDir)) {
    New-Item -ItemType Directory -Path $tempDir | Out-Null
}

$javafxZip = Join-Path $tempDir "javafx.zip"
Write-Host "Telechargement de JavaFX SDK..." -ForegroundColor Yellow
try {
    Invoke-WebRequest -Uri $javafxUrl -OutFile $javafxZip
    Write-Host "JavaFX SDK telecharge" -ForegroundColor Green

    Write-Host "Extraction..." -ForegroundColor Yellow
    Expand-Archive -Path $javafxZip -DestinationPath $tempDir -Force
    
    # Copie des DLLs depuis le dossier bin
    $extractedBin = Get-ChildItem -Path $tempDir -Recurse -Filter "bin" | Select-Object -First 1
    if ($extractedBin) {
        $dlls = Get-ChildItem -Path $extractedBin.FullName -Filter "*.dll"
        foreach ($dll in $dlls) {
            $dest = Join-Path $libDir $dll.Name
            Copy-Item -Path $dll.FullName -Destination $libDir -Force
            Write-Host "Copie de $($dll.Name)" -ForegroundColor Gray
        }
        Write-Host "Tous les fichiers DLL ont ete copies dans lib" -ForegroundColor Green
    } else {
        Write-Error "Dossier bin introuvable"
    }

} catch {
    Write-Error "Erreur : $_"
}

# Nettoyage
if (Test-Path $tempDir) {
    Remove-Item -Path $tempDir -Recurse -Force
}

Write-Host "Reparation terminee." -ForegroundColor Cyan
