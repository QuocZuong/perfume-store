package Exceptions;

public class WrongPasswordException extends Exception {

  public WrongPasswordException() {
    super("Incorrect password.");
  }

  public WrongPasswordException(String message) {
    super(message);
  }
}