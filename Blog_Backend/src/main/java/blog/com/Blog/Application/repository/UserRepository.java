//package blog.com.Blog.Application.repository;
//
//
//import blog.com.Blog.Application.model.BlogUser;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//
//@Repository
//public interface UserRepository extends JpaRepository<BlogUser, Long> {
//
//
//    @Query(value = "SELECT * FROM blog_users WHERE email = :email", nativeQuery = true)
//    Optional<BlogUser> findByEmail(@Param("email") String email);
//
//    Boolean existsByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email);
//
//
//    @Query(value = "SELECT roles from blog_users WHERE id = :id", nativeQuery = true)
//    String[] getUserRoleById(@Param("id") Long id);
//    
//    
//    
//    @Query(value = "SELECT * FROM blog_users WHERE role = :roleName", nativeQuery = true)
//	List<BlogUser> getAdminDataFromDB(@Param("roleName") String roleName);
//
//
//
//
//}


package blog.com.Blog.Application.repository;

import blog.com.Blog.Application.model.BlogUser;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<BlogUser, Long> {

    @Query(value = "SELECT * FROM blog_users WHERE email = :email", nativeQuery = true)
    Optional<BlogUser> findByEmail(@Param("email") String email);

    boolean existsByEmail(String email);

    @Query(value = "SELECT role FROM blog_users WHERE id = :id", nativeQuery = true)
    String[] getUserRoleById(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM blog_users WHERE role = :roleName", nativeQuery = true)
    List<BlogUser> getAdminDataFromDB(@Param("roleName") String roleName);
}