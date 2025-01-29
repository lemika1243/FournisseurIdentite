package helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.time.format.DateTimeFormatter;

public class Util {

    public static String generateRandomString(int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!$%";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            sb.append(characters.charAt(randomIndex));
        }

        return sb.toString();
    }

    public static int generateRandomInt(int min, int max) {
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public static String formatResponse(String status, int errorCode, String message, Map<String, Object> data) {
        Gson gson = new Gson();
        JsonObject responseJson = new JsonObject();
        
        responseJson.addProperty("status", status);
        responseJson.addProperty("code", errorCode);
        responseJson.addProperty("message", message);

        JsonObject dataJson = new JsonObject();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            dataJson.add(entry.getKey(), gson.toJsonTree(entry.getValue()));
        }
        
        responseJson.add("data", dataJson);
        
        return responseJson.toString();
    }


    public static JsonObject extractJsonFrom(HttpServletRequest request)throws Exception{
        StringBuilder stringBuilder = new StringBuilder();
        try(BufferedReader bufferedReader = request.getReader()){
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }
            JsonElement jsonElement = new JsonParser().parse(stringBuilder.toString());
            return jsonElement.getAsJsonObject();
        }catch(Exception e){
            throw new Exception("Erreur lors de l'extraction du body json object "+e.getMessage());
        }
    }

    
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    public static int generateRandomPin(int length) {
        final String digits = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(digits.length());
            sb.append(digits.charAt(randomIndex));
        }

        return Integer.parseInt(sb.toString());
    }

    
    // public static Timestamp addDurationToTimestamp(Timestamp timestamp, float duree) {
    //     LocalDateTime dateTime = timestamp.toLocalDateTime();

    //     int hours = (int) duree;
    //     int minutes = Math.round((duree - hours) * 60);

    //     LocalDateTime newDateTime = dateTime.plusHours(hours).plusMinutes(minutes);

    //     return Timestamp.valueOf(newDateTime);
    // }

    public static Timestamp addDurationToTimestamp(Timestamp timestamp, float duree) {
        long totalSeconds = Math.round(duree);
    
        LocalDateTime dateTime = timestamp.toLocalDateTime();
        LocalDateTime newDateTime = dateTime.plusSeconds(totalSeconds);
    
        return Timestamp.valueOf(newDateTime);
    }

    public static void supports(HttpServletRequest request)throws Exception{
        String headerValue = request.getHeader("Authorization");
        if (headerValue == null || headerValue.isEmpty()) {
            throw new Exception("Header invalide ou manquant");
        }
        if (!headerValue.startsWith("Bearer ")) {
            throw new Exception("Token non fourni");
        }
    }

    public static String extractToken(HttpServletRequest request)throws Exception{
        try {
            supports(request);
            return request.getHeader("Authorization").substring(7);
        } catch (Exception e) {
            throw e;
        }
    }
    public static String generateStyledHtmlPin(int pin, Timestamp dateExpiration) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss");
        String formattedDate = dateExpiration.toLocalDateTime().format(formatter);
    
        return "<div style=\"font-family: Arial, sans-serif; color: #333; background-color: #f9f9f9; " +
               "border: 2px solid #4CAF50; border-radius: 10px; padding: 20px; text-align: center; " +
               "width: fit-content; margin: auto;\">" +
               "<h2 style=\"color: #4CAF50;\">Votre Code PIN Login Fournisseur Identité</h2>" +
               "<p style=\"font-size: 24px; font-weight: bold; margin: 10px 0;\">" + pin + "</p>" +
               "<p style=\"font-size: 20px; font-weight: bold; margin: 10px 0;\">" +
               "Ce code expirera le : " + formattedDate + "</p>" +
               "<p style=\"font-size: 14px; color: #555;\">" +
               "Veuillez ne pas partager ce code avec quiconque.</p>" +
               "</div>";
    }

    // public static String generateLink(String text) {
    //     return "<div style=\"font-family: Arial, sans-serif; color: #333; background-color: #f9f9f9; " +
    //            "border: 2px solid #4CAF50; border-radius: 10px; padding: 20px; text-align: center; " +
    //            "width: fit-content; margin: auto;\"><a href="+text+">Cliquer ce lien pour valider l'inscription</a></div>";
    // }

    public static String generateLink(String text) {
        return "<div style=\"font-family: Arial, sans-serif; color: #333; background-color: #f9f9f9; " +
               "border: 2px solid #4CAF50; border-radius: 10px; padding: 20px; text-align: center; " +
               "width: fit-content; margin: auto;\"><p>Copier ce lien pour valider l'inscription <strong>"+text+"</strong> </p></div>";
    }
}
