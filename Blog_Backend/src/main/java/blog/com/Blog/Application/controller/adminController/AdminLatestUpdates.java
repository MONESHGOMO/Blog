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
        logger.info("GET /admin/latestUpdates - Fetching all latest updates");
        List<LatestUpdates> updates = latestUpdatesService.getAllLatestUpdate();

        if (updates.isEmpty()) {
            logger.warn("No latest updates found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        logger.info("Fetched {} latest updates", updates.size());
        return ResponseEntity.ok(updates);
    }

    @PostMapping("/addLatestUpdates")
    public ResponseEntity<String> addLatestUpdate(@RequestBody LatestUpdatesDTO latestUpdatesDTO) {
        logger.info("POST /admin/addLatestUpdates - Adding a new latest update");

        boolean added = latestUpdatesService.addLatestUpdate(latestUpdatesDTO);
        return added
                ? ResponseEntity.status(HttpStatus.CREATED).body("Success")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to store");
    }

    @DeleteMapping("/latestUpdates/{id}")
    public ResponseEntity<String> deleteTheLatestUpdateByID(@PathVariable Long id) {
        logger.info("DELETE /admin/latestUpdates/{} - Deleting update", id);

        boolean deleted = latestUpdatesService.deleteLatestUpdateByID(id);
        return deleted
                ? ResponseEntity.ok("Deleted successfully.")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No record found with ID: " + id);
    }

    @PutMapping("/latestUpdates/{id}/status")
    public ResponseEntity<String> updateLiveStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> status) {
        Boolean live = status.get("live");
        logger.info("PUT /admin/latestUpdates/{}/status - Updating live status to {}", id, live);

        boolean updated = latestUpdatesService.updateLiveStatus(id, live);
        return updated
                ? ResponseEntity.ok("Status updated successfully.")
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No record found with ID: " + id);
    }
}
