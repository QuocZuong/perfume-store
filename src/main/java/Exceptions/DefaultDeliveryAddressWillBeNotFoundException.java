package Exceptions;

public class DefaultDeliveryAddressWillBeNotFoundException extends Exception
    implements Interfaces.Exceptions.ICustomException {
  private String queryString;

  public DefaultDeliveryAddressWillBeNotFoundException() {
    super("Bạn phải có một địa chỉ giao hàng mặc định!");
    queryString = "DdaWNF";
  }

  public DefaultDeliveryAddressWillBeNotFoundException(String message) {
    super(message);
    queryString = "DdaWNF";
  }

  public String getQueryString() {
    return queryString;
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}
