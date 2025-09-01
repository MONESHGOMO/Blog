package blog.com.Blog.Application.service.emailService;

import blog.com.Blog.Application.DTO.SubscribeDto;
import blog.com.Blog.Application.Exceptions.UnauthorizedAccessException;
import blog.com.Blog.Application.model.EmailSubscribers;
import blog.com.Blog.Application.repository.SubscribersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class Subscribers {

    @Autowired
    private SubscribersRepository subscribersRepository;

    @Value("${auth.header}")
    private String AUTH_HEADER;

    @Autowired
    private CustomMailSender customMailSender;

    public Map<String, Object> addSubscriber(String authHeader, SubscribeDto emailDto) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (authHeader == null || !authHeader.equals(AUTH_HEADER)) {
                throw new UnauthorizedAccessException();
            }

            Long emailExists = existsByEmail(emailDto.getEmail());
            if (emailExists != null && emailExists > 0) {
                response.put("status", "error");
                response.put("message", "Email already subscribed");
                response.put("code", HttpStatus.CONFLICT.value()); // 409
                return response;
            }

            EmailSubscribers addSubscribers = new EmailSubscribers();
            addSubscribers.setEmail(emailDto.getEmail());
            addSubscribers.setDateOFSubscribe(new Date(System.currentTimeMillis()));
            subscribersRepository.save(addSubscribers);

            response.put("status", "success");
            response.put("message", "Subscription successful");
            response.put("code", HttpStatus.CREATED.value()); // 201
            customMailSender.sendConfirmEmail(emailDto.getEmail());
        } catch (UnauthorizedAccessException e) {
            response.put("status", "error");
            response.put("message", "Unauthorized Access");
            response.put("code", HttpStatus.UNAUTHORIZED.value()); // 401
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Internal Server Error");
            response.put("error", e.getMessage());
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value()); // 500
        }

        return response;
    }

    public Long existsByEmail(String email) {
        return subscribersRepository.countByEmail(email);
    }
}