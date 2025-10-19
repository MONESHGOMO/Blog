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

    private static final Logger logger = LoggerFactory.getLogger(AdminData.class);

    @GetMapping("/adminList")
    public ResponseEntity<List<BlogUser>> getAdminData() {
        logger.info("GET /admin/adminList - Fetching admin users");

        List<BlogUser> blogAdmins = adminService.getAdminData();

        if (blogAdmins.isEmpty()) {
            logger.warn("No admin users found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        logger.info("Fetched {} admin users successfully", blogAdmins.size());
        return ResponseEntity.ok(blogAdmins);
    }

    @DeleteMapping("/adminUser/{id}")
    public ResponseEntity<String> removeAdminAccount(@PathVariable Long id) {
        logger.info("DELETE /adminUser/{} - Deleting admin account", id);

        boolean deleted = adminService.deleteAdminById(id);

        if (deleted) {
            logger.info("Admin with ID {} deleted successfully", id);
            return ResponseEntity.ok("Admin account deleted successfully");
        } else {
            logger.warn("No admin found with ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No admin account found with ID: " + id);
        }
    }
}
