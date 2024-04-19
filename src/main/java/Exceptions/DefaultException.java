package Exceptions;

public class DefaultException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public DefaultException() {
        super("Server đã gặp sự cố! Thử lại sau");
        queryString = "Default";
    }

    public DefaultException(String message) {
        super(message);
        queryString = "Default";
    }
}
