package Exceptions;

public class PhoneNumberDuplicationException extends Exception {

  public PhoneNumberDuplicationException() {
    super("User's phone number cannot be the same.");
  }

  public PhoneNumberDuplicationException(String message) {
    super(message);
  }
}