package helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class Util {

    public static String generateRandomString(int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%";
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

    public static String formatResponse(String status, int errorCode, String message, String[] data) {
        Gson gson = new Gson();
        JsonObject errorJson = new JsonObject();
        errorJson.addProperty("status", status);
        errorJson.addProperty("code", errorCode);
        errorJson.addProperty("data", gson.toJson(data));
        errorJson.addProperty("message", message);
        return errorJson.toString();
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

}
