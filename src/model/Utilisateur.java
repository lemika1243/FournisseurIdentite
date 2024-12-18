package model;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.regex.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean sendEmail(String sender, String pwdSender, String receiver, String content) throws Exception{
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
        } catch (MessagingException e) {
            throw new Exception("Erreur lors de l'envoie "+e.getMessage());
            // return false;
        }
    }
    
    public boolean verifyEmail() {
        String gmailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
        
        Pattern pattern = Pattern.compile(gmailPattern);
        Matcher matcher = pattern.matcher(this.email);

        return matcher.matches();
    }

    public void insert(Connection connection) {
        String query = "INSERT INTO utilisateur (id_utilisateur, email, mdp, tentative_max, id_etat) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.idUtilisateur);
            stmt.setString(2, this.email);
            stmt.setString(3, hashPassword(this.mdp));
            stmt.setInt(4, this.tentativeMax);
            stmt.setInt(5, this.etat.getIdEtat());  

            int rowsAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Utilisateur getByEmailAndPassword(Connection connection, String email, String password) throws Exception{
        Utilisateur utilisateur = null;

        String query = "SELECT * FROM utilisateur WHERE email = ? AND mdp = ?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, hashPassword(password));

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            int idUtilisateur = rs.getInt("id_utilisateur");
            String dbEmail = rs.getString("email");
            String dbMdp = rs.getString("mdp");
            int tentativeMax = rs.getInt("tentative_max");
            Etat etat = Etat.getById(connection, rs.getInt("id_etat"));

            utilisateur = new Utilisateur(idUtilisateur, dbEmail, dbMdp, tentativeMax, etat);
        }

        return utilisateur;
    }
}
