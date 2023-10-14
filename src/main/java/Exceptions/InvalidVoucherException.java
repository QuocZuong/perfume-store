package Exceptions;

public class InvalidVoucherException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public InvalidVoucherException() {
        super("Voucher không hợp lệ");
        queryString = "VouInval";
    }

    public InvalidVoucherException(String message) {
        super(message);
        queryString = "VouInval";
    }
}
