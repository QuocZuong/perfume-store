package Exceptions;

public class CitizenIDDuplicationException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public CitizenIDDuplicationException() {
        super("CCCD đã tồn tại");
        queryString = "CIDDup";
    }

    public CitizenIDDuplicationException(String message) {
        super(message);
        queryString = "CIDDup";
    }
}
