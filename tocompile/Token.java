package model.authentification;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;

import model.Reference;
import model.Utilisateur;

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

    public static Token genererToken(Connection connection, Utilisateur utilisateur) throws Exception{
        Reference reference = Reference.getTokenReference(connection);
        // Generate 16 random bytes
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);

        // Encode the random bytes as a Base64 string
        String generatedToken = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        // Set token expiration to 24 hours from now
        LocalDateTime expiration = LocalDateTime.now().plusHours((long) reference.getDuree());

        // Create and return a new Token
        Token token = new Token();
        token.setToken(generatedToken);
        token.setDateExpiration(Timestamp.valueOf(expiration));
        token.setUtilisateur(utilisateur);

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

