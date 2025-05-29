package blog.com.Blog.Application.service.emailService;


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

    @Autowired
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
            message.setSubject("Welcome to Gomo Blogs!");
            message.setText(buildEmailBody());

            mailSender.send(message);
            logger.info("Signup confirmation email sent to: {}", to);
        } catch (Exception e) {
            logger.error("Failed to send signup email to {}: {}", to, e.getMessage(), e);
        }
    }

    private String buildEmailBody() {
        return """
            Hi there,

            Thank you for signing up at Gomo Blog! We’re thrilled to have you in our community.

            You can now explore trending posts, share your ideas, and engage with other readers.

            👉 Start exploring: https://gomo-blog.com (SOON)

            If you didn’t sign up for this account, please disregard this email.

            Warm regards,
            Gomo Blog Team
            """;
    }
}
