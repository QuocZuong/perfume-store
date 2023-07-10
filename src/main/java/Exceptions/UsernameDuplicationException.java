package Exceptions;

public class UsernameDuplicationException extends Exception {

  public UsernameDuplicationException() {
    super("User's username cannot be the same.");
  }

  public UsernameDuplicationException(String message) {
    super(message);
  }
}