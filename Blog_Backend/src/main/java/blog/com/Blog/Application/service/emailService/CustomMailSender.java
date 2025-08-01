package blog.com.Blog.Application.service.emailService;

import jakarta.persistence.Cacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CustomMailSender {

    private final JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(CustomMailSender.class);


    public CustomMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private final SimpleMailMessage message = new SimpleMailMessage();

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendUserSignUpNotification(String to) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Welcome to Dev16 Blog!");
            message.setText(buildEmailBody());

            mailSender.send(message);
            logger.info("Signup confirmation email sent to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send signup email to {}: {}", to, e.getMessage(), e);
        }
    }

    private String buildEmailBody() {
        return """
                Dear User,
                
                Thank you for registering at dev16 Blog. We are excited to welcome you to our growing community of passionate readers and contributors.
                
                You can now browse trending posts, share your ideas, and connect with fellow enthusiasts.
                
                👉 Get started here: https://dev16-blog.web.app
                
                If you wish to contribute by sharing your own blog articles, please send your documents to: gomo.dev.16@gmail.com
                
                We look forward to your valuable contributions.
                
                Best regards,
                Team Gomo Blogs
                https://moneshgomo.netlify.app
                """;
    }
}
