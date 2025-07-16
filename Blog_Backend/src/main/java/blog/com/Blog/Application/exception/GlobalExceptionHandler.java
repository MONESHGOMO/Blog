package blog.com.Blog.Application.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        String html = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>Access Restricted</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet">
                <style>
                    .error-card {
                        max-width: 500px;
                        margin: 0 auto;
                        border: none;
                        border-radius: 15px;
                        overflow: hidden;
                        box-shadow: 0 10px 30px rgba(0,0,0,0.1);
                        transition: all 0.3s ease;
                    }
                    .error-card:hover {
                        transform: translateY(-5px);
                        box-shadow: 0 15px 35px rgba(0,0,0,0.15);
                    }
                    .error-icon {
                        font-size: 5rem;
                        margin-bottom: 1rem;
                        color: #dc3545;
                    }
                    .btn-go-back {
                        background: linear-gradient(135deg, #6a11cb 0%, #2575fc 100%);
                        border: none;
                        padding: 10px 25px;
                        border-radius: 50px;
                        font-weight: 600;
                        transition: all 0.3s ease;
                    }
                    .btn-go-back:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 5px 15px rgba(37, 117, 252, 0.4);
                    }
                    .footer {
                        background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                    }
                </style>
            </head>
            <body class="d-flex flex-column min-vh-100 bg-light">
                <div class="container my-auto py-5">
                    <div class="error-card animate__animated animate__fadeInUp bg-white p-4 text-center">
                        <div class="error-icon animate__animated animate__shakeX">
                            <i class="bi bi-shield-lock-fill"></i>
                        </div>
                        <h1 class="display-4 fw-bold text-danger mb-3">403</h1>
                        <h3 class="mb-3">Access Restricted</h3>
                        <p class="text-muted mb-4">You don't have permission to view this content.</p>
                        <button onclick="window.history.back()" class="btn btn-go-back text-white">
                            <i class="bi bi-arrow-left me-2"></i>Return to Safety
                        </button>
                    </div>
                </div>
              
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"></script>
            </body>
            </html>
            """;

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(html);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpServletRequest request) {
        String html = """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1">
                <title>Page Not Found</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <link href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css" rel="stylesheet">
                <style>
                    .error-card {
                        max-width: 500px;
                        margin: 0 auto;
                        border: none;
                        border-radius: 15px;
                        overflow: hidden;
                        box-shadow: 0 10px 30px rgba(0,0,0,0.1);
                        transition: all 0.3s ease;
                    }
                    .error-card:hover {
                        transform: translateY(-5px);
                        box-shadow: 0 15px 35px rgba(0,0,0,0.15);
                    }
                    .error-icon {
                        font-size: 5rem;
                        margin-bottom: 1rem;
                        color: #fd7e14;
                    }
                    .btn-go-back {
                        background: linear-gradient(135deg, #f12711 0%, #f5af19 100%);
                        border: none;
                        padding: 10px 25px;
                        border-radius: 50px;
                        font-weight: 600;
                        transition: all 0.3s ease;
                    }
                    .btn-go-back:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 5px 15px rgba(245, 175, 25, 0.4);
                    }
                    .footer {
                        background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
                    }
                </style>
            </head>
            <body class="d-flex flex-column min-vh-100 bg-light">
                <div class="container my-auto py-5">
                    <div class="error-card animate__animated animate__fadeInUp bg-white p-4 text-center">
                        <div class="error-icon animate__animated animate__pulse">
                            <i class="bi bi-exclamation-triangle-fill"></i>
                        </div>
                        <h1 class="display-4 fw-bold text-warning mb-3">404</h1>
                        <h3 class="mb-3">Page Not Found</h3>
                        <p class="text-muted mb-4">The content you're looking for doesn't exist or has been moved.</p>
                        <button onclick="window.history.back()" class="btn btn-go-back text-white">
                            <i class="bi bi-arrow-left me-2"></i>Go Back
                        </button>
                    </div>
                </div>
                
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css"></script>
            </body>
            </html>
            """;

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(html);
    }
}