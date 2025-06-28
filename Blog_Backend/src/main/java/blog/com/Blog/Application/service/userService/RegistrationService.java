package blog.com.Blog.Application.service.userService;

import blog.com.Blog.Application.DTO.RegisterUser_DTO;
import blog.com.Blog.Application.model.BlogUser;
import blog.com.Blog.Application.model.Role;
import blog.com.Blog.Application.repository.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.secret}")
    private String adminSecretKey;

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Boolean existsByEmail(@NotBlank @Email String email) {
        return userRepository.existsByEmail(email);
    }

    
    public boolean createUser(RegisterUser_DTO registerUserDto) {
        if (userRepository.existsByEmail(registerUserDto.getEmail())) {
            return false;
        }

        BlogUser user = new BlogUser();
        user.setUsername(registerUserDto.getUsername());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setRole(determineRole(registerUserDto.getUsername()));
        
        userRepository.save(user);
        return true;
    }

    private Role determineRole(String username) {
        if (username == null || username.isEmpty()) {
            return Role.USER;
        }
        if (username.toLowerCase().contains(adminSecretKey.toLowerCase())) {
            return Role.ADMIN;
        } else {
            return Role.USER;
        }
    }



    public Optional<BlogUser> findByEmail(@NotBlank @Email String email) {
        return userRepository.findByEmail(email);
    }
}
