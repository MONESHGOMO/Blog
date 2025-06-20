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
                           <title>Dev16 Welcome's U </title>
                           <meta name="viewport" content="width=device-width, initial-scale=1">
                
                           <!-- Bootstrap 5 -->
                           <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
                
                           <!-- Font Awesome -->
                           <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" rel="stylesheet">
                
                           <style>
                               body {
                                   margin: 0;
                                   padding: 0;
                                   min-height: 100vh;
                                   display: flex;
                                   justify-content: center;
                                   align-items: center;
                                   font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                                   background: linear-gradient(-45deg, #1e3c72, #2a5298, #4e54c8, #1fa2ff);
                                   background-size: 400% 400%;
                                   animation: gradientMove 12s ease infinite;
                               }
                
                               @keyframes gradientMove {
                                   0% {
                                       background-position: 0% 50%;
                                   }
                
                                   50% {
                                       background-position: 100% 50%;
                                   }
                
                                   100% {
                                       background-position: 0% 50%;
                                   }
                               }
                
                               .welcome-container {
                                   background: rgba(255, 255, 255, 0.05);
                                   border: 1px solid rgba(255, 255, 255, 0.15);
                                   box-shadow: 0 8px 32px rgba(0, 0, 0, 0.3);
                                   backdrop-filter: blur(12px);
                                   -webkit-backdrop-filter: blur(12px);
                                   border-radius: 20px;
                                   padding: 3rem 2rem;
                                   color: #ffffff;
                                   text-align: center;
                                   max-width: 600px;
                                   width: 90%;
                                   animation: fadeInUp 1.4s ease;
                               }
                
                               @keyframes fadeInUp {
                                   0% {
                                       opacity: 0;
                                       transform: translateY(30px);
                                   }
                
                                   100% {
                                       opacity: 1;
                                       transform: translateY(0);
                                   }
                               }
                
                               .blog-link {
                                   color: #00ffe1;
                                   font-weight: 500;
                                   transition: color 0.3s ease, text-shadow 0.3s ease;
                               }
                
                               .blog-link:hover {
                                   color: #ffffff;
                                   text-shadow: 0 0 8px #00ffe1;
                               }
                
                               .social-icons a {
                                   color: #ffffff;
                                   margin: 0 15px;
                                   font-size: 1.8rem;
                                   transition: transform 0.3s ease, color 0.3s ease, text-shadow 0.3s ease;
                               }
                
                               .social-icons a:hover {
                                   color: #00ffe1;
                                   transform: scale(1.3);
                                   text-shadow: 0 0 10px #00ffe1;
                               }
                
                               @media (max-width: 576px) {
                                   .welcome-container {
                                       padding: 2rem;
                                   }
                
                                   h1.display-4 {
                                       font-size: 2rem;
                                   }
                
                                   .social-icons a {
                                       font-size: 1.5rem;
                                       margin: 0 10px;
                                   }
                               }
                           </style>
                       </head>
                
                       <body>
                
                           <div class="welcome-container container">
                               <h1 class="display-4 mb-3">dev16 Backend is running fine!</h1>
                               <p class="lead mb-2">
                                   Visit our <a href="https://dev16-blog.web.app/" target="_blank"
                                       class="blog-link text-decoration-underline">Blog</a>.
                               </p>
                               <p class="fs-5 mb-4">Happy coding! 💻🔥</p>
                
                               <div class="social-icons">
                                   <a href="https://github.com/MONESHGOMO" target="_blank" aria-label="GitHub">
                                       <i class="fab fa-github"></i>
                                   </a>
                
                               </div>
                           </div>
                
                           <!-- Bootstrap Bundle -->
                           <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
                       </body>
                
                       </html>
                """;
    }
}
