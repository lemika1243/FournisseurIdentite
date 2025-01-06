package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Connexion;
import model.Etat;
import model.Utilisateur;
import model.ValidationInscription;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

import helper.Constantes;
import helper.Util;

@WebServlet("/api/inscription")
public class InscriptionController extends HttpServlet {

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Add CORS headers for preflight requests
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, x-binarybox-api-key");
        response.setHeader("Access-Control-Max-Age", "3600");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Connection con = null;
        
        try {
            con = Connexion.dbConnect();
            con.setAutoCommit(false);
            
            JsonObject jsonObject = Util.extractJsonFrom(request);
            String email = jsonObject.get("email").getAsString();
            String mdp = jsonObject.get("mdp").getAsString();
            int tentativeMax = Constantes.TENTATIVE_MAX;
            
            Etat etat = Etat.getEtatByUniqueEtat(con, Constantes.CREE);
            
            Utilisateur utilisateur = new Utilisateur(-1, email, Util.hashPassword(mdp), tentativeMax, etat);
            utilisateur.save(con);
            
            String tempToken = Util.generateRandomString(Constantes.TOKEN_LONGUEUR);
            ValidationInscription validationInscription = new ValidationInscription(-1, tempToken, utilisateur);
            Utilisateur.sendEmail(Constantes.EMAIL, Constantes.MDP_EMAIL_APP, utilisateur.getEmail(),
                    Util.generateLink(Constantes.BASE_URL + "/api/inscription/validation?token=" + tempToken));
            validationInscription.save(con);
            
            con.commit();

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("message", "Veuillez verifier votre boite de reception email et valider l'inscription");

            out.println(Util.formatResponse("Success", Constantes.SUCCESS_CODE, "Inscription réussie", responseData));

        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException e1) {
                Map<String, Object> errorData = new HashMap<>();
                errorData.put("error", e1.getMessage());
                out.println(Util.formatResponse("Erreur", Constantes.INTERNAL_SERVER_ERROR, "Erreur lors de la transaction", errorData));
            }

            Map<String, Object> errorData = new HashMap<>();
            errorData.put("error", e.getMessage());
            out.println(Util.formatResponse("Erreur", Constantes.INTERNAL_SERVER_ERROR, "Erreur lors de l'inscription", errorData));
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
            }
        }
    }
}
