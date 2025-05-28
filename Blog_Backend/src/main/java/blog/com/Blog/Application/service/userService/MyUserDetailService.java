package blog.com.Blog.Application.service.userService;


import blog.com.Blog.Application.model.BlogUser;
import blog.com.Blog.Application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<BlogUser> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            BlogUser user = userOpt.get();
            String role = user.getRole();

            return User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(role)
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found: " + email);
        }
    }


    private String getRoles(BlogUser blogUser) {
        return blogUser.getRole();
    }


}
