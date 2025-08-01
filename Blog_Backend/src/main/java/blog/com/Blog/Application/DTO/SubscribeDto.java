package blog.com.Blog.Application.DTO;


public class SubscribeDto {

    private String email;

    SubscribeDto(){}

    SubscribeDto(String email){
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
