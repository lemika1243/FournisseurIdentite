package model;

import helper.Constantes;
import helper.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Pin{

    int id;
    int pin;
    Timestamp dateExpiration;
    Utilisateur utilisateur;
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getPin() {
        return pin;
    }
    public void setPin(int pin) {
        this.pin = pin;
    }
    public Timestamp getDateExpiration() {
        return dateExpiration;
    }
    public void setDateExpiration(Timestamp dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Pin(int id , int pin , Timestamp dateexpiration , Utilisateur utilisateur){
        this.setId(id);
        this.setPin(pin);
        this.setDateExpiration(dateexpiration);
        this.setUtilisateur(utilisateur);
    }
    public Pin(int pin , Timestamp dateexpiration , Utilisateur utilisateur){
        this.setPin(pin);
        this.setDateExpiration(dateexpiration);
        this.setUtilisateur(utilisateur);
    }

    public void save(Connection connection)throws Exception{
        String query ="insert into pin(pin,date_expiration,id_utilisateur) values(?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, this.getPin());
            ps.setTimestamp(2, this.getDateExpiration());
            ps.setInt(3, this.getUtilisateur().getIdUtilisateur());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new Exception("erreur lors de l'insertion de pin :"+e.getMessage());
        }
    }

    public static Pin genererPin(Utilisateur utilisateur, Reference[] references){
        Timestamp currentTimes = Util.getCurrentTimestamp();
        Reference ref = Reference.getByType(references, Constantes.PIN_TYPE);
        return new Pin(Util.generateRandomPin(Constantes.PIN_LONGUEUR) , Util.addDurationToTimestamp(currentTimes, ref.getDuree()),utilisateur);
    }

   public static Pin getPinByUtilisateur(Utilisateur utilisateur, Connection connection) throws Exception {
        Pin pin = null;
        String query = "SELECT p.id_pin, p.pin, p.date_expiration, p.id_utilisateur " +
                       "FROM pin p " +
                       "WHERE p.id_utilisateur = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, utilisateur.getIdUtilisateur());  // Lier l'ID de l'utilisateur passé en paramètre

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Récupérer les informations de la table 'pin'
                    int idPin = rs.getInt("id_pin");
                    int pinValue = rs.getInt("pin");
                    Timestamp expiration = rs.getTimestamp("date_expiration");

                    // Créer l'objet Pin
                    pin = new Pin(idPin,pinValue,expiration,utilisateur);
                }
            } catch (SQLException e) {
                throw new Exception("Erreur lors de la récupération du pin pour l'utilisateur : " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new Exception("Erreur de connexion ou de préparation de la requête : " + e.getMessage());
        }

        return pin;  // Retourner l'objet Pin ou null si aucun pin trouvé
    }

    public static void delete(Connection con, Utilisateur utilisateur)throws Exception{
        String sql = "DELETE FROM pin WHERE id_utilisateur=?";
        try(PreparedStatement prst = con.prepareStatement(sql)) {
            prst.setInt(1, utilisateur.getIdUtilisateur());
            prst.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Erreur lors de la suppression des anciens pin "+e.getMessage());
        }
    }


}