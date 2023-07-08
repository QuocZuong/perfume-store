package Exceptions;

public class EmailDuplicationException extends Exception {

  public EmailDuplicationException() {
    super("User's email cannot be the same.");
  }

  public EmailDuplicationException(String message) {
    super(message);
  }
}