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


    public void sendConfirmEmail(String email) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject("No reply email from dev16-blog");
            message.setText(buildSubscribersMessage());
            mailSender.send(message);
    }

    private String buildSubscribersMessage() {
        return """
                <!DOCTYPE html>
                                               <html lang="en">
                                               <head>
                                                   <meta charset="UTF-8">
                                                   <title>Subscription Confirmation</title>
                                                   <style>
                                                       body {
                                                           font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                                                           background-color: #f4f4f4;
                                                           margin: 0;
                                                           padding: 0;
                                                       }
                                                       .container {
                                                           max-width: 600px;
                                                           margin: 40px auto;
                                                           background-color: #ffffff;
                                                           padding: 30px;
                                                           border-radius: 10px;
                                                           box-shadow: 0 0 10px rgba(0,0,0,0.1);
                                                       }
                                                       .header {
                                                           font-size: 24px;
                                                           color: #2c3e50;
                                                           font-weight: bold;
                                                       }
                                                       .message {
                                                           font-size: 16px;
                                                           color: #34495e;
                                                           line-height: 1.6;
                                                           margin-top: 20px;
                                                       }
                                                       .cta {
                                                           display: inline-block;
                                                           margin-top: 20px;
                                                           padding: 12px 20px;
                                                           background-color: #2c3e50;
                                                           color: #ffffff;
                                                           text-decoration: none;
                                                           border-radius: 6px;
                                                           font-weight: bold;
                                                       }
                                                       .footer {
                                                           margin-top: 30px;
                                                           font-size: 14px;
                                                           color: #7f8c8d;
                                                       }
                                                       .footer a {
                                                           color: #2c3e50;
                                                           text-decoration: none;
                                                       }
                                                   </style>
                                               </head>
                                               <body>
                                                   <div class="container">
                                                       <div class="header">🎉 Thank you for subscribing</div>
                                                       <div class="message">
                                                           You’re now part of our community and will receive daily updates whenever we publish a new blog post.<br><br>
                                                           Stay tuned for fresh content, insights, and updates delivered straight to your inbox 🚀
                                                       </div>
                                                       <a href="https://dev16-blog.web.app" class="cta">👉 Visit Our Blog</a>
                                                       <div class="footer">
                                                           <p>Best regards,</p>
                                                           <p><strong>Team Gomo Blogs</strong></p>
                                                           <p><a href="https://moneshgomo.netlify.app">🌐 moneshgomo.netlify.app</a></p>
                                                       </div>
                                                   </div>
                                               </body>
                                               </html>
                
                """;
    }

}
