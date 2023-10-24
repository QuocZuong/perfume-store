package Exceptions;

public class EmailDoesntExist extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public EmailDoesntExist() {
        super("Email không tồn tại");
        queryString = "EmailDoesntExist";
    }

    public EmailDoesntExist(String message) {
        super(message);
        queryString = "EmailDoesntExist";
    }
}
