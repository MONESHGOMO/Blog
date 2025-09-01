package blog.com.Blog.Application.repository;

import blog.com.Blog.Application.model.EmailSubscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribersRepository extends JpaRepository<EmailSubscribers, Long> {

    @Query(value = "SELECT COUNT(*) FROM email_subscribers WHERE email = :email", nativeQuery = true)
    Long countByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM email_subscribers", nativeQuery = true)
    List<EmailSubscribers> findAll();

}
