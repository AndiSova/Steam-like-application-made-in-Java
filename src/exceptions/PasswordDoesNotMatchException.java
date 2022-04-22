package exceptions;

public class PasswordDoesNotMatchException extends Throwable {
    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}
