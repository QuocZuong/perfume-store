package Exceptions;

public class PhoneNumberDuplicationException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public PhoneNumberDuplicationException() {
        super("Số điện thoại đã tồn tại");
        queryString = "PhoneDup";
    }

    public PhoneNumberDuplicationException(String message) {
        super(message);
        queryString = "PhoneDup";
    }
}
