package blog.com.Blog.Application.controller.adminController;

import java.util.List;
import java.util.Optional;

import blog.com.Blog.Application.model.Blogs;
import blog.com.Blog.Application.service.adminService.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminBlogs {

    @Autowired
    private AdminService adminService;

    private static final Logger logger = LoggerFactory.getLogger(AdminBlogs.class);

    @GetMapping("/blogs")
    public ResponseEntity<List<Blogs>> getBlogs() {
        logger.info("GET /blogs called - fetching all blogs");
        List<Blogs> allBlogs = adminService.getAllBlogsFromDB();
        return ResponseEntity.ok(allBlogs);
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blogs> getBlogById(@PathVariable Long id) {
        logger.info("GET /blogs/{} called - fetching blog by ID", id);
        Optional<Blogs> blog = adminService.getBlogByIdFromDB(id);

        if (blog.isEmpty()) {
            logger.warn("Blog with ID {} not found", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(blog.get());
    }

    @PostMapping("/addBlog")
    public ResponseEntity<String> addBlogToDB(@RequestBody Blogs newBlog) {
        logger.info("POST /addBlog called - Adding new blog '{}'", newBlog.getTitle());
        adminService.saveBlog(newBlog);
        logger.info("Blog '{}' added successfully", newBlog.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body("Blog added successfully!");
    }

    @PutMapping("/blogs/{id}")
    public ResponseEntity<String> updateBlogToDB(@PathVariable Long id, @RequestBody Blogs blogUpdate) {
        logger.info("PUT /blogs/{} called - Updating blog", id);

        Blogs existingBlog = adminService.getBlogFromDBUsingId(id);
        if (existingBlog == null) {
            logger.warn("Blog with ID {} not found for update", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
        }

        existingBlog.setTitle(blogUpdate.getTitle());
        existingBlog.setContent(blogUpdate.getContent());
        existingBlog.setCategory(blogUpdate.getCategory());
        existingBlog.setImageURL(blogUpdate.getImageURL());

        adminService.saveBlog(existingBlog);

        logger.info("Blog with ID {} updated successfully", id);
        return ResponseEntity.ok("Blog updated successfully!");
    }

    @DeleteMapping("/blogs/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id) {
        logger.info("DELETE /blogs/{} called - Deleting blog", id);

        boolean deleted = adminService.deleteBlog(id);
        if (deleted) {
            logger.info("Blog with ID {} deleted successfully", id);
            return ResponseEntity.ok("Blog deleted successfully!");
        } else {
            logger.warn("Blog with ID {} not found to delete", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
        }
    }
}
