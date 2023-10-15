package Exceptions;

public class VoucherNotFoundException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public VoucherNotFoundException() {
        super("Không tìm thấy Voucher");
        queryString = "VouNF";
    }

    public VoucherNotFoundException(String message) {
        super(message);
        queryString = "VouNF";
    }
}
