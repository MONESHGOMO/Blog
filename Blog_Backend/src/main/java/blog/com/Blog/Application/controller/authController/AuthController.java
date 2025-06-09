package blog.com.Blog.Application.controller.authController;

import blog.com.Blog.Application.service.emailService.CustomMailSender;
import blog.com.Blog.Application.DTO.JwtResponse;
import blog.com.Blog.Application.DTO.Login_DTO;
import blog.com.Blog.Application.DTO.RegisterUser_DTO;
import blog.com.Blog.Application.model.BlogUser;
import blog.com.Blog.Application.service.security.JWT.JwtUtil;
import blog.com.Blog.Application.service.userService.RegistrationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://127.0.0.1:5500",
        "http://localhost:5501",
        "http://127.0.0.1:5501"
})
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class.getName());

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomMailSender mailSender;
    
    
    @PostMapping("/register/user")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUser_DTO registerUserDto) {
        logger.info("Registration attempt for: {}", registerUserDto.getEmail());
        
        if (registrationService.existsByEmail(registerUserDto.getEmail())) {
            logger.warn("Duplicate registration: {}", registerUserDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email exists");
        }

        if (!registrationService.createUser(registerUserDto)) {
            logger.error("Registration failed for: {}", registerUserDto.getEmail());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        logger.info("User registered: {}", registerUserDto.getEmail());
        mailSender.sendUserSignUpNotification(registerUserDto.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login_DTO request) {
        try {
            logger.info("Login attempt for email: {}", request.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtil.generateToken(request.getEmail());

            Optional<BlogUser> optionalUser = registrationService.findByEmail(request.getEmail());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            BlogUser user = optionalUser.get();
            String userRole = user.getRole();
            String userName = user.getUsername();

            logger.info("Login successful for email: {} with role: {}  with  userName  : {}", request.getEmail(),
                    userRole, userName);
            return ResponseEntity.ok(new JwtResponse(token, userRole, userName));

        } catch (BadCredentialsException e) {
            logger.warn("Invalid login attempt for email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
        } catch (Exception e) {
            logger.error("Unexpected error during login for email {}: {}", request.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

 
}
