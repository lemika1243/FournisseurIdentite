INSERT INTO type_reference (id_type, type)
VALUES (1, 'PIN');
INSERT INTO reference (id_reference, duree, id_type)
VALUES (1, 90, (SELECT id_type FROM type_reference WHERE type = 'PIN'));

INSERT INTO type_reference (id_type, type)
VALUES (2, 'Token');
INSERT INTO reference (id_reference, duree, id_type)
VALUES (2, 24, (SELECT id_type FROM type_reference WHERE type = 'Token'));
