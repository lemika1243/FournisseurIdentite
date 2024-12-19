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

@WebServlet("/api/reinitialisation")
public class ReinitialisationCompteController extends HttpServlet{
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        Connection con = null;
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        try{
            JsonObject jsonObject = Util.extractJsonFrom(request);
            String email = jsonObject.get("email").getAsString();
            String mdp = jsonObject.get("mdp").getAsString();
            con = Connexion.dbConnect();
            Utilisateur utilisateur = Utilisateur.getUtilisateurByEmail(email, con);
            if (!utilisateur.getMdp().equals(Util.hashPassword(mdp))) {
                throw new Exception("Mot de passe incorrect");
            }
            Token userToken = Token.getTokenByUtilisateur(con, utilisateur, request.getHeader("User-Agent"));
            String token = Util.extractToken(request);
            if (!token.equals(userToken.getToken())) {
                throw new Exception("Token invalide");
            }
            if (userToken.getDateExpiration().before(Util.getCurrentTimestamp())) {
                throw new Exception("Token expiré");
            }
            Utilisateur.sendEmail(Constantes.EMAIL, Constantes.MDP_EMAIL_APP, email, Constantes.BASE_URL+"/api/reinitialisation/validation?e="+email);
            out.println(Util.formatResponse("Success", Constantes.SUCCESS_CODE, "Veuillez verifier votre boite de reception email pour valider la recuperation du compte", new String[0]));
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            out.println(Util.formatResponse("Erreur", Constantes.INTERNAL_SERVER_ERROR, "Erreur "+e.getMessage(), new String[0]));
        }finally{
            try {
                if (con != null) con.close();
            } catch (Exception e) {
            }
        }
    }

}
