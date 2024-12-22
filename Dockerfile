# Utilisez une version stable de Node.js (alpine pour être plus léger)
FROM node:18-alpine

# Répertoire de travail dans le conteneur
WORKDIR /app

# Copier les fichiers package.json et package-lock.json
COPY package.json package-lock.json ./

# Supprimer node_modules et package-lock.json précédents si présents (évite les erreurs liées aux versions et dépendances)
RUN rm -rf node_modules package-lock.json

# Installer les dépendances avec --legacy-peer-deps
RUN npm install --legacy-peer-deps

# Copier tout le reste des fichiers du projet
COPY . .

# Exposer le port de l'application Vite
EXPOSE 5173

# Démarrer l'application Vite
CMD ["npm", "run", "dev"]
