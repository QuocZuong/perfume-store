package Lib;

import java.util.Random;
import java.security.SecureRandom;

public class PasswordGenerator {
  private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
  private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
  private static final String NUMBER = "0123456789";
  private static final String SPECIAL_CHARS = "!@#$%^&*()_+~`|}{[]:;?><,./-=";
  private static final String ALL_ALLOWED_CHARS = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARS;
  private static final Random random = new SecureRandom();

  public static String generateStrongPassword(int length) {
    StringBuilder sb = new StringBuilder(length);

    // Add at least one lowercase character
    sb.append(CHAR_LOWER.charAt(random.nextInt(CHAR_LOWER.length())));

    // Add at least one uppercase character
    sb.append(CHAR_UPPER.charAt(random.nextInt(CHAR_UPPER.length())));

    // Add at least one number
    sb.append(NUMBER.charAt(random.nextInt(NUMBER.length())));

    // Add at least one special character
    sb.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

    // Generate remaining characters randomly
    for (int i = 4; i < length; i++) {
      sb.append(ALL_ALLOWED_CHARS.charAt(random.nextInt(ALL_ALLOWED_CHARS.length())));
    }

    // Shuffle the generated password
    char[] passwordChars = sb.toString().toCharArray();
    for (int i = 0; i < passwordChars.length; i++) {
      int randomIndex = random.nextInt(passwordChars.length);
      char temp = passwordChars[i];
      passwordChars[i] = passwordChars[randomIndex];
      passwordChars[randomIndex] = temp;
    }

    return new String(passwordChars);
  }
}