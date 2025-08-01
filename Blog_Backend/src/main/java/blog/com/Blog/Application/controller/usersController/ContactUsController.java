package blog.com.Blog.Application.controller.usersController;

import blog.com.Blog.Application.model.ContactUs;
import blog.com.Blog.Application.service.contactUsService.Contact_Us;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class ContactUsController {

    private static final Logger logger = LoggerFactory.getLogger(ContactUsController.class);

    @Autowired
    private Contact_Us contactUsService;

    @PostMapping("/contact-us")
    public ResponseEntity<Map<String, Object>> contactUs(@RequestHeader("Authorization") String authToken, @RequestBody ContactUs contactUs) {

        logger.info("Received contact-us request from token: {}", authToken);

        Map<String, Object> response = contactUsService.storeMessage(authToken, contactUs);
        int statusCode = (int) response.get("code");

        return ResponseEntity.status(statusCode).body(response);
    }
}
