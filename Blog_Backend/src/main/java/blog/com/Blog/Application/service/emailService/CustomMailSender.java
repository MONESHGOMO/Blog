package blog.com.Blog.Application.service.emailService;
import blog.com.Blog.Application.model.EmailSubscribers;
import blog.com.Blog.Application.repository.SubscribersRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomMailSender {

    private final JavaMailSender mailSender;
    private final SubscribersRepository emailSubscribersRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomMailSender.class);



    @Value("${spring.mail.username}")
    private String fromEmail;

    public CustomMailSender(JavaMailSender mailSender, SubscribersRepository emailSubscribersRepository) {
        this.mailSender = mailSender;
        this.emailSubscribersRepository = emailSubscribersRepository;
    }


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
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(email);
            message.setSubject("No reply email from dev16-blog");
            message.setText(buildSubscribersMessage());
            mailSender.send(message);
            logger.info("Subscription confirmation sent to: {}", email);
        } catch (Exception e) {
            logger.error("Failed to send subscription confirmation to {}: {}", email, e.getMessage(), e);
        }
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
                            You're now part of our community and will receive daily updates whenever we publish a new blog post.<br><br>
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

    public List<String> getAllSubscribersEmails() {
        try {
            List<EmailSubscribers> subscribers = emailSubscribersRepository.findAll();
            return subscribers.stream()
                    .map(EmailSubscribers::getEmail)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Failed to fetch subscribers: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch subscribers", e);
        }
    }

    public void pushMessageToAllSubscribers(String category, String title, String imageURL) {
        try {
            List<String> subscriberEmails = getAllSubscribersEmails();

            if (subscriberEmails.isEmpty()) {
                logger.info("No subscribers found to send notification to");
                return;
            }

            logger.info("Sending new blog notification to {} subscribers", subscriberEmails.size());

            for (String email : subscriberEmails) {
                try {
                    pushMessageToSubscriber(category, title, imageURL, email);
                    Thread.sleep(100);
                } catch (Exception e) {
                    logger.error("Failed to send email to {}: {}", email, e.getMessage());
                    // Continue with other emails even if one fails
                }
            }

            logger.info("New blog notifications sent successfully to all subscribers");
        } catch (Exception e) {
            logger.error("Failed to send notifications to subscribers: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to send notifications", e);
        }
    }

    private void pushMessageToSubscriber(String category, String title, String imageURL, String email) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            String subject = "New Blog Published: " + title;
            helper.setFrom(fromEmail);
            helper.setTo(email);
            helper.setSubject(subject);

            String htmlContent = buildNewBlogNotification(category, title, imageURL);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            logger.debug("New blog notification sent to: {}", email);
        } catch (MessagingException e) {
            logger.error("Failed to send new blog notification to {}: {}", email, e.getMessage(), e);
            throw new RuntimeException("Failed to send email to " + email, e);
        }
    }

    private String buildNewBlogNotification(String category, String title, String imageURL) {
        return "<!DOCTYPE html>" +
                "<html lang=\"en\">" +
                "<head>" +
                "    <meta charset=\"UTF-8\">" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "    <title>New Blog Published</title>" +
                "    <style>" +
                "        body {" +
                "            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;" +
                "            background-color: #f7f9fc;" +
                "            margin: 0;" +
                "            padding: 0;" +
                "            color: #333;" +
                "        }" +
                "        .container {" +
                "            max-width: 600px;" +
                "            margin: 0 auto;" +
                "            background-color: #ffffff;" +
                "        }" +
                "        .header {" +
                "            background: linear-gradient(to right, #1e3c72, #2a5298);" +
                "            padding: 20px;" +
                "            text-align: center;" +
                "        }" +
                "        .logo {" +
                "            color: white;" +
                "            font-size: 24px;" +
                "            font-weight: bold;" +
                "        }" +
                "        .content {" +
                "            padding: 30px;" +
                "        }" +
                "        .blog-image {" +
                "            width: 100%;" +
                "            border-radius: 8px;" +
                "            margin-bottom: 20px;" +
                "        }" +
                "        .category {" +
                "            display: inline-block;" +
                "            background-color: #e8f0fe;" +
                "            color: #1e3c72;" +
                "            padding: 5px 12px;" +
                "            border-radius: 20px;" +
                "            font-size: 14px;" +
                "            font-weight: 500;" +
                "            margin-bottom: 15px;" +
                "        }" +
                "        .title {" +
                "            font-size: 22px;" +
                "            font-weight: 700;" +
                "            color: #1e3c72;" +
                "            margin-bottom: 20px;" +
                "            line-height: 1.4;" +
                "        }" +
                "        .description {" +
                "            font-size: 16px;" +
                "            line-height: 1.6;" +
                "            color: #444;" +
                "            margin-bottom: 25px;" +
                "        }" +
                "        .cta-button {" +
                "            display: inline-block;" +
                "            background: linear-gradient(to right, #1e3c72, #2a5298);" +
                "            color: white;" +
                "            text-align: center;" +
                "            padding: 12px 25px;" +
                "            border-radius: 5px;" +
                "            text-decoration: none;" +
                "            font-weight: 600;" +
                "            margin: 15px 0;" +
                "        }" +
                "        .footer {" +
                "            background-color: #f1f5f9;" +
                "            padding: 20px;" +
                "            text-align: center;" +
                "            font-size: 14px;" +
                "            color: #666;" +
                "        }" +
                "        .social-links {" +
                "            margin: 15px 0;" +
                "        }" +
                "        .social-links a {" +
                "            color: #1e3c72;" +
                "            margin: 0 10px;" +
                "            text-decoration: none;" +
                "            font-weight: 500;" +
                "        }" +
                "        @media (max-width: 600px) {" +
                "            .content {" +
                "                padding: 20px;" +
                "            }" +
                "            .title {" +
                "                font-size: 20px;" +
                "            }" +
                "        }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class=\"container\">" +
                "        <div class=\"header\">" +
                "            <div class=\"logo\">Dev16 Blog</div>" +
                "        </div>" +
                "        " +
                "        <div class=\"content\">" +
                "            <p class=\"category\">" + category + "</p>" +
                "            <h1 class=\"title\">" + title + "</h1>" +
                "            " +
                "            <img src=\"" + imageURL + "\" alt=\"" + title + "\" class=\"blog-image\">" +
                "            " +
                "            <p class=\"description\">We've just published a new blog post that we think you'll find interesting. The article explores the latest trends and insights in " + category + ".</p>" +
                "            " +
                "            <p class=\"description\">Read the full article to discover practical tips, in-depth analysis, and expert perspectives on this topic.</p>" +
                "            " +
                "            <a href=\"https://dev16-blog.web.app\" class=\"cta-button\">Read Now</a>" +
                "        </div>" +
                "        " +
                "        <div class=\"footer\">" +
                "            <p>You're receiving this email because you subscribed to updates from Dev16 Blog</p>" +
                "            " +
                "            <div class=\"social-links\">" +
                "                <a href=\"https://github.com/MONESHGOMO\">GitHub</a> | " +
                "                <a href=\"https://dev16-blog.web.app\">Blog</a> | " +
                "                <a href=\"https://moneshgomo.netlify.app\">Portfolio</a>" +
                "            </div>" +
                "            " +
                "            <p>&copy; 2023 Dev16 Blog. All rights reserved.</p>" +
                "            <p><a href=\"#\">Unsubscribe</a> from these notifications</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
}