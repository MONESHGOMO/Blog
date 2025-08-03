package blog.com.Blog.Application.controller.adminController;

import blog.com.Blog.Application.model.ContactUs;
import blog.com.Blog.Application.model.EmailSubscribers;
import blog.com.Blog.Application.service.contactUsService.Contact_Us;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminContactUs {

    private static final Logger logger = LoggerFactory.getLogger(AdminContactUs.class);

    @Autowired
    private Contact_Us contactUs;

    @GetMapping("/contact-us")
    public ResponseEntity<List<ContactUs>> getAllContactUs() {
        logger.info("Request received to fetch all 'Contact Us' messages");
        List<ContactUs> getAll = contactUs.getAllMessages();
        logger.info("Fetched {} contact messages", getAll.size());
        return new ResponseEntity<>(getAll, HttpStatus.OK);
    }

    @GetMapping("/subscribers")
    public ResponseEntity<List<EmailSubscribers>> getAllSubscribers() {
        logger.info("Request received to fetch all email subscribers");
        List<EmailSubscribers> subscribers = contactUs.getAllSubscribers();
        logger.info("Fetched {} subscribers", subscribers.size());
        return new ResponseEntity<>(subscribers, HttpStatus.OK);
    }

    @DeleteMapping("/contact-us/{id}")
    public ResponseEntity<String> deleteContactMessage(@PathVariable Long id) {
        logger.info("Request to delete contact message with ID: {}", id);
            contactUs.deleteMessage(id);
            logger.info("Successfully deleted message with ID: {}", id);
            return ResponseEntity.ok("Message deleted successfully");
    }
}

