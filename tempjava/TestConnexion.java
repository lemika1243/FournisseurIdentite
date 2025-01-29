package controller;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.PrintWriter;
import model.Connexion;

import java.sql.Connection;

@WebServlet("/test")
public class TestConnexion extends HttpServlet{
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        PrintWriter out = response.getWriter();
        try{
            // Connection c = Connexion.dbConnect();
            out.println("Connected !");
        }catch(Exception e){
            out.println("Erreur dans test connexion servlet "+e.getMessage());
        }
    }

}
