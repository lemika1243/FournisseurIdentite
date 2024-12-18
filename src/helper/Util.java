package helper;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;
import java.sql.Timestamp;

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

    public static int generateRandomPin(int length) {
        final String digits = "0123456789"; // Utiliser uniquement des chiffres pour un PIN
        SecureRandom random = new SecureRandom(); // Générer des nombres aléatoires sécurisés
        StringBuilder sb = new StringBuilder(length); // Utiliser un StringBuilder pour construire la chaîne

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(digits.length()); // Générer un index aléatoire pour choisir un chiffre
            sb.append(digits.charAt(randomIndex)); // Ajouter le chiffre à la chaîne
        }

        // Convertir la chaîne de chiffres en entier et la retourner
        return Integer.parseInt(sb.toString()); // Retourner le PIN sous forme d'un entier
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

     public static Timestamp addDurationToTimestamp(Timestamp timestamp, float duree) {
        LocalDateTime dateTime = timestamp.toLocalDateTime();

        int hours = (int) duree; // La partie entière est le nombre d'heures
        int minutes = Math.round((duree - hours) * 60); // La partie décimale est convertie en minutes

        LocalDateTime newDateTime = dateTime.plusHours(hours).plusMinutes(minutes);

        return Timestamp.valueOf(newDateTime);
    }

    public static Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

}
