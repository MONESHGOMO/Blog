package blog.com.Blog.Application.controller.userController;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.com.Blog.Application.model.Blog;
import blog.com.Blog.Application.service.userService.BlogsData;

@RestController
@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "http://127.0.0.1:5501"
})
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

   @Autowired
    private BlogsData blogsData;

    @GetMapping("/blogs") // http://localhost:8080/users/blogs
    public ResponseEntity<?> getBlogs() {
        try {
            logger.info("Fetching all blogs for user...");
            List<Blog> getAllBlogs = blogsData.getAllBlogsFromDB();
            logger.info("Successfully fetched {} blogs.", getAllBlogs.size());
            return new ResponseEntity<>(getAllBlogs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while fetching blogs: {}", e.getMessage(), e);
            return new ResponseEntity<>("Something went wrong while fetching blogs.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @GetMapping("/blogs/{id}")
public ResponseEntity<?> getBlogsById(@PathVariable Long id) {
    try {
        // Add startup delay detection
        long startTime = System.currentTimeMillis();
        
        Optional<Blog> blog = blogsData.getAllBlogByIdFromDB(id);
        long processingTime = System.currentTimeMillis() - startTime;

        if (processingTime > 5000) { // If took more than 5 seconds
            logger.warn("Slow response detected - possible cold start");
        }
        
        return blog.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
                   
    } catch (Exception e) {
        logger.error("Database unavailable: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
               .body("Server starting up, please retry in 30 seconds");
    }
}
/*

    @GetMapping("/blogImage/{id}")
    public ResponseEntity<String> getBlogImageById(@PathVariable Long id) {
        Optional<Blog> blogOpt = blogsData.getAllBlogByIdFromDB(id);
        if (blogOpt.isPresent()) {
            return ResponseEntity.ok(blogOpt.get().getImageURL());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
        }
    }
 */

    @GetMapping("/blogImage/{id}")
    public ResponseEntity<String> getBlogImageById(@PathVariable Long id) {
        Optional<Blog> blogOpt = blogsData.getAllBlogByIdFromDB(id);
        return blogOpt.map(blog -> ResponseEntity.ok(blog.getImageURL())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found"));
    }

}
