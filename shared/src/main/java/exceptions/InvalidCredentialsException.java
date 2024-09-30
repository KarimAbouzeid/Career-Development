package exceptions;


public class InvalidCredentialsException extends IllegalArgumentException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
