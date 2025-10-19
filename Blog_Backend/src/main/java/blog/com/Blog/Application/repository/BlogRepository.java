

package blog.com.Blog.Application.repository;

import blog.com.Blog.Application.model.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blogs, Long> {

    @Query(value = "SELECT * FROM blogs WHERE id = :id", nativeQuery = true)
    Blogs getBlogFromDBUsingId(@Param("id") Long id);

    @Query(value = "SELECT * FROM blogs ORDER BY id DESC LIMIT 1", nativeQuery = true)
    List<Blogs> getLatestBlogNative();

    @Query(value = "SELECT category FROM blogs ",nativeQuery = true)
    List<String> getListOfCategory();

    @Query(value = "SELECT * FROM blogs WHERE category = :category", nativeQuery = true)
    List<Blogs> getBlogByCategory(@Param("category") String category);


    @Query(value = "SELECT * FROM blogs ORDER BY id DESC",nativeQuery = true)
    List<Blogs> getAllBlogs();


}