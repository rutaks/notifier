package rw.rutakayile.k8sproject.util;

import java.security.SecureRandom;

public class StringUtil {
    public static StringBuilder generateRandomString(int length) {
    // You can customize the characters that you want to add into
    // the random strings
    String charLower = "abcdefghijklmnopqrstuvwxyz";
    String charUpper = charLower.toUpperCase();
    String number = "0123456789";

    String dataForRandomString = charLower + charUpper + number;
    SecureRandom random = new SecureRandom();

    if (length < 1) throw new IllegalArgumentException();

    StringBuilder sb = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
        // 0-62 (exclusive), random returns 0-61
        int rndCharAt = random.nextInt(dataForRandomString.length());
        char rndChar = dataForRandomString.charAt(rndCharAt);

        sb.append(rndChar);
    }
    return sb;
}

    public StringBuilder generateRandomStringWithSpecialChars(int length) {
        // You can customize the characters that you want to add into
        // the random strings
        String charLower = "abcdefghijklmnopqrstuvwxyz";
        String charUpper = charLower.toUpperCase();
        String number = "0123456789";
        String specialChars = "!@#$%^&*()";

        String dataForRandomString = charLower + charUpper + number + specialChars;
        SecureRandom random = new SecureRandom();

        if (length < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(dataForRandomString.length());
            char rndChar = dataForRandomString.charAt(rndCharAt);

            sb.append(rndChar);
        }
        return sb;
    }
}
