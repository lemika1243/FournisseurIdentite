package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Connexion;
import model.Utilisateur;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.JsonObject;

import helper.Constantes;
import helper.Util;
import java.sql.Connection;

@WebServlet("/api/updateInfo")
public class UpdateInfoController extends HttpServlet{
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        Connection con = null;
        try {
            con = Connexion.dbConnect();
            con.setAutoCommit(false);
            JsonObject jsonObject = Util.extractJsonFrom(request);
            String email = jsonObject.get("email").getAsString();
            String mdp = jsonObject.get("mdp").getAsString();
            Utilisateur utilisateur = Utilisateur.getUtilisateurByEmail(email, null);
            utilisateur.setMdp(mdp);
            utilisateur.update(con);
            con.commit();
            out.println(Util.formatResponse("Success", Constantes.SUCCESS_CODE, "Les informations du compte a ete mis a jour", new String[0]));
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception ee) {
            }
            out.println(Util.formatResponse("Erreur", Constantes.INTERNAL_SERVER_ERROR, e.getMessage(), new String[0]));
        }finally{
            try {
                if (con != null) con.close();
            } catch (Exception e) {
            }
        }
    }

}
