package blog.com.Blog.Application.controller.userController;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import blog.com.Blog.Application.model.Blog;
import blog.com.Blog.Application.service.userService.BlogsData;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

   @Autowired
    private BlogsData blogsData;

    @GetMapping("/blogs") // Example: http://localhost:8080/users/blogs?page=0&size=5
    public ResponseEntity<?> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        try {
            logger.info("Fetching blogs - page: {}, size: {}", page, size);

            List<Blog> allBlogs = blogsData.getAllBlogsFromDB();

            int total = allBlogs.size();
            int fromIndex = page * size;
            int toIndex = Math.min(fromIndex + size, total);

            if (fromIndex >= total) {
                return new ResponseEntity<>("Page index out of range.", HttpStatus.BAD_REQUEST);
            }

            List<Blog> pagedBlogs = allBlogs.subList(fromIndex, toIndex);
            logger.info("Successfully fetched {} blogs (paged).", pagedBlogs.size());

            return new ResponseEntity<>(pagedBlogs, HttpStatus.OK);

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

        if (processingTime > 5000) {
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





}
