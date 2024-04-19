package Exceptions;

public class NotEnoughProductQuantityException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public NotEnoughProductQuantityException() {
        super("Số lượng sản phẩm trong kho không đủ để thanh toán");
        queryString = "NEPQE";
    }

    public NotEnoughProductQuantityException(String message) {
        super(message);
        queryString = "NEPQE";
    }
}
