package Exceptions;

public class BrandNotFoundException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }

    public BrandNotFoundException() {
        super("Thương hiệu không tìm thấy");
        queryString = "BrandNF";
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    public BrandNotFoundException(String message) {
        super(message);
        queryString = "BrandNF";
    }
}
