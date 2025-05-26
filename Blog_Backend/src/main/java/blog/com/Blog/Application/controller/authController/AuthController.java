package blog.com.Blog.Application.controller.authController;
import org.springframework.security.core.userdetails.User;
import blog.com.Blog.Application.DTO.JwtResponse;
import blog.com.Blog.Application.DTO.Login_DTO;
import blog.com.Blog.Application.DTO.RegisterUser_DTO;
import blog.com.Blog.Application.model.BlogUser;
import blog.com.Blog.Application.service.sercurity.JWT.JwtUtil;
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

    @PostMapping("/register/user")
    public ResponseEntity<?> addUser(@RequestBody @Valid RegisterUser_DTO registerUserDto) {
        try {
            logger.info("Registration attempt for email: {}", registerUserDto.getEmail());

            if (registrationService.existsByEmail(registerUserDto.getEmail())) {
                logger.warn("Attempt to register with existing email: {}", registerUserDto.getEmail());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email Already Exists");
            }

            boolean created = registrationService.createUser(registerUserDto);
            if (created) {
                Optional<BlogUser> createdUser = registrationService.findByEmail(registerUserDto.getEmail());
                if (createdUser.isPresent()) {
                    logger.info("User registered successfully: {}", createdUser.get().getEmail());
                    return ResponseEntity
                            .status(HttpStatus.CREATED)
                            .body(createdUser.get());
                } else {
                    logger.error("User was created but could not be retrieved for email: {}", registerUserDto.getEmail());
                    return ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("User created but retrieval failed");
                }
            } else {
                logger.error("User creation failed in service layer for email: {}", registerUserDto.getEmail());
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to register user");
            }
        } catch (Exception e) {
            logger.error("Exception during user registration for email {}: {}", registerUserDto.getEmail(), e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login_DTO request) {
        try {
            logger.info("Login attempt for email: {}", request.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtil.generateToken(request.getEmail());

            Optional<BlogUser> optionalUser = registrationService.findByEmail(request.getEmail());
            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            BlogUser user = optionalUser.get();
            String userRole = user.getRole();
            String userName =user.getUsername();

            logger.info("Login successful for email: {} with role: {}  with  userName  : {}", request.getEmail(), userRole,userName);
            return ResponseEntity.ok(new JwtResponse(token, userRole,userName));

        } catch (BadCredentialsException e) {
            logger.warn("Invalid login attempt for email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            logger.error("Unexpected error during login for email {}: {}", request.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }


   /* @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login_DTO request) {
        try {
            logger.info("Login attempt for email: {}", request.getEmail());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtil.generateToken(request.getEmail());
            logger.info("Login successful for email: {}", request.getEmail());
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (BadCredentialsException e) {
            logger.warn("Invalid login attempt for email: {}", request.getEmail());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            logger.error("Unexpected error during login for email {}: {}", request.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }   */
}

