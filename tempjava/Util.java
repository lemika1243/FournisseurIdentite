package helper;

import java.security.SecureRandom;
import java.util.Random;

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

}
