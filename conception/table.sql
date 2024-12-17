CREATE TABLE etat(
   id_etat SMALLINT,
   etat SMALLINT NOT NULL,
   PRIMARY KEY(id_etat)
);

CREATE TABLE type_reference(
   id_type INTEGER,
   type VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_type)
);

CREATE TABLE utilisateur(
   id_utilisateur INTEGER,
   email VARCHAR(150)  NOT NULL,
   mdp VARCHAR(255)  NOT NULL,
   tentative_max SMALLINT CHECK(tentative_max >= 0),
   id_etat SMALLINT NOT NULL,
   PRIMARY KEY(id_utilisateur),
   UNIQUE(email),
   FOREIGN KEY(id_etat) REFERENCES etat(id_etat)
);

CREATE TABLE reference(
   id_reference INTEGER,
   duree NUMERIC(10,2)   NOT NULL,
   id_type INTEGER NOT NULL,
   PRIMARY KEY(id_reference),
   FOREIGN KEY(id_type) REFERENCES type_reference(id_type)
);

CREATE TABLE pin(
   id_pin INTEGER,
   pin SMALLINT NOT NULL,
   date_expiration TIMESTAMP NOT NULL,
   id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_pin),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE token(
   id_token INTEGER,
   token TEXT,
   user_agent VARCHAR(255)  NOT NULL,
   date_expiration TIMESTAMP,
   id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_token),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE tentative(
   id_tentative INTEGER,
   date_tentative TIMESTAMP NOT NULL,
   id_utilisateur INTEGER NOT NULL,
   PRIMARY KEY(id_tentative),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE historique_etat(
   id_utilisateur INTEGER,
   id_etat SMALLINT,
   date_etat VARCHAR(50)  NOT NULL,
   PRIMARY KEY(id_utilisateur, id_etat),
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur),
   FOREIGN KEY(id_etat) REFERENCES etat(id_etat)
);
