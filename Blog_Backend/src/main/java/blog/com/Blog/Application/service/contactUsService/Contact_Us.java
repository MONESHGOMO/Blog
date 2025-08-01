package blog.com.Blog.Application.service.contactUsService;

import blog.com.Blog.Application.Exceptions.UnauthorizedAccessException;
import blog.com.Blog.Application.model.ContactUs;
import blog.com.Blog.Application.model.EmailSubscribers;
import blog.com.Blog.Application.repository.ContactUsRepository;
import blog.com.Blog.Application.repository.SubscribersRepository;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Contact_Us {

    @Autowired
    private ContactUsRepository contactUsRepository;

    @Autowired
    private SubscribersRepository subscribersRepository;

    @Value("${auth.header}")
    private String AUTH_HEADER;

    public Map<String, Object> storeMessage(String authToken, ContactUs contactUsDTO) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (!authToken.equals(AUTH_HEADER)) {
                throw new UnauthorizedAccessException();
            }

            ContactUs storeMessage = new ContactUs();
            storeMessage.setUserName(contactUsDTO.getUserName());
            storeMessage.setUserEmail(contactUsDTO.getUserEmail());
            storeMessage.setMessage(validateMessage(contactUsDTO.getMessage()));
            storeMessage.setCreatedAt(LocalDateTime.now());

            contactUsRepository.save(storeMessage);

            response.put("status", "success");
            response.put("message", "Message stored successfully");
            response.put("code", HttpStatus.OK.value());
        } catch (UnauthorizedAccessException e) {
            response.put("status", "error");
            response.put("message", "Unauthorized Access");
            response.put("code", HttpStatus.UNAUTHORIZED.value());
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Internal server error");
            response.put("error", e.getMessage());
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return response;
    }

    private String validateMessage(String message) {
        return Jsoup.clean(message, Safelist.none());
    }

    public List<ContactUs> getAllMessages() {
        return contactUsRepository.findAll();
    }

    public List<EmailSubscribers> getAllSubscribers() {
        return subscribersRepository.findAll();
    }
}
