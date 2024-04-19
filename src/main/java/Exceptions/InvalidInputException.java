package Exceptions;

public class InvalidInputException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public InvalidInputException() {
        super("Input sai format, hãy thử lại");
        queryString = "InvalidInput";
    }

    public InvalidInputException(String message) {
        super(message);
        queryString = "InvalidInput";
    }
}
