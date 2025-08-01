package blog.com.Blog.Application.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LatestUpdatesDTO {

    private  String contentOne;

    @JsonProperty("URL_1")
    private String url1;

    private String contentTwo;

    @JsonProperty("URL_2")
    private String url2;


    public LatestUpdatesDTO() {}
    public LatestUpdatesDTO(String contentOne, String URL_1, String contentTwo, String URL_2) {
        this.contentOne = contentOne;
        this.url1 = URL_1;
        this.contentTwo = contentTwo;
        this.url2 = URL_2;
    }


    public LatestUpdatesDTO(String contentOne, String URL_1) {
        this.contentOne = contentOne;
        this.url1 = URL_1;
    }

    public String getContentOne() {
        return contentOne;
    }

    public void setContentOne(String contentOne) {
        this.contentOne = contentOne;
    }

    public String getURL_1() {
        return url1;
    }

    public void setURL_1(String URL_1) {
        this.url1 = URL_1;
    }

    public String getContentTwo() {
        return contentTwo;
    }

    public void setContentTwo(String contentTwo) {
        this.contentTwo = contentTwo;
    }

    public String getURL_2() {
        return url2;
    }

    public void setURL_2(String URL_2) {
        this.url2 = URL_2;
    }
}
