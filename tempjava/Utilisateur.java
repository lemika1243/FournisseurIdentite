package model;

import helper.Constantes;
import helper.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.Socket;
import java.util.regex.*;

public class Utilisateur {
    private int idUtilisateur;
    private String email;
    private String mdp;
    private int tentativeMax;
    private Etat etat;

    public int getIdUtilisateur() {
        return idUtilisateur;
    }
    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMdp() {
        return mdp;
    }
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
    public int getTentativeMax() {
        return tentativeMax;
    }
    public void setTentativeMax(int tentativeMax) {
        this.tentativeMax = tentativeMax;
    }
    public Etat getEtat() {
        return etat;
    }
    public void setEtat(Etat etat) {
        this.etat = etat;
    }


    public Utilisateur(int idUtilisateur, String email, String mdp, int tentativeMax, Etat etat){
        this.setIdUtilisateur(idUtilisateur);
        this.setEmail(email);
        this.setMdp(mdp);
        this.setTentativeMax(tentativeMax);
        this.setEtat(etat);
    }

    public void save(Connection connection) throws Exception {
        String sql = "INSERT INTO utilisateur (email, mdp, tentative_max, id_etat) VALUES (?, ?, ?, ?) RETURNING id_utilisateur";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, this.email);
            statement.setString(2, this.mdp);
            statement.setInt(3, this.tentativeMax);
            statement.setInt(4, this.etat.getIdEtat());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.idUtilisateur = resultSet.getInt("id_utilisateur");
                }
            }
        }catch(Exception e){
            throw new Exception("Erreur lors de l'insertion de l'utilisateur "+e.getMessage());
        }
    }

    public void save()throws Exception{
        Connection con = Connexion.dbConnect();
        this.save(con);
        con.close();
    }

    public void validee(Connection con) throws SQLException {
        Etat valideEtat = Etat.getEtatByUniqueEtat(con, Constantes.VALIDE);

        String updateUserQuery = "UPDATE utilisateur SET id_etat = ? WHERE id_utilisateur = ?";
        try (PreparedStatement pstmt = con.prepareStatement(updateUserQuery)) {
            pstmt.setInt(1, valideEtat.getIdEtat());
            pstmt.setInt(2, this.getIdUtilisateur());
            pstmt.executeUpdate();
        }
    }
    public void bloque(Connection con) throws SQLException {
        Etat valideEtat = Etat.getEtatByUniqueEtat(con, Constantes.BLOQUE);

        String updateUserQuery = "UPDATE utilisateur SET id_etat = ? WHERE id_utilisateur = ?";
        try (PreparedStatement pstmt = con.prepareStatement(updateUserQuery)) {
            pstmt.setInt(1, valideEtat.getIdEtat());
            pstmt.setInt(2, this.getIdUtilisateur());
            pstmt.executeUpdate();
        }
    }
    
    public static Utilisateur getUtilisateurByEmail(String email , Connection connection) throws Exception{
        Utilisateur u = null;
        String query = "select * from utilisateur where email = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    u = new Utilisateur(rs.getInt("id_utilisateur"), email, rs.getString("mdp"), rs.getInt("tentative_max"), Etat.getById(connection,rs.getInt("id_etat")));
                }
            } 
        } catch (Exception e) {
            throw new Exception("erreur lors de getUtilisateurByEmail :"+e.getMessage());
        }
        return u;
    }

    public static Utilisateur getById(int idUtilisateur , Connection connection) throws Exception{
        Utilisateur u = null;
        String query = "select * from utilisateur where id_utilisateur = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, idUtilisateur);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    u = new Utilisateur(idUtilisateur, rs.getString("email"), rs.getString("mdp"), rs.getInt("tentative_max"), Etat.getById(connection,rs.getInt("id_etat")));
                }
            } 
        } catch (Exception e) {
            throw new Exception("erreur lors de getUtilisateurByEmail :"+e.getMessage());
        }
        return u;
    }
    
    public void update(Connection con)throws Exception{
        Utilisateur utilisateur = getById(this.getIdUtilisateur(), con);
        if(!this.getEmail().equals(utilisateur.getEmail())){
            throw new Exception("Erreur, l'email ne doit pas etre modifie !");
        }
        String sql = "UPDATE utilisateur SET mdp=? WHERE id_utilisateur=?";
        try {
            try(PreparedStatement prst = con.prepareStatement(sql)){
                prst.setString(1, this.getMdp());
                prst.setInt(2, this.getIdUtilisateur());
            }
        } catch (Exception e) {
            throw new Exception("Erreur lors de la mis a jour de l'utilisateur "+e.getMessage());
        }
    }

    
    public static boolean sendEmail(String sender, String pwdSender, String receiver, String content) throws Exception {
        String host = "smtp.gmail.com"; 
        int port = 587; 

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(sender, pwdSender);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
            message.setSubject("Inscription"); // Objet de l'e-mail
            message.setText(content); // Contenu de l'e-mail

            Transport.send(message);

            System.out.println("E-mail envoyé avec succès !");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Erreur lors de l'envoie de l'email "+e.getMessage());
        }
    }
    
    public boolean verifyEmail() {
        String gmailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        
        Pattern pattern = Pattern.compile(gmailPattern);
        Matcher matcher = pattern.matcher(this.email);

        return matcher.matches();
    }
}
