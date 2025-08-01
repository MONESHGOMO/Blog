package blog.com.Blog.Application.repository;


import blog.com.Blog.Application.model.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactUsRepository  extends JpaRepository<ContactUs,Long> {

}
