@echo off
REM Script pour lancer le raytracer sur Windows
REM Usage: run.bat <fichier_scene>
REM Exemple: run.bat scenes/final-2.scene

if "%~1"=="" (
    echo Usage: run.bat ^<fichier_scene^>
    echo Exemple: run.bat scenes/final-2.scene
    echo.
    echo Scenes disponibles:
    dir /b scenes\*.scene 2>nul
    exit /b 1
)

echo Lancement du raytracer...
java -jar target/raytracer-1.0-SNAPSHOT.jar %1

if %ERRORLEVEL% neq 0 (
    echo.
    echo Erreur: Assurez-vous que Java est installe et que le projet est compile.
    echo Compilez avec: mvn clean package -DskipTests
)
