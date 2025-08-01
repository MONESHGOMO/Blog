package blog.com.Blog.Application.Exceptions;

public class BlogNotFoundException extends RuntimeException {
    public BlogNotFoundException() {
        super("Blog not found.");
    }
}
