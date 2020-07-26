package exception;

public class UserNotAuthorizedException extends RuntimeException {
    public UserNotAuthorizedException() {
        super("User does not have permission to edit this post");
    }
}
