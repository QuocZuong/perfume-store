package Lib;

import DAOs.UserDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.security.SecureRandom;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Generator {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*()_+~`|}{[]:;?><,./-=";
    private static final String ALL_ALLOWED_CHARS = CHAR_LOWER + CHAR_UPPER + NUMBER + SPECIAL_CHARS;
    private static final Random random = new SecureRandom();

    public static enum DatePattern {

        DateForwardSlashPattern("dd/MM/yyyy"),
        DateDashPattern("dd-MM-yyyy"),
        DateSqlPattern("yyyy-MM-dd"),
        DateTimeForwardSlashPattern("dd/MM/yyyy HH:mm:ss.SSS"),
        DateTimeDashPattern("dd-MM-yyyy HH:mm:ss.SSS"),
        DateTimeActivityLog("yyyy-MM-dd HH:mm:ss");

        public String pattern;

        private DatePattern(String pattern) {
            this.pattern = pattern;
        }

    };

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

    public static String generateForgotPassword() {
        Random randomGenerator = new Random();
        StringBuilder randomNumbers = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomNumber = randomGenerator.nextInt(10);
            randomNumbers.append(randomNumber);
        }

        String randomNumberString = randomNumbers.toString();

        return randomNumberString;
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

    public static String generateDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }

    public static String generateDateCustomPattern(DatePattern pattern) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern.pattern);
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }

    /**
     * Get the filtered list from the original list, according to its page and
     * num of row.
     *
     * @param <T> The type of the list.
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

    /**
     * Get the current time in milliseconds.
     *
     * @return a long number that represents the current time in milliseconds
     * from the epoch (1970-01-01).
     */
    public static long getCurrentTimeFromEpochMilli() {
        return Instant.now().toEpochMilli();
    }

    /**
     * Get the time string in a specific format.
     *
     * @param epochMilli The time in milliseconds from the epoch (1970-01-01).
     * @param pattern The format of the time string, as defined in
     * {@link Generator#DatePattern}.
     * @return The time string in the specified format.
     */
    public static String getDateTime(long epochMilli, DatePattern pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern.pattern);
        return sdf.format(new Date(epochMilli));
    }

    public static String generateProductHTML(String imageURL, String productName, int quantity, int price) {
        String result = "<tr>\n"
                + "                                            <td\n"
                + "                                                class=\"esdev-adapt-off\"\n"
                + "                                                align=\"left\"\n"
                + "                                                style=\"\n"
                + "                                                    margin: 0;\n"
                + "                                                    padding-top: 10px;\n"
                + "                                                    padding-bottom: 10px;\n"
                + "                                                    padding-left: 20px;\n"
                + "                                                    padding-right: 20px;\n"
                + "                                                \"\n"
                + "                                            >\n"
                + "                                                <table\n"
                + "                                                    cellpadding=\"0\"\n"
                + "                                                    cellspacing=\"0\"\n"
                + "                                                    class=\"esdev-mso-table\"\n"
                + "                                                    role=\"none\"\n"
                + "                                                    style=\"\n"
                + "                                                        mso-table-lspace: 0pt;\n"
                + "                                                        mso-table-rspace: 0pt;\n"
                + "                                                        border-collapse: collapse;\n"
                + "                                                        border-spacing: 0px;\n"
                + "                                                        width: 560px;\n"
                + "                                                    \"\n"
                + "                                                >\n"
                + "                                                    <tr>\n"
                + "                                                        <td\n"
                + "                                                            class=\"esdev-mso-td\"\n"
                + "                                                            valign=\"top\"\n"
                + "                                                            style=\"padding: 0; margin: 0\"\n"
                + "                                                        >\n"
                + "                                                            <table\n"
                + "                                                                cellpadding=\"0\"\n"
                + "                                                                cellspacing=\"0\"\n"
                + "                                                                class=\"es-left\"\n"
                + "                                                                align=\"left\"\n"
                + "                                                                role=\"none\"\n"
                + "                                                                style=\"\n"
                + "                                                                    mso-table-lspace: 0pt;\n"
                + "                                                                    mso-table-rspace: 0pt;\n"
                + "                                                                    border-collapse: collapse;\n"
                + "                                                                    border-spacing: 0px;\n"
                + "                                                                    float: left;\n"
                + "                                                                \"\n"
                + "                                                            >\n"
                + "                                                                <tr>\n"
                + "                                                                    <td\n"
                + "                                                                        class=\"es-m-p0r\"\n"
                + "                                                                        align=\"center\"\n"
                + "                                                                        style=\"padding: 0; margin: 0; width: 70px\"\n"
                + "                                                                    >\n"
                + "                                                                        <table\n"
                + "                                                                            cellpadding=\"0\"\n"
                + "                                                                            cellspacing=\"0\"\n"
                + "                                                                            width=\"100%\"\n"
                + "                                                                            role=\"presentation\"\n"
                + "                                                                            style=\"\n"
                + "                                                                                mso-table-lspace: 0pt;\n"
                + "                                                                                mso-table-rspace: 0pt;\n"
                + "                                                                                border-collapse: collapse;\n"
                + "                                                                                border-spacing: 0px;\n"
                + "                                                                            \"\n"
                + "                                                                        >\n"
                + "                                                                            <tr>\n"
                + "                                                                                <td\n"
                + "                                                                                    align=\"center\"\n"
                + "                                                                                    style=\"\n"
                + "                                                                                        padding: 0;\n"
                + "                                                                                        margin: 0;\n"
                + "                                                                                        font-size: 0px;\n"
                + "                                                                                    \"\n"
                + "                                                                                >\n"
                + "                                                                                    <img\n"
                + "                                                                                        class=\"adapt-img\"\n"
                + "                                                                                        src=\"https://fbhjjld.stripocdn.email/content/guids/CABINET_c67048fd0acf81b47e18129166337c05/images/43961618299486640.png\"\n"
                + "                                                                                        alt\n"
                + "                                                                                        style=\"\n"
                + "                                                                                            display: block;\n"
                + "                                                                                            border: 0;\n"
                + "                                                                                            outline: none;\n"
                + "                                                                                            text-decoration: none;\n"
                + "                                                                                            -ms-interpolation-mode: bicubic;\n"
                + "                                                                                        \"\n"
                + "                                                                                        width=\"70\"\n"
                + "                                                                                    />\n"
                + "                                                                                </td>\n"
                + "                                                                            </tr>\n"
                + "                                                                        </table>\n"
                + "                                                                    </td>\n"
                + "                                                                </tr>\n"
                + "                                                            </table>\n"
                + "                                                        </td>\n"
                + "                                                        <td style=\"padding: 0; margin: 0; width: 20px\"></td>\n"
                + "                                                        <td\n"
                + "                                                            class=\"esdev-mso-td\"\n"
                + "                                                            valign=\"top\"\n"
                + "                                                            style=\"padding: 0; margin: 0\"\n"
                + "                                                        >\n"
                + "                                                            <table\n"
                + "                                                                cellpadding=\"0\"\n"
                + "                                                                cellspacing=\"0\"\n"
                + "                                                                class=\"es-left\"\n"
                + "                                                                align=\"left\"\n"
                + "                                                                role=\"none\"\n"
                + "                                                                style=\"\n"
                + "                                                                    mso-table-lspace: 0pt;\n"
                + "                                                                    mso-table-rspace: 0pt;\n"
                + "                                                                    border-collapse: collapse;\n"
                + "                                                                    border-spacing: 0px;\n"
                + "                                                                    float: left;\n"
                + "                                                                \"\n"
                + "                                                            >\n"
                + "                                                                <tr>\n"
                + "                                                                    <td\n"
                + "                                                                        align=\"center\"\n"
                + "                                                                        style=\"padding: 0; margin: 0; width: 265px\"\n"
                + "                                                                    >\n"
                + "                                                                        <table\n"
                + "                                                                            cellpadding=\"0\"\n"
                + "                                                                            cellspacing=\"0\"\n"
                + "                                                                            width=\"100%\"\n"
                + "                                                                            role=\"presentation\"\n"
                + "                                                                            style=\"\n"
                + "                                                                                mso-table-lspace: 0pt;\n"
                + "                                                                                mso-table-rspace: 0pt;\n"
                + "                                                                                border-collapse: collapse;\n"
                + "                                                                                border-spacing: 0px;\n"
                + "                                                                            \"\n"
                + "                                                                        >\n"
                + "                                                                            <tr>\n"
                + "                                                                                <td\n"
                + "                                                                                    align=\"left\"\n"
                + "                                                                                    style=\"padding: 0; margin: 0\"\n"
                + "                                                                                >\n"
                + "                                                                                    <p\n"
                + "                                                                                        style=\"\n"
                + "                                                                                            margin: 0;\n"
                + "                                                                                            -webkit-text-size-adjust: none;\n"
                + "                                                                                            -ms-text-size-adjust: none;\n"
                + "                                                                                            mso-line-height-rule: exactly;\n"
                + "                                                                                            font-family: arial,\n"
                + "                                                                                                'helvetica neue',\n"
                + "                                                                                                helvetica, sans-serif;\n"
                + "                                                                                            line-height: 21px;\n"
                + "                                                                                            color: #333333;\n"
                + "                                                                                            font-size: 14px;\n"
                + "                                                                                        \"\n"
                + "                                                                                    >\n"
                + "                                                                                        <strong>T-shirt</strong>\n"
                + "                                                                                    </p>\n"
                + "                                                                                </td>\n"
                + "                                                                            </tr>\n"
                + "                                                                        </table>\n"
                + "                                                                    </td>\n"
                + "                                                                </tr>\n"
                + "                                                            </table>\n"
                + "                                                        </td>\n"
                + "                                                        <td style=\"padding: 0; margin: 0; width: 20px\"></td>\n"
                + "                                                        <td\n"
                + "                                                            class=\"esdev-mso-td\"\n"
                + "                                                            valign=\"top\"\n"
                + "                                                            style=\"padding: 0; margin: 0\"\n"
                + "                                                        >\n"
                + "                                                            <table\n"
                + "                                                                cellpadding=\"0\"\n"
                + "                                                                cellspacing=\"0\"\n"
                + "                                                                class=\"es-left\"\n"
                + "                                                                align=\"left\"\n"
                + "                                                                role=\"none\"\n"
                + "                                                                style=\"\n"
                + "                                                                    mso-table-lspace: 0pt;\n"
                + "                                                                    mso-table-rspace: 0pt;\n"
                + "                                                                    border-collapse: collapse;\n"
                + "                                                                    border-spacing: 0px;\n"
                + "                                                                    float: left;\n"
                + "                                                                \"\n"
                + "                                                            >\n"
                + "                                                                <tr>\n"
                + "                                                                    <td\n"
                + "                                                                        align=\"left\"\n"
                + "                                                                        style=\"padding: 0; margin: 0; width: 80px\"\n"
                + "                                                                    >\n"
                + "                                                                        <table\n"
                + "                                                                            cellpadding=\"0\"\n"
                + "                                                                            cellspacing=\"0\"\n"
                + "                                                                            width=\"100%\"\n"
                + "                                                                            role=\"presentation\"\n"
                + "                                                                            style=\"\n"
                + "                                                                                mso-table-lspace: 0pt;\n"
                + "                                                                                mso-table-rspace: 0pt;\n"
                + "                                                                                border-collapse: collapse;\n"
                + "                                                                                border-spacing: 0px;\n"
                + "                                                                            \"\n"
                + "                                                                        >\n"
                + "                                                                            <tr>\n"
                + "                                                                                <td\n"
                + "                                                                                    align=\"center\"\n"
                + "                                                                                    style=\"padding: 0; margin: 0\"\n"
                + "                                                                                >\n"
                + "                                                                                    <p\n"
                + "                                                                                        style=\"\n"
                + "                                                                                            margin: 0;\n"
                + "                                                                                            -webkit-text-size-adjust: none;\n"
                + "                                                                                            -ms-text-size-adjust: none;\n"
                + "                                                                                            mso-line-height-rule: exactly;\n"
                + "                                                                                            font-family: arial,\n"
                + "                                                                                                'helvetica neue',\n"
                + "                                                                                                helvetica, sans-serif;\n"
                + "                                                                                            line-height: 21px;\n"
                + "                                                                                            color: #333333;\n"
                + "                                                                                            font-size: 14px;\n"
                + "                                                                                        \"\n"
                + "                                                                                    >\n"
                + "                                                                                        1 pcs\n"
                + "                                                                                    </p>\n"
                + "                                                                                </td>\n"
                + "                                                                            </tr>\n"
                + "                                                                        </table>\n"
                + "                                                                    </td>\n"
                + "                                                                </tr>\n"
                + "                                                            </table>\n"
                + "                                                        </td>\n"
                + "                                                        <td style=\"padding: 0; margin: 0; width: 20px\"></td>\n"
                + "                                                        <td\n"
                + "                                                            class=\"esdev-mso-td\"\n"
                + "                                                            valign=\"top\"\n"
                + "                                                            style=\"padding: 0; margin: 0\"\n"
                + "                                                        >\n"
                + "                                                            <table\n"
                + "                                                                cellpadding=\"0\"\n"
                + "                                                                cellspacing=\"0\"\n"
                + "                                                                class=\"es-right\"\n"
                + "                                                                align=\"right\"\n"
                + "                                                                role=\"none\"\n"
                + "                                                                style=\"\n"
                + "                                                                    mso-table-lspace: 0pt;\n"
                + "                                                                    mso-table-rspace: 0pt;\n"
                + "                                                                    border-collapse: collapse;\n"
                + "                                                                    border-spacing: 0px;\n"
                + "                                                                    float: right;\n"
                + "                                                                \"\n"
                + "                                                            >\n"
                + "                                                                <tr>\n"
                + "                                                                    <td\n"
                + "                                                                        align=\"left\"\n"
                + "                                                                        style=\"padding: 0; margin: 0; width: 85px\"\n"
                + "                                                                    >\n"
                + "                                                                        <table\n"
                + "                                                                            cellpadding=\"0\"\n"
                + "                                                                            cellspacing=\"0\"\n"
                + "                                                                            width=\"100%\"\n"
                + "                                                                            role=\"presentation\"\n"
                + "                                                                            style=\"\n"
                + "                                                                                mso-table-lspace: 0pt;\n"
                + "                                                                                mso-table-rspace: 0pt;\n"
                + "                                                                                border-collapse: collapse;\n"
                + "                                                                                border-spacing: 0px;\n"
                + "                                                                            \"\n"
                + "                                                                        >\n"
                + "                                                                            <tr>\n"
                + "                                                                                <td\n"
                + "                                                                                    align=\"right\"\n"
                + "                                                                                    style=\"padding: 0; margin: 0\"\n"
                + "                                                                                >\n"
                + "                                                                                    <p\n"
                + "                                                                                        style=\"\n"
                + "                                                                                            margin: 0;\n"
                + "                                                                                            -webkit-text-size-adjust: none;\n"
                + "                                                                                            -ms-text-size-adjust: none;\n"
                + "                                                                                            mso-line-height-rule: exactly;\n"
                + "                                                                                            font-family: arial,\n"
                + "                                                                                                'helvetica neue',\n"
                + "                                                                                                helvetica, sans-serif;\n"
                + "                                                                                            line-height: 21px;\n"
                + "                                                                                            color: #333333;\n"
                + "                                                                                            font-size: 14px;\n"
                + "                                                                                        \"\n"
                + "                                                                                    >\n"
                + "                                                                                        $20\n"
                + "                                                                                    </p>\n"
                + "                                                                                </td>\n"
                + "                                                                            </tr>\n"
                + "                                                                        </table>\n"
                + "                                                                    </td>\n"
                + "                                                                </tr>\n"
                + "                                                            </table>\n"
                + "                                                        </td>\n"
                + "                                                    </tr>\n"
                + "                                                </table>\n"
                + "                                            </td>\n"
                + "                                        </tr>";

        result = result.replace("https://fbhjjld.stripocdn.email/content/guids/CABINET_c67048fd0acf81b47e18129166337c05/images/43961618299486640.png", imageURL);
        result = result.replace("T-shirt", productName);
        result = result.replace("1 pcs", quantity + "");
        result = result.replace("$20", Converter.convertMoneyToInteger(price + "") + "vnđ");
        return result;
    }
}
