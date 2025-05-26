package blog.com.Blog.Application.service.userService;

import blog.com.Blog.Application.DTO.RegisterUser_DTO;
import blog.com.Blog.Application.model.BlogUser;
import blog.com.Blog.Application.model.Role;
import blog.com.Blog.Application.repository.UserRepository;
import jakarta.validation.Valid;
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

    public Boolean createUser(@Valid RegisterUser_DTO registerUserDto) {
        try {
            BlogUser blogUser = new BlogUser();
            blogUser.setEmail(registerUserDto.getEmail());
            System.out.println("================================================================================");

            System.out.println("Before encode : " + registerUserDto.getPassword());
            blogUser.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
            blogUser.setUsername(registerUserDto.getUsername());
            System.out.println("after encode : " + blogUser.getPassword());
            System.out.println("================================================================================");

            Role assignedRole = assignRoleBasedOnUsername(registerUserDto.getUsername());
            blogUser.setRole(assignedRole);


            userRepository.save(blogUser);
            return true;

        } catch (Exception e) {
            log.error("Exception occurred during user registration", e);
            return false;
        }
    }

    private Role assignRoleBasedOnUsername(String username) {
        String userNameLower = username.toLowerCase();
        log.info("Creating user: {}", userNameLower);

        if (userNameLower.contains(adminSecretKey.toLowerCase())) {
            log.info("Assigned role: ADMIN");
            return Role.ADMIN;
        } else {
            log.info("Assigned role: USER");
            return Role.USER;
        }
    }

    public Optional<BlogUser> findByEmail(@NotBlank @Email String email) {
        return userRepository.findByEmail(email);
    }
}
