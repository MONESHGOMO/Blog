package blog.com.Blog.Application.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "email_subscribers")
public class EmailSubscribers {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Date dateOFSubscribe;

    public EmailSubscribers(){}

    public EmailSubscribers(Long id, String email, Date dateOFSubscribe) {
        this.id = id;
        this.email = email;
        this.dateOFSubscribe = dateOFSubscribe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOFSubscribe() {
        return dateOFSubscribe;
    }

    public void setDateOFSubscribe(Date dateOFSubscribe) {
        this.dateOFSubscribe = dateOFSubscribe;
    }
}
