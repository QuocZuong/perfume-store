package Exceptions;

public class WrongPasswordException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public WrongPasswordException() {
        super("Sai tài khoản hoặc mật khẩu");
        queryString = "AccNF";
    }

    public WrongPasswordException(String message) {
        super(message);
        queryString = "AccNF";
    }
}
