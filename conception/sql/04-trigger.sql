CREATE OR REPLACE FUNCTION update_historique_etat()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO historique_etat (id_utilisateur, id_etat, date_etat)
        VALUES (NEW.id_utilisateur, NEW.id_etat, to_char(CURRENT_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS'));
        RETURN NEW;
    END IF;

    IF (TG_OP = 'UPDATE') THEN
        IF (OLD.id_etat IS DISTINCT FROM NEW.id_etat) THEN
            INSERT INTO historique_etat (id_utilisateur, id_etat, date_etat)
            VALUES (NEW.id_utilisateur, NEW.id_etat, to_char(CURRENT_TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS'));
        END IF;
        RETURN NEW;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER trigger_historique_etat
AFTER INSERT OR UPDATE OF id_etat
ON utilisateur
FOR EACH ROW
EXECUTE FUNCTION update_historique_etat();
