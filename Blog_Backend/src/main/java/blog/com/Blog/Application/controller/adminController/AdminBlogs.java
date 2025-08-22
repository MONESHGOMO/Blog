package blog.com.Blog.Application.controller.adminController;

import java.util.List;
import java.util.Optional;

import blog.com.Blog.Application.model.Blogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import blog.com.Blog.Application.service.adminService.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminBlogs {

    @Autowired
    private AdminService adminService;
    private static final Logger logger = LoggerFactory.getLogger(AdminBlogs.class);

    @GetMapping("/blogs")
    public ResponseEntity<List<blog.com.Blog.Application.model.Blogs>> getBlogs() {
        logger.info("GET /blogs called - fetching all blogs");
            List<blog.com.Blog.Application.model.Blogs> getAllBlogs = adminService.getAllBlogsFromDB();
            return new ResponseEntity<>(getAllBlogs, HttpStatus.OK);
    }

    @GetMapping("/blogs/{id}") // http://localhost:8080/admin/blogs/1
    public ResponseEntity<?> getBlogsById(@PathVariable Long id) {
        logger.info("GET /blogs/{} called - fetching blog by ID", id);

            Optional<Blogs> getBlogByID = adminService.getBlogByIdFromDB(id);
                return new ResponseEntity<>(getBlogByID.get(), HttpStatus.OK);
    }



    @PostMapping("/addBlog") // http://localhost:8080/admin/addBlog
    public ResponseEntity<String> addBlogToDB(@RequestBody Blogs addBlogFromAdmin) {
    logger.info("POST /addBlog called - Adding new blog with title '{}'", addBlogFromAdmin.getTitle());
        adminService.saveBlog(addBlogFromAdmin);
        logger.info("Blog '{}' added successfully", addBlogFromAdmin.getTitle());
        return new ResponseEntity<>("Blog added successfully!", HttpStatus.CREATED);
}



		@PutMapping("/blogs/{id}") // http://localhost:8080/admin/blogs/1
		public ResponseEntity<String> updateBlogToDB(@PathVariable Long id,@RequestBody Blogs blogUpdateFromAdmin) {

		    logger.info("PUT /blogs/{} called - Updating blog", id);
		    try {
		        blog.com.Blog.Application.model.Blogs existingBlog = adminService.getBlogFromDBUsingId(id);
		        if (existingBlog == null) {
		            logger.warn("Blog with ID {} not found for update", id);
		            return new ResponseEntity<>("Blog not found", HttpStatus.NOT_FOUND);
                }

        existingBlog.setTitle(blogUpdateFromAdmin.getTitle());
        existingBlog.setContent(blogUpdateFromAdmin.getContent());
        existingBlog.setCategory(blogUpdateFromAdmin.getCategory());
        existingBlog.setImageURL(blogUpdateFromAdmin.getImageURL());

        adminService.saveBlog(existingBlog);

        logger.info("Blog with ID {} updated successfully", id);
        return new ResponseEntity<>("Blog updated successfully!", HttpStatus.OK);

    } catch (Exception e) {
        logger.error("Exception in updateBlogToDB() for ID {}: {}", id, e.getMessage(), e);
        return new ResponseEntity<>("Failed to update blog", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

    @DeleteMapping("/blogs/{id}") // http://localhost:8080/admin/blogs/1
    public ResponseEntity<String> deleteBlog(@PathVariable Long id) {
        logger.info("DELETE /blogs/{} called - Deleting blog", id);
        try {
            boolean deleted = adminService.deleteBlog(id);
            if (deleted) {
                logger.info("Blog with ID {} deleted successfully", id);
                return ResponseEntity.ok("Blog deleted successfully!");
            } else {
                logger.warn("Blog with ID {} not found to delete", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
            }
        } catch (Exception e) {
            logger.error("Exception in deleteBlog() for ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete blog");
        }
    }



}
