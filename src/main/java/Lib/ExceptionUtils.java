/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lib;

import Exceptions.AccountDeactivatedException;
import Exceptions.AccountNotFoundException;
import Exceptions.DefaultException;
import Exceptions.EmailDuplicationException;
import Exceptions.NotEnoughInformationException;
import Exceptions.OperationAddFailedException;
import Exceptions.PhoneNumberDuplicationException;
import Exceptions.ProductNotFoundException;
import Exceptions.UsernameDuplicationException;
import Exceptions.WrongPasswordException;
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

    public ExceptionUtils() {
        exceptionMap = new HashMap<String, ICustomException>() {
            {
                //Account
                put("WrongPasswordException", new WrongPasswordException());
                put("AccountNotFoundException", new AccountNotFoundException());
                put("AccountDeactivatedException", new AccountDeactivatedException());
                put("PhoneNumberDuplicationException", new PhoneNumberDuplicationException());
                put("UsernameDuplicationException", new UsernameDuplicationException());
                put("EmailDuplicationException", new EmailDuplicationException());
                //Product
                put("ProductNotFoundException", new ProductNotFoundException());
                //Other
                put("NotEnoughInformationException", new NotEnoughInformationException());
                put("OperationAddFailedException", new OperationAddFailedException());
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

    private static String getExceptionNameByQueryString(String queryString) {
        //Iterate through a map entry to compare query string
        for (Map.Entry<String, ICustomException> entry : exceptionMap.entrySet()) {
            if (entry.getValue().getQueryString().equals(queryString)) {
                return entry.getKey();
            }
        }
        return "";
    }

    public static String getMessageFromExceptionQueryString(String queryString) {
        String exceptionKey = getExceptionNameByQueryString(queryString);
        ICustomException exception = exceptionMap.getOrDefault(exceptionKey, new DefaultException());
        return exception.getMessage();
    }

}
