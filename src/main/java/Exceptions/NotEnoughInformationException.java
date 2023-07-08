package Exceptions;

public class NotEnoughInformationException extends Exception {
  
  public NotEnoughInformationException() {
    super("Not enough information that is required.");
  }

  public NotEnoughInformationException(String message) {
    super(message);
  }
}