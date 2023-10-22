package Exceptions;

public class OrderNotFoundException extends Exception implements Interfaces.Exceptions.ICustomException {
  private String queryString;

  public String getQueryString() {
    return queryString;
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }

  public OrderNotFoundException() {
    super("Không tìm thấy đơn hàng nào khớp với tìm kiếm của bạn.");
    queryString = "ONF";
  }

  public OrderNotFoundException(String message) {
    super(message);
    queryString = "ONF";
  }
}
