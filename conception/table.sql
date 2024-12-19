CREATE TABLE etat (
   id_etat SMALLINT DEFAULT nextval('etat_id_etat_seq') PRIMARY KEY,
   designation VARCHAR(50) ,
   etat SMALLINT NOT NULL UNIQUE
);

CREATE TABLE type_reference (
   id_type INTEGER DEFAULT nextval('type_reference_id_type_seq') PRIMARY KEY,
   type VARCHAR(50) NOT NULL
);

CREATE TABLE utilisateur (
   id_utilisateur INTEGER DEFAULT nextval('utilisateur_id_utilisateur_seq') PRIMARY KEY,
   email VARCHAR(150) NOT NULL UNIQUE,
   mdp VARCHAR(255) NOT NULL,
   tentative_max SMALLINT CHECK (tentative_max >= 0),
   id_etat SMALLINT NOT NULL REFERENCES etat(id_etat)
);

CREATE TABLE reference (
   id_reference INTEGER DEFAULT nextval('reference_id_reference_seq') PRIMARY KEY,
   duree NUMERIC(10, 2) NOT NULL,
   id_type INTEGER NOT NULL REFERENCES type_reference(id_type)
);

CREATE TABLE pin (
   id_pin INTEGER DEFAULT nextval('pin_id_pin_seq') PRIMARY KEY,
   pin SMALLINT NOT NULL,
   date_expiration TIMESTAMP NOT NULL,
   id_utilisateur INTEGER NOT NULL REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE token (
   id_token INTEGER DEFAULT nextval('token_id_token_seq') PRIMARY KEY,
   token TEXT,
   user_agent VARCHAR(255) NOT NULL,
   date_expiration TIMESTAMP,
   id_utilisateur INTEGER NOT NULL REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE tentative (
   id_tentative INTEGER DEFAULT nextval('tentative_id_tentative_seq') PRIMARY KEY,
   date_tentative TIMESTAMP NOT NULL,
   id_utilisateur INTEGER NOT NULL REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE validation_inscription (
   id_validation INTEGER DEFAULT nextval('validation_inscription_id_validation_seq') PRIMARY KEY,
   token TEXT NOT NULL,
   id_utilisateur INTEGER NOT NULL REFERENCES utilisateur(id_utilisateur)
);

CREATE TABLE historique_etat (
   id_historique_etat INTEGER DEFAULT nextval('historique_etat_id_seq') PRIMARY KEY,
   id_utilisateur INTEGER,
   id_etat SMALLINT,
   date_etat VARCHAR(50) NOT NULL,
   FOREIGN KEY(id_utilisateur) REFERENCES utilisateur(id_utilisateur),
   FOREIGN KEY(id_etat) REFERENCES etat(id_etat)
);
