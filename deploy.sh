#!/bin/bash

# Définir les variables de chemin de travail dans Docker
webapps="."
applicationName="FournisseurIdentite"
temp="./temp"
src="./src"
lib="./lib"
web="./web"
bin="./bin"
webXML="$web/*.xml"
tempjava="./tempjava"
appName="FournisseurIdentite"

# Déboguer les chemins pour vérifier leur existence
echo "Vérification des répertoires et fichiers..."
echo "web: $web"
echo "lib: $lib"
echo "src: $src"
echo "temp: $temp"

# Supprimer les répertoires temp et tempjava s'ils existent
rm -rf "$temp"
rm -rf "$tempjava"

# Créer les répertoires nécessaires
mkdir -p "$temp"
mkdir -p "$tempjava"
mkdir -p "$temp/WEB-INF/lib"
mkdir -p "$temp/WEB-INF/classes"

# Vérifier les fichiers XML dans le répertoire web
if [ -d "$web" ] && [ "$(ls -A $web/*.xml)" ]; then
    cp $webXML "$temp/WEB-INF/"
else
    echo "Aucun fichier XML trouvé dans $web"
fi

# Copier les fichiers de l'application (web, lib, etc.) dans les répertoires correspondants
if [ -d "$web" ]; then
    cp -r "$web"/* "$temp/"
else
    echo "Le répertoire web est introuvable"
fi

if [ -d "$lib" ]; then
    cp -r "$lib"/* "$temp/WEB-INF/lib/"
else
    echo "Le répertoire lib est introuvable"
fi

if [ -d "$src/controller" ] && [ -d "$src/model" ] && [ -d "$src/helper" ]; then
    cp -r "$src/controller" "$tempjava"
    cp -r "$src/model" "$tempjava"
    cp -r "$src/helper" "$tempjava"
else
    echo "Les répertoires src/controller, src/model ou src/helper sont introuvables"
fi

# Compiler les fichiers Java dans tempjava
if [ -d "$tempjava" ] && [ "$(ls -A $tempjava/*.java)" ]; then
    javac -parameters -cp "$temp/WEB-INF/lib/*" -d "$bin" "$tempjava"/*.java
else
    echo "Aucun fichier Java à compiler dans $tempjava"
fi

# Copier les fichiers compilés dans WEB-INF/classes
if [ -d "$bin" ]; then
    cp -r "$bin"/* "$temp/WEB-INF/classes"
else
    echo "Aucun fichier compilé trouvé dans $bin"
fi

# Créer le fichier .war
cd "$temp" && jar -cvf "/usr/local/tomcat/webapps/$appName.war" ./*

# Message de fin
echo "Déploiement terminé."
