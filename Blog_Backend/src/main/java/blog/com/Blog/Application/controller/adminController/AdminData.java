package blog.com.Blog.Application.controller.adminController;

import blog.com.Blog.Application.model.BlogUser;
import blog.com.Blog.Application.service.adminService.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/admin")
public class AdminData {


    @Autowired
    private AdminService adminService;

    private static final Logger logger = LoggerFactory.getLogger(AdminBlogs.class);


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
