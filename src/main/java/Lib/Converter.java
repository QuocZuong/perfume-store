package Lib;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Converter {

    public static String convertToStringData(
            String pName,
            String bName,
            String pPrice,
            String Gender,
            String Smell,
            String Quantity,
            String ReleaseYear,
            String Volume,
            String ImgURL,
            String Description) {
        // "NAME~BRANDNAME(string)~PRICE(INT)~Gender(string)~Smell(String)~Quantity(int)~ReleaseYear(smallint)~Volume(INT)~URL(Srtring)~Description",
        String sep = "~";
        String out = pName
                + sep
                + bName
                + sep
                + pPrice
                + sep
                + Gender
                + sep
                + Smell
                + sep
                + Quantity
                + sep
                + ReleaseYear
                + sep
                + Volume
                + sep
                + ImgURL
                + sep
                + Description;
        return out;
    }

    public static String covertIntergerToMoney(int integer) {
        String out = "";
        String StringDu = "";
        int du;
        while (integer / 1000 != 0) {
            du = integer % 1000;
            integer = integer / 1000;
            StringDu = String.valueOf(du);
            while (StringDu.length() < 3) {
                StringDu = "0" + StringDu;
            }
            out = "." + StringDu + out;
        }
        out = integer + "" + out;
        return out;
    }

    public static String convertMoneyToInteger(String moneyInput) {
        StringBuilder moneyInt = new StringBuilder(moneyInput);

        for (int i = 0; i < moneyInt.length(); i++) {
            if (moneyInt.charAt(i) == '.') {
                moneyInt.delete(i, i + 1);
            }
        }

        return moneyInt.toString();
    }

    /**
     * Gets the MD5 hash of a string.
     *
     * @param str The string to be hashed.
     * @return The MD5 hash of the string. {@code null} if an error occurs while
     *         hashing.
     */
    public static String convertToMD5Hash(String str) {

        MessageDigest md = null;
        String pwdMD5 = null;

        try {
            md = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        md.update(str.getBytes());
        byte[] digest = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        pwdMD5 = sb.toString();
        // System.out.println(pwdMD5);

        return pwdMD5;
    }

    public static Date convertStringToDate(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(str);

            System.out.println("Time:" + utilDate.getTime());

            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Convert a string to epoch milli.
     * 
     * @param str the {@code String} to be converted, must have format "yyyy-MM-dd".
     * @return Epoch milli of the string.
     */
    public static Long convertStringToEpochMilli(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(str);

            return utilDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
