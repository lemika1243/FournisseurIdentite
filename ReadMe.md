pour lancer le projet:
    1-docker-compose build --no-cache
    2-docker-compose up -d

-La documentation technique de l'application se trouve dans le dossier documentation a la racine du projet
-La collection postman se trouve dans le dossier collection postman a la racine du projet

-Voir documentation swagger:
    localhost:8080/FournisseurIdentite

-On a deux configurations
    -Constantes.java:
        -Longueur du PIN
        -Nombre de tentative maximal
        -Longueur du token
    -Base de donnee:
        -Dans la table reference a joindre avec la table type_reference pour voir la correspondance:
            -On peut configurer la duree de vie de token
            -On peut configurer la duree de vie du pin
            -la colonne duree est exprime en seconde


-En cas de modification du class Constantes.java, relancer les deux commandes pour relancer le projet