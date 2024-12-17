# Étape 1 : Utiliser une image de base Tomcat avec JDK
FROM tomcat:10-jdk17

# Étape 2 : Copier l'ensemble du projet dans le répertoire webapps de Tomcat
COPY . /usr/local/tomcat/webapps/FournisseurIdentite/

# Étape 3 : Copier les bibliothèques PostgreSQL dans le répertoire lib de Tomcat
COPY lib/postgresql-42.6.0.jar /usr/local/tomcat/lib/

# Étape 4 : Copier le script `deploy.sh` dans le répertoire du projet
# COPY deploy.sh /usr/local/tomcat/webapps/FournisseurIdentite/

# Étape 5 : Définir le répertoire de travail pour exécuter deploy.sh
WORKDIR /usr/local/tomcat/webapps/FournisseurIdentite/

# Étape 6 : Donner les permissions d'exécution au script `deploy.sh`
RUN chmod +x /usr/local/tomcat/webapps/FournisseurIdentite/deploy.sh

# Étape 7 : Exposer le port 8080 pour Tomcat
EXPOSE 8080

# Étape 8 : Exécuter le script deploy.sh pour générer le .war puis démarrer Tomcat
CMD ["sh", "/usr/local/tomcat/webapps/FournisseurIdentite/deploy.sh", "&&", "/usr/local/tomcat/bin/catalina.sh", "run"]
