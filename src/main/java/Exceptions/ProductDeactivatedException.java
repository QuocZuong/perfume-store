package Exceptions;

public class ProductDeactivatedException extends Exception {

  public ProductDeactivatedException() {
    super("Product is deactivated.");
  }

  public ProductDeactivatedException(String message) {
    super(message);
  }
}