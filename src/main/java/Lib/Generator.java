package Lib;

import DAOs.UserDAO;
import Models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Generator {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+~`|}{[]:;?><,./-=";
    private static final String ALL_ALLOWED_CHARS = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARS;
    private static final Random random = new SecureRandom();

    public static String generatePassword(int length) {
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

    public static String generateUsername(String email) {
        // Get the word before the symbol '@'
        int toIndex = email.indexOf('@', 0);
        String username = email.substring(0, toIndex);
        UserDAO uDAO = new UserDAO();
        // Generate unique username
        while (uDAO.getUser(username) != null) {
            username += DatabaseUtils.getLastIndentityOf("User");
        }
        return username;
    }

    public static String generateDateTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }

    /**
     * Get the filtered list from the original list, according to its page and num
     * of row.
     * 
     * @param <T>  The type of the list.
     * @param list The original list.
     * @param page The page number.
     * @param rows The number of rows per page.
     * @return The filtered list.
     */
    public static <T> List<T> pagingList(List<T> list, int page, int rows) {
        if (list == null || list.isEmpty() || page <= 0) {
            return new ArrayList<>();
        }

        int offset = rows * (page - 1);
        int toIndex = offset + rows;

        if (toIndex >= list.size()) {
            toIndex = list.size();
        }

        if (offset >= list.size()) {
            offset = list.size();
        }

        List<T> subList = list.subList(offset, toIndex);
        return subList;
    }

}
