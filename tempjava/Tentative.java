package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Tentative {
    private int id_tentative;
    private Timestamp date_tentative;
    private Utilisateur utilisateur;
    public int getId_tentative() {
        return id_tentative;
    }
    public void setId_tentative(int id_tentative) {
        this.id_tentative = id_tentative;
    }
    public Timestamp getDate_tentative() {
        return date_tentative;
    }
    public void setDate_tentative(Timestamp date_tentative) {
        this.date_tentative = date_tentative;
    }
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Tentative(int id , Timestamp date_tentative , Utilisateur u){
        this.setId_tentative(id);
        this.setDate_tentative(date_tentative);
        this.setUtilisateur(u);
    }

    public Tentative(Timestamp date_tentative , Utilisateur u){
        this.setDate_tentative(date_tentative);
        this.setUtilisateur(u);
    }

    public void save(Connection connection) throws Exception{
        String query = "insert into tentative(date_tentative,id_utilisateur) values(?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)){
           ps.setTimestamp(1, this.getDate_tentative());
           ps.setInt(2, this.getUtilisateur().getIdUtilisateur());
           ps.executeUpdate();
        } catch (Exception e) {
            throw new Exception("erreur lors de l'insertion de Tentative :"+e.getMessage());
        }
    }

    public static Tentative[] getAllByUtilisateur(Utilisateur utilisateur, Connection connection)throws Exception{
        String query = "select * from tentative where id_utilisateur = ?";
        List<Tentative> tentatives = new ArrayList<>();
         try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, utilisateur.getIdUtilisateur());
            try (ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Tentative tentative = new Tentative(rs.getInt("id_tentative"), rs.getTimestamp("date_tentative"), utilisateur);
                    tentatives.add(tentative);
                }
            } 
        } catch (Exception e) {
            throw new Exception("erreur lors de getUtilisateurByEmail :"+e.getMessage());
        }
        return tentatives.toArray(new Tentative[0]);
    }

    public static void delete(Connection connection , int idUtilisateur)throws Exception{
        String query = "delete from tentative where id_utilisateur = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, idUtilisateur);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new Exception("erreur lors de delete tentative :"+e.getMessage());
        }
    }
}
