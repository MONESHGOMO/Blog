package blog.com.Blog.Application.Exceptions;

public class SubscribersException extends RuntimeException{

    public SubscribersException(){
        super("Email Already Exists ");
    }
}
