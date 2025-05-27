package blog.com.Blog.Application.controller.HomeController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/home")
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping
    public String welcomeDev() {
        logger.info("GET /home - Welcome message requested");

        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Welcome Dev</title>
                    <style>
                        body {
                            background: linear-gradient(135deg, #2c3e50, #3498db);
                            color: #ecf0f1;
                            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            height: 100vh;
                            margin: 0;
                        }
                        .container {
                            background-color: rgba(0, 0, 0, 0.6);
                            padding: 40px;
                            border-radius: 15px;
                            box-shadow: 0 0 20px rgba(0,0,0,0.5);
                            text-align: center;
                        }
                        h1 {
                            font-size: 3em;
                            margin-bottom: 10px;
                        }
                        p {
                            font-size: 1.2em;
                            color: #bdc3c7;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>🚀 Welcome Gomo  Dev!</h1>
                        <p>Our Spring Boot backend is running .</p>
                        <p>Happy coding! 💻🔥</p>
                    </div>
                </body>
                </html>
                """;
    }
}
