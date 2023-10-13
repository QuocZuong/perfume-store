package Exceptions;

public class NotEnoughInformationException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public NotEnoughInformationException() {
        super("Không đủ thông tin! Hãy thử lại");
        queryString = "NEInfo";
    }

    public NotEnoughInformationException(String message) {
        super(message);
        queryString = "NEInfo";
    }
}
