package controller;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.PrintWriter;

import model.Connexion;
import model.Pin;
import model.Reference;
import model.TypeReference;
import model.Utilisateur;

import java.sql.Connection;

import io.swagger.v3.oas.annotations.media.Schema;

@WebServlet("/loginController")
public class LoginController extends HttpServlet{
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        PrintWriter out = response.getWriter();
        try{
            out.println("Connected !");
        }catch(Exception e){
            out.println("Erreur dans test connexion servlet "+e.getMessage());
        }
    }

    @Schema(description = "POST : pour le login en email et password et pour la verification du pin")
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        PrintWriter out = response.getWriter();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String type = request.getParameter("type");
        Utilisateur utilisateur = null;
        // String query = "SELECT * FROM utilisateur where mdp = '"+Utilisateur.hashPassword(password)+"' and email = '"+email+"'";
        // out.println(query);
        try {
            Connection c = Connexion.dbConnect();
            if(type.equals("pin") && request.getParameter("pin")!=null){
                utilisateur = (Utilisateur) request.getSession().getAttribute("utilisateur");
                int pinInForm = Integer.parseInt(request.getParameter("pin"));
                Pin pin = Pin.getPinByUtilisateur(utilisateur, c);
                if(pin!=null && pin.getPin()==pinInForm){
                    
                }
            }
            else if(type.equals("connect")){
                utilisateur = Utilisateur.getByEmailAndPassword(c, email, password);
                Pin pin = Pin.genererPin(utilisateur, "Token", Reference.getAll(c, TypeReference.getAll(c)));
                Utilisateur.sendEmail("mikajyjoharytsiorysarobidy@gmail.com", "cmhi suiu sebd uepr ", email, pin.getPin()+"");
                out.println("PIN envoyee");
            }
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }

}
