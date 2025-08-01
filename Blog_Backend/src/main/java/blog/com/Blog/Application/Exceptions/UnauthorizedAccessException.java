package blog.com.Blog.Application.Exceptions;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {
        super("Forbidden: You don't have access  gomo.");
    }
}