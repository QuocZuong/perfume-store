/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;

import Exceptions.*;
import Interfaces.Exceptions.ICustomException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Acer
 */
public class ExceptionUtils {

    private static Map<String, ICustomException> exceptionMap;

    public enum ExceptionType {
        //User info
        WrongPasswordException,
        AccountNotFoundException,
        AccountDeactivatedException,
        PhoneNumberDuplicationException,
        UsernameDuplicationException,
        EmailDuplicationException,
        //Employee info
        CitizenIDDuplicationException,
        //Brand
        BrandNotFoundException,
        //Product
        ProductNotFoundException,
        ProductDeactivatedException,
        //Other
        NotEnoughInformationException,
        OperationAddFailedException
    }

    static {
        exceptionMap = new HashMap<String, ICustomException>() {
            {
                //User info
                put(ExceptionType.WrongPasswordException.toString(), new WrongPasswordException());
                put(ExceptionType.AccountNotFoundException.toString(), new AccountNotFoundException());
                put(ExceptionType.AccountDeactivatedException.toString(), new AccountDeactivatedException());
                put(ExceptionType.PhoneNumberDuplicationException.toString(), new PhoneNumberDuplicationException());
                put(ExceptionType.UsernameDuplicationException.toString(), new UsernameDuplicationException());
                put(ExceptionType.EmailDuplicationException.toString(), new EmailDuplicationException());
                //Employee info
                put(ExceptionType.CitizenIDDuplicationException.toString(), new CitizenIDDuplicationException());
                //Brand
                put(ExceptionType.BrandNotFoundException.toString(), new BrandNotFoundException());
                //Product
                put(ExceptionType.ProductNotFoundException.toString(), new ProductNotFoundException());
                put(ExceptionType.ProductDeactivatedException.toString(), new ProductDeactivatedException());
                //Other
                put(ExceptionType.NotEnoughInformationException.toString(), new NotEnoughInformationException());
                put(ExceptionType.OperationAddFailedException.toString(), new OperationAddFailedException());
            }
        };

    }

    public static String generateExceptionQueryString(HttpServletRequest request) {
        if (request.getAttribute("exceptionType") == null) {
            return "";
        }
        String exceptionKey = (String) request.getAttribute("exceptionType");
        String exception = "?err" + exceptionMap.getOrDefault(exceptionKey, new DefaultException()).getQueryString() + "=true";
        return exception;
    }

    //Query String can be "errPNF=true&&otherAttribute=..."
    private static String getExceptionNameByQueryString(String queryString) {
        if (queryString == null) {
            return "";
        }
        //Extract the exception attribute. Example: PNF instead of errPNF
        String attribute = extractExceptionQueryString(queryString);
        if (attribute == null) {
            return null;
        }

        //Iterate through a map entry to compare query string
        for (Map.Entry<String, ICustomException> entry : exceptionMap.entrySet()) {
            if (entry.getValue().getQueryString().equals(attribute)) {
                return entry.getKey();
            }
        }
        return "";
    }

    public static String extractExceptionQueryString(String queryString) {
        if (queryString == null) {
            return null;
        }
        String[] attributes = queryString.split("=");
        if (attributes.length == 0) {
            return "";
        }
        String attribute = attributes[0];
        attribute = attribute.substring(3, attribute.length());
        return attribute;
    }

    public static String getMessageFromExceptionQueryString(String queryString) {
        String exceptionKey = getExceptionNameByQueryString(queryString);
        System.out.println("Exception key:" + exceptionKey);
        ICustomException exception = exceptionMap.getOrDefault(exceptionKey, new DefaultException());
        return exception.getMessage();
    }

// =================================== VALIDATION SECTION =================================
    public static boolean isWebsiteError(String queryString) {
        return !getExceptionNameByQueryString(queryString).isEmpty();
    }

//                    put(ExceptionType.WrongPasswordException.toString(), new WrongPasswordException());
//                put(ExceptionType.AccountNotFoundException.toString(), new AccountNotFoundException());
//                put(ExceptionType.AccountDeactivatedException.toString(), new AccountDeactivatedException());
//                put(ExceptionType.PhoneNumberDuplicationException.toString(), new PhoneNumberDuplicationException());
//                put(ExceptionType.UsernameDuplicationException.toString(), new UsernameDuplicationException());
//                put(ExceptionType.EmailDuplicationException.toString(), new EmailDuplicationException());
//                //Employee info
//                put(ExceptionType.CitizenIDDuplicationException.toString(), new CitizenIDDuplicationException());
//                //Brand
//                put(ExceptionType.BrandNotFoundException.toString(), new BrandNotFoundException());
//                //Product
//                put(ExceptionType.ProductNotFoundException.toString(), new ProductNotFoundException());
//                put(ExceptionType.ProductDeactivatedException.toString(), new ProductDeactivatedException());
//                //Other
//                put(ExceptionType.NotEnoughInformationException.toString(), new NotEnoughInformationException());
//                put(ExceptionType.OperationAddFailedException.toString(), new OperationAddFailedException());
    public static boolean isWrongPassword(String queryString) {
        return getExceptionNameByQueryString(queryString).equals(ExceptionType.WrongPasswordException.toString());
    }

    public static boolean isAccountNotFound(String queryString) {
        return getExceptionNameByQueryString(queryString).equals(ExceptionType.AccountNotFoundException.toString());
    }

    public static boolean isAccountDeactivated(String queryString) {
        return getExceptionNameByQueryString(queryString).equals(ExceptionType.AccountDeactivatedException.toString());
    }

    public static boolean isPhoneNumberDuplication(String queryString) {
        return getExceptionNameByQueryString(queryString).equals(ExceptionType.PhoneNumberDuplicationException.toString());
    }

    public static boolean isUsernameDuplication(String queryString) {
        return getExceptionNameByQueryString(queryString).equals(ExceptionType.UsernameDuplicationException.toString());
    }

    public static boolean isEmailDuplication(String queryString) {
        return getExceptionNameByQueryString(queryString).equals(ExceptionType.EmailDuplicationException.toString());
    }

    public static boolean isCitizenIDDuplication(String queryString) {
        return getExceptionNameByQueryString(queryString).equals(ExceptionType.CitizenIDDuplicationException.toString());
    }

    public static boolean isBrandNotFound(String queryString) {
        return getExceptionNameByQueryString(queryString).equals(ExceptionType.BrandNotFoundException.toString());
    }

    public static boolean isProductNotFound(String queryString) {
        return getExceptionNameByQueryString(queryString).equals(ExceptionType.ProductNotFoundException.toString());
    }

    public static boolean isProductDeactivated(String queryString) {
        return getExceptionNameByQueryString(queryString).equals(ExceptionType.ProductDeactivatedException.toString());
    }

    public static boolean isNotEnoughInformation(String queryString) {
        return getExceptionNameByQueryString(queryString).equals(ExceptionType.NotEnoughInformationException.toString());
    }

    public static boolean isOperationAddFailed(String queryString) {
        return getExceptionNameByQueryString(queryString).equals(ExceptionType.OperationAddFailedException.toString());
    }
}