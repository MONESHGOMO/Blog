package blog.com.Blog.Application.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "latestUpdate")
public class LatestUpdates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private  String contentOne;

    @NotBlank
    private String URL_1;

    private  String contentTwo;
    private String URL_2;


    private boolean isLive;


    public LatestUpdates(){}

    public LatestUpdates(Long id, String contentOne, String URL_1, String contentTwo, String URL_2,boolean isLive) {
        this.id = id;
        this.contentOne = contentOne;
        this.URL_1 = URL_1;
        this.contentTwo = contentTwo;
        this.URL_2 = URL_2;
        this.isLive=isLive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentOne() {
        return contentOne;
    }

    public void setContentOne(String contentOne) {
        this.contentOne = contentOne;
    }

    public String getURL_1() {
        return URL_1;
    }

    public void setURL_1(String URL_1) {
        this.URL_1 = URL_1;
    }

    public String getContentTwo() {
        return contentTwo;
    }

    public void setContentTwo(String contentTwo) {
        this.contentTwo = contentTwo;
    }

    public String getURL_2() {
        return URL_2;
    }

    public void setURL_2(String URL_2) {
        this.URL_2 = URL_2;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
