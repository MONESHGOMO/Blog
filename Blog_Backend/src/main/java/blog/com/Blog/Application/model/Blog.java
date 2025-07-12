package blog.com.Blog.Application.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "blogs")
public class Blog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    @Column(length = 10000)
    private String content;

    @NotBlank(message = "Category is required")
    private String category;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date(); 

    @NotBlank(message = "Image URL is required")
    private String imageURL;

    public Blog() {
    }

    public Blog(String title, String content, String category, String imageURL) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.imageURL = imageURL;
        this.createdAt = new Date();
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
