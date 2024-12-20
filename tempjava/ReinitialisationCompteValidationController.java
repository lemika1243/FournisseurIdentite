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
import java.sql.Connection;
import java.sql.SQLException;

import helper.Constantes;
import helper.Util;

@WebServlet("/api/reinitialisation/validation")
public class ReinitialisationCompteValidationController extends HttpServlet{
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        Connection con = null;
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        String queryString = request.getQueryString();
        String email = queryString.split("e=")[1];
        try{
            con = Connexion.dbConnect();
            con.setAutoCommit(false);
            Utilisateur utilisateur = Utilisateur.getUtilisateurByEmail(email, con);
            utilisateur.reinitialiser(con);
            con.commit();
            out.println(Util.formatResponse("Success", Constantes.SUCCESS_CODE, "Votre compte a ete reinitialise", new String[0]));
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
