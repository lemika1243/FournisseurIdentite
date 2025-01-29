package model;

import helper.Constantes;
import helper.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Token {
    private int idToken;
    private String token;
    private String userAgent;
    private Timestamp dateExpiration;
    private Utilisateur utilisateur;

    // Constructor
    public Token(int idToken, String token, String userAgent, Timestamp dateExpiration, Utilisateur utilisateur) {
        this.idToken = idToken;
        this.token = token;
        this.userAgent = userAgent;
        this.dateExpiration = dateExpiration;
        this.utilisateur = utilisateur;
    }

    // Default Constructor
    public Token() {}

    // Getters and Setters
    public int getIdToken() {
        return idToken;
    }

    public void setIdToken(int idToken) {
        this.idToken = idToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Timestamp getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Timestamp dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setToken(Connection connection , String token) throws Exception{
        String query = "update token set token = ? where id_token = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setString(1, token);
            ps.setInt(2, this.getIdToken());
            ps.executeUpdate(); 
        } catch (Exception e) {
            throw new Exception("erreur lors de setToken :"+e.getMessage());
        }
    }

    // toString Method
    @Override
    public String toString() {
        return "Token{" +
                "idToken:" + idToken +
                ", token:'" + token + '\'' +
                ", userAgent:'" + userAgent + '\'' +
                ", dateExpiration:" + dateExpiration +
                ", utilisateur:" + utilisateur +
                '}';
    }

    public static Token genererToken(Connection connection, Utilisateur utilisateur ,String userAgent) throws Exception{
        Reference reference = Reference.getTokenReference(connection);
        // Generate 16 random bytes
       
        String generatedToken = Util.generateRandomString(Constantes.TOKEN_LONGUEUR);

        // Set token expiration to 24 hours from now
        LocalDateTime expiration = LocalDateTime.now().plusHours((long) reference.getDuree());

        // Create and return a new Token
        Token token = new Token();
        token.setUserAgent(userAgent);
        token.setToken(generatedToken);
        token.setDateExpiration(Timestamp.valueOf(expiration));
        token.setUtilisateur(utilisateur);

        return token;
    }

    public static Token getTokenByUtilisateur(Connection connection , Utilisateur utilisateur , String userAgent)throws Exception{
        Token token = null;
        String query = "select * from token where id_utilisateur = ? and user_agent = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)){
            ps.setInt(1, utilisateur.getIdUtilisateur());ps.setString(2,userAgent);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    token = new Token(rs.getInt("id_token"),rs.getString("token"),rs.getString("user_agent"),rs.getTimestamp("date_expiration"),utilisateur);
                }
            } 
        } catch (Exception e) {
            throw new Exception("erreur lors de getTokenByUtilisateur :"+e.getMessage());
        }
        return token;
    }

    public void insert(Connection connection) throws Exception {
        String sql = "INSERT INTO token (token, user_agent, date_expiration, id_utilisateur) " +
                     "VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, this.token);
        stmt.setString(2, this.userAgent);
        stmt.setTimestamp(3, this.dateExpiration);
        stmt.setInt(4, this.utilisateur.getIdUtilisateur()); // Assuming Utilisateur class has getIdUtilisateur() method

        // Execute the insertion
        stmt.executeUpdate();
    }
}

