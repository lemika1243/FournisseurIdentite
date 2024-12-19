INSERT INTO etat (designation, etat) VALUES
('CREE', 0),
('VALIDE', 1),
('BLOQUE', 2);


INSERT INTO type_reference (id_type, type) VALUES
(DEFAULT, 'pin'),
(DEFAULT, 'token');


INSERT INTO reference (id_reference, duree, id_type) VALUES
(DEFAULT, 90, (SELECT id_type FROM type_reference WHERE type = 'pin')),
(DEFAULT, 900, (SELECT id_type FROM type_reference WHERE type = 'token'));
