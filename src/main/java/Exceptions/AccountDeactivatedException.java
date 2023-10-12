package Exceptions;

public class AccountDeactivatedException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public AccountDeactivatedException() {
        super("Tài khoản của bạn đã bị vô hiệu hóa");
        queryString = "AccD";
    }

    public AccountDeactivatedException(String message) {
        super(message);
        queryString = "AccD";
    }

}
