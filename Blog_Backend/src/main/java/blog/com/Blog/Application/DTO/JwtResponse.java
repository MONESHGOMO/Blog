package blog.com.Blog.Application.DTO;

public class JwtResponse {

    private String token;
    private String userName;

    private String role;
    public JwtResponse(String token, String role,String userName) {
        this.token = token;
        this.role = role;
        this.userName=userName;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

