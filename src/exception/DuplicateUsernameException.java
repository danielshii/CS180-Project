package exception;

public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(String username) {
        super(String.format("Username %s already exists in the system!", username));
    }
}
