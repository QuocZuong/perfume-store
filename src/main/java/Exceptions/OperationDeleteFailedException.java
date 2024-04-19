package Exceptions;

public class OperationDeleteFailedException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public OperationDeleteFailedException() {
        super("Xoá thất bại");
        queryString = "ODFE";
    }

    public OperationDeleteFailedException(String message) {
        super(message);
        queryString = "ODFE";
    }
}
