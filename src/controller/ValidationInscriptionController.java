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


import helper.Constantes;
import helper.Util;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/api/inscription/validation")
public class ValidationInscriptionController extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        Connection con = null;
        String queryString = request.getQueryString();
        String token = queryString.split("token=")[1];
        try {
            con = Connexion.dbConnect();
            con.setAutoCommit(false);
            ValidationInscription validationInscription = ValidationInscription.getValidationByToken(con, token);
            validationInscription.getUtilisateur().validee(con);
            con.commit();
            out.println(Util.formatResponse("Succes", Constantes.SUCCESS_CODE, "Felicitation pour votre inscription", new String[0]));
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
