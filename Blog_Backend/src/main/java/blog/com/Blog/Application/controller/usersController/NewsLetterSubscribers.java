package blog.com.Blog.Application.controller.usersController;

import blog.com.Blog.Application.DTO.SubscribeDto;
import blog.com.Blog.Application.service.emailService.Subscribers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class NewsLetterSubscribers {

    private static final Logger logger = LoggerFactory.getLogger(NewsLetterSubscribers.class);

    @Autowired
    private Subscribers subscribers;

    @PostMapping("/subscribe")
    public ResponseEntity<Map<String, Object>> addSubscribers(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody SubscribeDto emailDto) {
        logger.info("Received subscription request");
        Map<String, Object> response = subscribers.addSubscriber(authHeader, emailDto);
        int statusCode = (int) response.get("code");
        if (statusCode == HttpStatus.CREATED.value()) {
            logger.info("Subscription successful");
        }
        return ResponseEntity.status(statusCode).body(response);
    }
}