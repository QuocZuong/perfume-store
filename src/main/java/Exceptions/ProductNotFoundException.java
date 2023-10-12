package Exceptions;

public class ProductNotFoundException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public ProductNotFoundException() {
        super("Không tìm thấy sản phẩm");
        queryString = "PNF";
    }

    public ProductNotFoundException(String message) {
        super(message);
        queryString = "PNF";
    }
}
