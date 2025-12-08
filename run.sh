#!/bin/bash
# Script pour lancer le raytracer sur macOS/Linux
# Usage: ./run.sh <fichier_scene>
# Exemple: ./run.sh scenes/final-2.scene

if [ -z "$1" ]; then
    echo "Usage: ./run.sh <fichier_scene>"
    echo "Exemple: ./run.sh scenes/final-2.scene"
    echo ""
    echo "Scenes disponibles:"
    ls scenes/*.scene 2>/dev/null
    exit 1
fi

echo "Lancement du raytracer..."
java -jar target/raytracer-1.0-SNAPSHOT.jar "$1"

if [ $? -ne 0 ]; then
    echo ""
    echo "Erreur: Assurez-vous que Java est installé et que le projet est compilé."
    echo "Compilez avec: mvn clean package -DskipTests"
fi
