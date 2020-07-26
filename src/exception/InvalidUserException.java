package exception;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException() {
        super("Username or password is incorrect!");
    }
}
