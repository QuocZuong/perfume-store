package Exceptions;

public class EmailDuplicationException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public EmailDuplicationException() {
        super("Email đã tồn tại");
        queryString = "EmailDup";
    }

    public EmailDuplicationException(String message) {
        super(message);
        queryString = "EmailDup";
    }
}
