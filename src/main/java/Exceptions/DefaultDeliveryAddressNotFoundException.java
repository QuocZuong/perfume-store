package Exceptions;

public class DefaultDeliveryAddressNotFoundException extends Exception
    implements Interfaces.Exceptions.ICustomException {
  private String queryString;

  public DefaultDeliveryAddressNotFoundException() {
    super("Không tìm thấy địa chỉ giao hàng mặc định!");
    queryString = "DdaNF";
  }

  public DefaultDeliveryAddressNotFoundException(String message) {
    super(message);
    queryString = "DdaNF";
  }

  public String getQueryString() {
    return queryString;
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}