package Exceptions;

public class CitizenIDDuplicationException extends Exception {

    public CitizenIDDuplicationException() {
        super("User's citizen id is duplicated.");
    }

    public CitizenIDDuplicationException(String message) {
        super(message);
    }
}