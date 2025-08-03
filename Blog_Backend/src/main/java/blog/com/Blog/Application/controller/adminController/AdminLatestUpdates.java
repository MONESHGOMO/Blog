package blog.com.Blog.Application.controller.adminController;

import blog.com.Blog.Application.DTO.LatestUpdatesDTO;
import blog.com.Blog.Application.model.LatestUpdates;
import blog.com.Blog.Application.service.latestUpdates.LatestUpdatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminLatestUpdates {

    private static final Logger logger = LoggerFactory.getLogger(AdminLatestUpdates.class);

    @Autowired
    private LatestUpdatesService latestUpdatesService;

    @GetMapping("/latestUpdates")
    public ResponseEntity<List<LatestUpdates>> getAllLatestUpdate() {
        logger.info("Fetching all latest updates (non-sensitive operation)");
        List<LatestUpdates> updates = latestUpdatesService.getAllLatestUpdate();
        logger.info("Fetched {} latest updates", updates.size());
        return new ResponseEntity<>(updates, HttpStatus.OK);
    }

    @PostMapping("/addLatestUpdates")
    public ResponseEntity<String> addLatestUpdate(@RequestBody LatestUpdatesDTO latestUpdatesDTO) {
        logger.info("Request received to add a new latest update");
        boolean addLatestUpdate = latestUpdatesService.addLatestUpdate(latestUpdatesDTO);
        if (addLatestUpdate) {
            logger.info("Latest update added successfully");
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } else {
            logger.warn("Failed to store the latest update");
            return new ResponseEntity<>("Unable to Store", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/latestUpdates/{id}")
    public ResponseEntity<String> deleteTheLatestUpdateByID(@PathVariable Long id) {
        logger.info("Request received to delete latest update with ID: {}", id);
        boolean deleted = latestUpdatesService.deleteLatestUpdateByID(id);
        if (deleted) {
            logger.info("Latest update with ID {} deleted successfully", id);
            return ResponseEntity.ok("Deleted successfully.");
        } else {
            logger.warn("No latest update del  found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No record found with ID: " + id);
        }
    }
    @PatchMapping("/latestUpdates/{id}/status")
    public ResponseEntity<String> updateLiveStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> status) {
        logger.info("Request received to update live status for ID: {} to {}", id, status.get("live"));
        boolean updated = latestUpdatesService.updateLiveStatus(id, status.get("live"));
        if (updated) {
            logger.info("Live status updated successfully for ID: {}", id);
            return ResponseEntity.ok("Status updated successfully.");
        } else {
            logger.warn("No latest update found with ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No record found with ID: " + id);
        }
    }
}
