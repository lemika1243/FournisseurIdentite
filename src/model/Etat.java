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

    public static Etat getEtatByUniqueEtat(Connection connection, int uniqueEtat) throws SQLException {
        String sql = "SELECT id_etat, etat FROM etat WHERE etat = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, uniqueEtat);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int idEtat = resultSet.getInt("id_etat");
                    int etatValue = resultSet.getInt("etat");
                    return new Etat(idEtat, etatValue);
                } else {
                    return null;
                }
            }
        }
    }

    public static Etat getById(Connection con, int idEtat) throws Exception {
        String query = "SELECT id_etat, etat FROM etat WHERE id_etat = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, idEtat);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Etat(
                        rs.getInt("id_etat"),
                        rs.getInt("etat")
                    );
                } else {
                    throw new Exception("Aucun état trouvé avec l'ID : " + idEtat);
                }
            }
        }
    }
}