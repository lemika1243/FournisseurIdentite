package controller;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.PrintWriter;

import model.Connexion;
import model.Pin;
import model.Tentative;
import model.Utilisateur;
import model.Reference;
import model.Token;

import java.sql.Connection;

import com.google.gson.JsonObject;

import helper.Util;
import helper.Constantes;
import model.Reference;
import model.TypeReference;
import model.Utilisateur;

@WebServlet("/api/login")
public class LoginController extends HttpServlet{
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        PrintWriter out = response.getWriter();
        try{
            
            out.println("Connected !");
        }catch(Exception e){
            out.println("Erreur dans test connexion servlet "+e.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException{
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String message = "";
        Connection c = null;
        try {
            c = Connexion.dbConnect();
            c.setAutoCommit(false);
            JsonObject jsonObject = Util.extractJsonFrom(request);
            String email = jsonObject.get("email").getAsString();
            String type = jsonObject.get("type").getAsString();
            Utilisateur utilisateur = Utilisateur.getUtilisateurByEmail(email, c);
            Tentative[] tentatives = Tentative.getAllByUtilisateur(utilisateur, c);
            if(utilisateur.getTentativeMax() < tentatives.length){
                utilisateur.bloque(c);
                out.print(Util.formatResponse("Error",Constantes.TENTATIVE_DEPASSE,"trop de tentative , vous etes bloque en consequant",new String[0]));
                c.commit();
                c.close();
                return;
            }
            if (type.equalsIgnoreCase("login")) {
                String mdp = jsonObject.get("mdp").getAsString();
                mdp = Util.hashPassword(mdp);
                if (!utilisateur.getMdp().equals(mdp)) {
                    Tentative tentative = new Tentative(Util.getCurrentTimestamp(), utilisateur);
                    tentative.save(c);
                    out.print(Util.formatResponse("Error",Constantes.TENTATIVE_DEPASSE,"Mot de passe errone il vous reste "+(utilisateur.getTentativeMax() - tentatives.length - 1)+" d'essaie(s)",new String[0]));
                    c.commit();
                    c.close();
                    return;
                }
                //supprimer les anciens pin de l'utilisateur car on pas a les stockes
                Pin.delete(c, utilisateur);
                Reference[] refs = Reference.getAll(c, TypeReference.getAll(c));
                //generation d'une nouvelle pin
                Pin pin = Pin.genererPin(utilisateur, refs);
                Utilisateur.sendEmail(Constantes.EMAIL, Constantes.MDP_EMAIL_APP, utilisateur.getEmail(), "Veuillez copier ce chiffre pour valider l'authentification multi-facteur "+pin.getPin());
                pin.save(c);
                message = Util.formatResponse("Success",Constantes.SUCCESS_CODE,"Veuillez fournir le PIN que nous avons envoye a votre adresse email",new String[0]);
            }else if(type.equals("pin")){
                int pinInForm = jsonObject.get("pin").getAsInt();
                Pin pin = Pin.getPinByUtilisateur(utilisateur, c);
                if (pin.getDateExpiration().before(Util.getCurrentTimestamp())) {
                    out.print(Util.formatResponse("Error", Constantes.TENTATIVE_DEPASSE,"Ce Pin est deja expire",new String[0]));
                    return;
                }
                if(pin!=null && pin.getPin() == pinInForm){
                    Token token = Token.getTokenByUtilisateur(c, utilisateur,request.getHeader("User-Agent"));
                    Tentative.delete(c, utilisateur.getIdUtilisateur());
                    if(token==null){
                        token = Token.genererToken(c, utilisateur , request.getHeader("User-Agent"));
                        token.insert(c);
                    }
                    else{
                        token.setToken(c, Util.generateRandomString(Constantes.TOKEN_LONGUEUR));
                    }
                    message=Util.formatResponse("Success",Constantes.SUCCESS_CODE,"Vous etes connecte",new String[]{token.getToken()});
                }
                else{
                    Tentative tentative = new Tentative(Util.getCurrentTimestamp(), utilisateur);
                    tentative.save(c);
                    message = Util.formatResponse("Error",Constantes.INTERNAL_SERVER_ERROR,"PIN non reconnu il vous reste "+(utilisateur.getTentativeMax() - tentatives.length - 1)+" essaie(s)",new String[0]);
                }
            }
            c.commit();
            out.print(message);
        } catch (Exception e) {
            try {
                c.rollback();
            } catch (Exception ex) {
                // TODO: handle exception
            }
            out.println(Util.formatResponse("Erreur", Constantes.INTERNAL_SERVER_ERROR, "Erreur "+e.getMessage(), new String[0]));
        }finally{
            try {
                if (c != null) c.close();
            } catch (Exception e) {
            }
        }
    }

}
