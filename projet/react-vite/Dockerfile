# Étape 1 : Construire l'application React
# Utilise l'image Node officielle version 22 comme base pour construire l'application
# Le mot-clé 'AS builder' permet de nommer cette étape de construction
FROM node:22 AS builder

# Définit le répertoire de travail dans le conteneur comme '/app'
# Toutes les commandes suivantes seront exécutées dans ce dossier
WORKDIR /app

# Copie les fichiers de gestion des dépendances dans le conteneur
# Cela permet d'optimiser la mise en cache des couches Docker et de n'installer 
# les dépendances que si ces fichiers ont changé
COPY package.json package-lock.json ./

# Installe toutes les dépendances du projet définies dans package.json
# L'option 'RUN' exécute une commande pendant la construction de l'image
RUN npm install

# Copie tous les fichiers et dossiers du projet dans le conteneur
# Cela inclut le code source, les fichiers de configuration, etc.
COPY . ./

# Construit l'application React pour la production
# Génère les fichiers statiques dans le dossier 'dist'
RUN npm run build

# Étape 2 : Utiliser Nginx pour servir l'application
# Commence une nouvelle étape avec une image Nginx légère basée sur Alpine Linux
FROM nginx:alpine

# Copie les fichiers construits (dossier 'dist') de l'étape précédente 
# dans le répertoire de service web de Nginx
# L'option --from=builder permet de récupérer les fichiers de l'étape de construction
COPY --from=builder /app/dist /usr/share/nginx/html

# Copie une configuration Nginx personnalisée 
# Remplace la configuration par défaut pour personnaliser le serveur web
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Indique que le conteneur écoutera sur le port 80 
# Cela documente le port qui sera utilisé, mais ne l'ouvre pas automatiquement
EXPOSE 80

# Commande finale qui sera exécutée au démarrage du conteneur
# Lance Nginx en mode foreground pour que le conteneur reste actif
CMD ["nginx", "-g", "daemon off;"]
