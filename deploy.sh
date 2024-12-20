#!/bin/bash

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

rm -f "../../tomcat/webapps/$appName.war"
rm -rf "$temp"
rm -rf "$tempjava"

mkdir -p "$temp"
mkdir -p "$tempjava"
mkdir -p "$temp/WEB-INF/lib"
mkdir -p "$temp/WEB-INF/classes"

if [ -d "$webXML" ] && [ "$(ls -A $webXML/*.xml)" ]; then
    cp $webXML "$temp/WEB-INF/"
else
    echo "Aucun fichier XML trouvé dans $web"
fi

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
    cp -r "$src"/controller/* "$tempjava"
    cp -r "$src"/model/* "$tempjava"
    cp -r "$src"/helper/* "$tempjava"
else
    echo "Les répertoires src/controller, src/model ou src/helper sont introuvables"
fi
echo "Contenu de tempjava :"
ls -l "$tempjava"

if [ -d "$tempjava" ] && [ "$(ls -A $tempjava/*.java)" ]; then
    javac -parameters -cp "$temp/WEB-INF/lib/*" -d "$bin" "$tempjava"/*.java
else
    echo "Aucun fichier Java à compiler dans $tempjava"
fi

if [ -d "$bin" ]; then
    cp -r "$bin"/* "$temp/WEB-INF/classes"
else
    echo "Aucun fichier compilé trouvé dans $bin"
fi

cd "$temp" && jar -cvf "../$appName.war" *
cd ..
cp -r $appName.war "/usr/local/tomcat/webapps/"

echo "Déploiement terminé."
