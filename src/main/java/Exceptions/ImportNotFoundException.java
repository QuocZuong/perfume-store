package Exceptions;

public class ImportNotFoundException extends Exception implements Interfaces.Exceptions.ICustomException {

    private String queryString;

    public String getQueryString() {
        return queryString;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ImportNotFoundException() {
        super("Không tìm thấy import");
        queryString = "ImpNF";
    }

    public ImportNotFoundException(String message) {
        super(message);
        queryString = "ImpNF";
    }
}
