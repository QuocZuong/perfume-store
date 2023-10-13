package Exceptions;

public class ProductDeactivatedException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public ProductDeactivatedException() {
        super("Sản phẩm đã bị vô hiệu hóa");
        queryString = "PD";
    }

    public ProductDeactivatedException(String message) {
        super(message);
        queryString = "PD";
    }
}
