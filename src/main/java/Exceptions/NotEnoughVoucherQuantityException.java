package Exceptions;

public class NotEnoughVoucherQuantityException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public NotEnoughVoucherQuantityException() {
        super("Số lượng Voucher đang sử dụng đã hết");
        queryString = "NEVQE";
    }

    public NotEnoughVoucherQuantityException(String message) {
        super(message);
        queryString = "NEVQE";
    }
}
