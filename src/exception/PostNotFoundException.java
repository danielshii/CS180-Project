package exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException() {
        super("Post not found!");
    }
}
