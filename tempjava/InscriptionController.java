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

import com.google.gson.JsonObject;

import helper.Constantes;
import helper.Util;
import java.sql.Connection;
import java.sql.SQLException;

import model.Utilisateur;

@WebServlet("/api/inscription")
public class InscriptionController extends HttpServlet{

    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
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
            Utilisateur.sendEmail(Constantes.EMAIL, Constantes.MDP_EMAIL_APP, utilisateur.getEmail(), Constantes.BASE_URL+"/api/inscription/validation?token="+tempToken);
            validationInscription.save(con);
            con.commit();
            out.println(Util.formatResponse("Success", Constantes.SUCCESS_CODE, "Veuillez verifier votre boite de reception email et valider l'inscription", new String[0]));
            
            out.println(Util.formatResponse("Success", Constantes.SUCCESS_CODE, "Donnee recu", new String[]{email, mdp}));
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                out.println(Util.formatResponse("Erreur", Constantes.INTERNAL_SERVER_ERROR, "Erreur "+e1.getMessage(), new String[0]));
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
