package Exceptions;

public class VoucherCodeDuplication extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public VoucherCodeDuplication() {
        super("Voucher code đã tồn tại");
        queryString = "VoucherCodeDup";
    }

    public VoucherCodeDuplication(String message) {
        super(message);
        queryString = "VoucherCodeDup";
    }
}
