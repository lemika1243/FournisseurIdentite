package controller;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.PrintWriter;

import model.Connexion;
import model.Pin;
import model.Tentative;
import model.Token;
import java.sql.Connection;

import com.google.gson.JsonObject;

import helper.Util;
import helper.Constantes;
import model.Reference;
import model.TypeReference;
import model.Utilisateur;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/login")
public class LoginController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        try {
            out.println("Connected !");
        } catch (Exception e) {
            out.println("Erreur dans test connexion servlet " + e.getMessage());
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

            if (utilisateur == null) {
                throw new Exception("Aucun utilisateur n'est associe a cet email");
            }

            Tentative[] tentatives = Tentative.getAllByUtilisateur(utilisateur, c);
            int tentativeRestant = utilisateur.getTentativeMax() - tentatives.length - 1;

            if (utilisateur.estBloque()) {
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("message", "Ce compte est bloque");
                out.print(Util.formatResponse("Error", Constantes.TENTATIVE_DEPASSE, "Ce compte est bloque", responseMap));
                return;
            }

            if (tentativeRestant <= 0) {
                utilisateur.bloque(c);
                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("message", "Trop de tentatives, vous êtes bloqué");
                out.print(Util.formatResponse("Error", Constantes.TENTATIVE_DEPASSE, "Trop de tentatives, vous êtes bloqué", responseMap));
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

                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("message", "Mot de passe erroné, il vous reste " + tentativeRestant + " essai(s)");
                    out.print(Util.formatResponse("Error", Constantes.TENTATIVE_DEPASSE, "Mot de passe erroné, il vous reste " + tentativeRestant + " essai(s)", responseMap));
                    c.commit();
                    c.close();
                    return;
                }

                // Supprimer les anciens pins de l'utilisateur, car on ne doit pas les stocker
                Pin.delete(c, utilisateur);
                Reference[] refs = Reference.getAll(c, TypeReference.getAll(c));
                // Génération d'un nouveau pin
                Pin pin = Pin.genererPin(utilisateur, refs);
                Utilisateur.sendEmail(Constantes.EMAIL, Constantes.MDP_EMAIL_APP, utilisateur.getEmail(), Util.generateStyledHtmlPin(pin.getPin(), pin.getDateExpiration()));
                pin.save(c);

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("message", "Veuillez fournir le PIN que nous avons envoyé à votre adresse email");
                out.print(Util.formatResponse("Success", Constantes.SUCCESS_CODE, "Veuillez fournir le PIN que nous avons envoyé à votre adresse email", responseMap));
            } else if (type.equals("pin")) {
                int pinInForm = jsonObject.get("pin").getAsInt();
                Pin pin = Pin.getPinByUtilisateur(utilisateur, c);

                if (pin != null && pin.getPin() == pinInForm) {
                    if (pin.getDateExpiration().before(Util.getCurrentTimestamp())) {
                        Map<String, Object> responseMap = new HashMap<>();
                        // responseMap.put("status", "Error");
                        // responseMap.put("code", Constantes.TENTATIVE_DEPASSE);
                        // responseMap.put("message", "Ce PIN est déjà expiré");
                        out.print(Util.formatResponse("Error", Constantes.TENTATIVE_DEPASSE, "Ce PIN est déjà expiré", responseMap));
                        return;
                    }

                    Token token = Token.getTokenByUtilisateur(c, utilisateur, request.getHeader("User-Agent"));
                    Tentative.delete(c, utilisateur.getIdUtilisateur());

                    if (token == null) {
                        token = Token.genererToken(c, utilisateur, request.getHeader("User-Agent"));
                        token.insert(c);
                    } else {
                        token.setToken(c, Util.generateRandomString(Constantes.TOKEN_LONGUEUR));
                    }

                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("message", "Vous êtes connecté");
                    responseMap.put("token", token.getToken());
                    responseMap.put("email", utilisateur.getEmail());
                    responseMap.put("dateExpiration", token.getDateExpiration().toString());
                    out.print(Util.formatResponse("Success", Constantes.SUCCESS_CODE, "Vous êtes connecté", responseMap));
                } else {
                    Tentative tentative = new Tentative(Util.getCurrentTimestamp(), utilisateur);
                    tentative.save(c);

                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("message", "PIN non reconnu, il vous reste " + tentativeRestant + " essai(s)");
                    out.print(Util.formatResponse("Error", Constantes.INTERNAL_SERVER_ERROR, "PIN non reconnu, il vous reste " + tentativeRestant + " essai(s)", responseMap));
                }
            }
            c.commit();
        } catch (Exception e) {
            try {
                c.rollback();
            } catch (Exception ex) {
                Map<String, Object> responseMap = new HashMap<>();
                out.print(Util.formatResponse("Error", Constantes.INTERNAL_SERVER_ERROR, "Erreur " + ex.getMessage(), responseMap));
            }
            Map<String, Object> responseMap = new HashMap<>();
            out.print(Util.formatResponse("Error", Constantes.INTERNAL_SERVER_ERROR, "Erreur " + e.getMessage(), responseMap));
        } finally {
            try {
                if (c != null) c.close();
            } catch (Exception e) {
                // Log error
            }
        }
    }
}
