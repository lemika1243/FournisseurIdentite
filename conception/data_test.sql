CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO etat (id_etat, etat) VALUES 
(1, 0), -- Inactive state
(2, 1), -- Active state
(3, 2), -- Suspended state
(4, 3), -- Pending state
(5, 4); -- Deleted state

INSERT INTO type_reference (id_type, type)
VALUES (1, 'PIN');
INSERT INTO reference (id_reference, duree, id_type)
VALUES (1, 90, (SELECT id_type FROM type_reference WHERE type = 'PIN'));

INSERT INTO type_reference (id_type, type)
VALUES (2, 'Token');
INSERT INTO reference (id_reference, duree, id_type)
VALUES (2, 24, (SELECT id_type FROM type_reference WHERE type = 'Token'));


INSERT INTO utilisateur (id_utilisateur, email, mdp, tentative_max, id_etat)
VALUES 
(1, 'andriamahefazafyfinoana@gmail.com', encode(digest('Tsiory24', 'sha1'), 'hex'), 5, 1);
INSERT INTO utilisateur (id_utilisateur, email, mdp, tentative_max, id_etat)
VALUES 
(2, 'ramsmikajy@gmail.com', encode(digest('rams', 'sha1'), 'hex'), 5, 1);
