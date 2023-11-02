package Exceptions;

public class OperationDeleteBrandFailedCauseOfExistedProduct extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    @Override
    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public OperationDeleteBrandFailedCauseOfExistedProduct() {
        super("Không thể xóa thương hiệu vì thương hiệu này còn sản phẩm trong hệ thống");
        queryString = "OPDBFCOEP";
    }

    public OperationDeleteBrandFailedCauseOfExistedProduct(String message) {
        super(message);
        queryString = "OPDBFCOEP";
    }

}
