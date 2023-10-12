package Exceptions;

public class UsernameDuplicationException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public UsernameDuplicationException() {
        super("Tên đăng nhập đã tồn tại");
        queryString = "UsernameDup";
    }

    public UsernameDuplicationException(String message) {
        super(message);
        queryString = "UsernameDup";
    }
}
