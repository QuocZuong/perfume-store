package Exceptions;

public class NoProductVoucherAppliedException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public NoProductVoucherAppliedException() {
        super("Không có sản phẩm nào trong giỏ hàng được voucher áp dụng");
        queryString = "PVoucherInvalid";
    }

    public NoProductVoucherAppliedException(String message) {
        super(message);
        queryString = "PVoucherInvalid";
    }
}
