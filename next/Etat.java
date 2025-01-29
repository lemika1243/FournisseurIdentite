package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Etat{
    private int idEtat;
    private int etat;

    public int getIdEtat() {
        return idEtat;
    }
    public void setIdEtat(int idEtat) {
        this.idEtat = idEtat;
    }
    public int getEtat() {
        return etat;
    }
    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Etat(int idEtat, int etat){
        this.setIdEtat(idEtat);
        this.setEtat(etat);
    }

    public Etat(int etat){
        this.setEtat(etat);
    }

    // Méthode statique pour récupérer un état par son ID
    public static Etat getById(Connection connection, int idEtat) {
        Etat etat = null;

        String query = "SELECT * FROM etat WHERE id_etat = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idEtat);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int dbIdEtat = rs.getInt("id_etat");
                int dbEtat = rs.getInt("etat");

                etat = new Etat(dbIdEtat, dbEtat);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return etat;
    }
}