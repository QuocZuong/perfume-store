package Exceptions;

public class AccountDeactivatedException extends Exception {

  public AccountDeactivatedException() {
    super("User's account is deactivated.");
  }

  public AccountDeactivatedException(String message) {
    super(message);
  }
}