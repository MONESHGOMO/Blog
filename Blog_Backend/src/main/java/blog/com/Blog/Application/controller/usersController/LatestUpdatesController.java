package blog.com.Blog.Application.controller.usersController;

import blog.com.Blog.Application.model.LatestUpdates;
import blog.com.Blog.Application.service.latestUpdates.LatestUpdatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class LatestUpdatesController {

    private static final Logger logger = LoggerFactory.getLogger(LatestUpdatesController.class);

    @Autowired
    private LatestUpdatesService latestUpdatesService;

    @GetMapping("/getLatestUpdates")
    public ResponseEntity<Map<String, Object>> getLatestUpdates() {
        logger.info("User requested latest updates");

        Map<String, Object> response = latestUpdatesService.getLatestUpdate();

        if (response != null && !response.isEmpty()) {
            logger.info("Successfully fetched latest updates for authenticated user");
        } else {
            logger.warn("No latest updates found or user unauthenticated");
        }

        return ResponseEntity.ok(response);
    }
}
