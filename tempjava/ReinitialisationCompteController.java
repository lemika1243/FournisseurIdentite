package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Connexion;
import model.Token;
import model.Utilisateur;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import com.google.gson.JsonObject;

import helper.Constantes;
import helper.Util;

import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/reinitialisation")
public class ReinitialisationCompteController extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection con = null;
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        try {
            JsonObject jsonObject = Util.extractJsonFrom(request);
            String email = jsonObject.get("email").getAsString();
            String mdp = jsonObject.get("mdp").getAsString();
            con = Connexion.dbConnect();
            Utilisateur utilisateur = Utilisateur.getUtilisateurByEmail(email, con);
            
            if (!utilisateur.getMdp().equals(Util.hashPassword(mdp))) {
                throw new Exception("Mot de passe incorrect");
            }

            // Token userToken = Token.getTokenByUtilisateur(con, utilisateur, request.getHeader("User-Agent"));
            // String token = Util.extractToken(request);
            // if (!token.equals(userToken.getToken())) {
            //     throw new Exception("Token invalide");
            // }
            // if (userToken.getDateExpiration().before(Util.getCurrentTimestamp())) {
            //     throw new Exception("Token expire");
            // }

            // Envoi de l'email de reinitialisation
            Utilisateur.sendEmail(Constantes.EMAIL, Constantes.MDP_EMAIL_APP, email, Constantes.BASE_URL + "/api/reinitialisation/validation?e=" + email);

            // Preparation de la reponse
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("status", "Success");
            responseMap.put("code", Constantes.SUCCESS_CODE);
            responseMap.put("message", "Veuillez verifier votre boîte de reception email pour valider la recuperation du compte");

            out.print(Util.formatResponse("Success", Constantes.SUCCESS_CODE, "Veuillez verifier votre boîte de reception email pour valider la recuperation du compte", responseMap));

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("status", "Error");
            responseMap.put("code", Constantes.INTERNAL_SERVER_ERROR);
            responseMap.put("message", "Erreur " + e.getMessage());
            
            out.print(Util.formatResponse("Error", Constantes.INTERNAL_SERVER_ERROR, "Erreur " + e.getMessage(), responseMap));
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
                // Log error if needed
            }
        }
    }
}
