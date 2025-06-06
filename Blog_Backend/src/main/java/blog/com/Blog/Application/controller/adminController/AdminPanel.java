package blog.com.Blog.Application.controller.adminController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import blog.com.Blog.Application.model.Blog;
import blog.com.Blog.Application.model.BlogUser;
import blog.com.Blog.Application.service.adminService.AdminService;


@RestController

@RequestMapping("/admin")
public class AdminPanel {

    @Autowired
    private AdminService adminService;

    private static final Logger logger = LoggerFactory.getLogger(AdminPanel.class);

    @GetMapping("/blogs") // http://localhost:8080/admin/blogs
    public ResponseEntity<?> getBlogs() {
        logger.info("GET /blogs called - fetching all blogs");
        try {
            List<Blog> getAllBlogs = adminService.getAllBlogsFromDB();
            logger.info("Fetched {} blogs successfully", getAllBlogs.size());
            return new ResponseEntity<>(getAllBlogs, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Exception in getBlogs(): {}", e.getMessage(), e);
            return new ResponseEntity<>("Something went wrong while fetching blogs.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/blogs/{id}") // http://localhost:8080/admin/blogs/1
    public ResponseEntity<?> getBlogsById(@PathVariable Long id) {
        logger.info("GET /blogs/{} called - fetching blog by ID", id);
        try {
            Optional<Blog> getBlogByID = adminService.getBlogByIdFromDB(id);
            if (getBlogByID.isPresent()) {
                logger.info("Blog with ID {} found", id);
                return new ResponseEntity<>(getBlogByID.get(), HttpStatus.OK);
            } else {
                logger.warn("Blog with ID {} not found", id);
                return new ResponseEntity<>("No blog found with the given ID", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Exception in getBlogsById() for ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>("Something went wrong while fetching the blog.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/adminList")
    public ResponseEntity<List<BlogUser>> getAdminData() {
        logger.info("GET /admin/adminList - Fetching admin users");
        try {
            List<BlogUser> blogAdmins = adminService.getAdminData();

            if (blogAdmins.isEmpty()) {
                logger.warn("No admin users found");
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            logger.info("Successfully fetched {} admin users", blogAdmins.size());
            return ResponseEntity.ok(blogAdmins); // HTTP 200 is more appropriate than ACCEPTED
        } catch (Exception e) {
            logger.error("Error while fetching admin users: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
   

    @PostMapping("/addBlog") // http://localhost:8080/admin/addBlog
    public ResponseEntity<String> addBlogToDB(@RequestPart Blog addBlogFromAdmin,
                                              @RequestPart MultipartFile imageFile) {
        logger.info("POST /addBlog called - Adding new blog with title '{}'", addBlogFromAdmin.getTitle());
        try {
            addBlogFromAdmin.setImageName(imageFile.getOriginalFilename());
            addBlogFromAdmin.setImageType(imageFile.getContentType());
            addBlogFromAdmin.setImageData(imageFile.getBytes());
            addBlogFromAdmin.setCreatedAt(new Date());

            adminService.saveBlog(addBlogFromAdmin);

            logger.info("Blog '{}' added successfully", addBlogFromAdmin.getTitle());
            return new ResponseEntity<>("Blog added successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Exception in addBlogToDB(): {}", e.getMessage(), e);
            return new ResponseEntity<>("Failed to add blog", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/blogs/{id}") // http://localhost:8080/admin/blogs/1
    public ResponseEntity<String> updateBlogToDB(
            @PathVariable Long id,
            @RequestPart(  value = "blogUpdateFromAdmin") Blog blogUpdateFromAdmin,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        logger.info("PUT /blogs/{} called - Updating blog", id);
        try {
            Blog existingBlog = adminService.getBlogFromDBUsingId(id);
            if (existingBlog == null) {
                logger.warn("Blog with ID {} not found for update", id);
                return new ResponseEntity<>("Blog not found", HttpStatus.NOT_FOUND);
            }

            existingBlog.setTitle(blogUpdateFromAdmin.getTitle());
            existingBlog.setContent(blogUpdateFromAdmin.getContent());
            existingBlog.setCategory(blogUpdateFromAdmin.getCategory());
            existingBlog.setCreatedAt(new Date());

            if (imageFile != null && !imageFile.isEmpty()) {
                logger.info("Updating image for blog ID {}", id);
                existingBlog.setImageName(imageFile.getOriginalFilename());
                existingBlog.setImageType(imageFile.getContentType());
                existingBlog.setImageData(imageFile.getBytes());
                existingBlog.setCreatedAt(new Date());
            }

            adminService.saveBlog(existingBlog);

            logger.info("Blog with ID {} updated successfully", id);
            return new ResponseEntity<>("Blog updated successfully!", HttpStatus.OK);

        } catch (Exception e) {
            logger.error("Exception in updateBlogToDB() for ID {}: {}", id, e.getMessage(), e);
            return new ResponseEntity<>("Failed to update blog", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/blogs/{id}")  // http://localhost:8080/admin/blogs/1
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
    
   
    @DeleteMapping("/adminUser/{id}")
    public ResponseEntity<String> removeAdminAccount(@PathVariable Long id) {
        logger.info("DELETE /adminUser/{} - Attempting to delete admin account", id);
        try {
            boolean deleteAdmin = adminService.deleteAdminById(id);
            if (deleteAdmin) {
                logger.info("Successfully deleted admin with ID {}", id);
                return ResponseEntity.ok("Admin account deleted successfully");
            } else {
                logger.warn("No admin found with ID {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                   .body("No admin account found with ID: " + id);
            }
        } catch (Exception e) {
            logger.error("Error deleting admin with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                               .body("Failed to delete admin account");
        }
    }

   
    
    
    
}
