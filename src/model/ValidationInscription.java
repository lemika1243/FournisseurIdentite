package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ValidationInscription {
    private int idValidationInscription;
    private String token;
    private Utilisateur utilisateur;
    
    public int getIdValidationInscription() {
        return idValidationInscription;
    }
    public void setIdValidationInscription(int idValidationInscription) {
        this.idValidationInscription = idValidationInscription;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }


    public ValidationInscription(int idValidationInscription, String token, Utilisateur utilisateur) {
        this.setIdValidationInscription(idValidationInscription);
        this.setToken(token);
        this.setUtilisateur(utilisateur);
    }

    public static ValidationInscription getValidationByToken(Connection con, String token) throws Exception {
        String query = "SELECT vi.id_validation, vi.token, u.id_utilisateur, u.email, u.mdp, u.tentative_max, u.id_etat " +
                       "FROM validation_inscription vi " +
                       "JOIN utilisateur u ON vi.id_utilisateur = u.id_utilisateur " +
                       "WHERE vi.token = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, token);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int idValidation = rs.getInt("id_validation");
                    String userToken = rs.getString("token");
                    Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id_utilisateur"),
                        rs.getString("email"),
                        rs.getString("mdp"),
                        rs.getInt("tentative_max"),
                        Etat.getById(con, rs.getInt("id_etat"))
                    );
                    return new ValidationInscription(idValidation, userToken, utilisateur);
                } else {
                    throw new Exception("Aucune validation trouvée pour le token : " + token);
                }
            }
        }
    }

    public void save(Connection con) throws Exception {
        String query = "INSERT INTO validation_inscription (token, id_utilisateur) VALUES (?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, this.token);
            pstmt.setInt(2, this.utilisateur.getIdUtilisateur());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Échec de l'enregistrement de la validation d'inscription.");
            }
        }catch(Exception e){
            throw new Exception("Erreur lors de l'insertion du validation d'inscription "+e.getMessage());
        }
    }

}
