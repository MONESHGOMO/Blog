package blog.com.Blog.Application.service.adminService;

import blog.com.Blog.Application.Exceptions.BlogNotFoundException;
import blog.com.Blog.Application.model.Blogs;
import blog.com.Blog.Application.model.BlogUser;
import blog.com.Blog.Application.model.Role;
import blog.com.Blog.Application.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminService {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private LatestUpdatesRepository latestUpdatesRepository;




    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public List<Blogs> getAllBlogsFromDB() {
        try {
            List<Blogs> blogs = blogRepository.getAllBlogs();
            logger.info("Fetched {} blogs successfully.", blogs.size());
            return blogs;
        } catch (Exception e) {
            logger.error("Failed to fetch blogs from database: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Optional<Blogs> getBlogByIdFromDB(Long id) {

            logger.info("Fetching blog with ID: {}", id);
            Optional<Blogs> blog = blogRepository.findById(id);
            if (blog.isPresent()) {
                logger.info("Blog found with ID: {}", id);
            } else {
                throw new BlogNotFoundException();
            }
            return blog;

    }

    public Blogs getBlogFromDBUsingId(Long id) {
        try {
            Blogs getBlog = blogRepository.getBlogFromDBUsingId(id);
            if (getBlog != null) {
                return getBlog;
            } else {
                logger.warn("Blog not found with ID: {}", id);
                return null;
            }
        } catch (Exception e) {
            logger.error("Error while fetching blog: {}", e.getMessage(), e);
            return null;
        }
    }

    public Blogs saveBlog(Blogs addBlogFromAdmin) {
        addBlogFromAdmin.setCreatedAt(new Date());
        return blogRepository.save(addBlogFromAdmin);
    }


    public boolean deleteBlog(Long id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<BlogUser> getAdminData() {
        Role adminRole = Role.ADMIN;
        return userRepository.getAdminDataFromDB(adminRole.name());
    }

    public boolean deleteAdminById(Long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return true;
            }
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete admin", e);
        }
    }

    public Map<String, Long> getAllCounts() {

        Map<String, Long> getCounts = new LinkedHashMap<>();
        Long blogCount = blogRepository.count();
        Long latestUpdates = latestUpdatesRepository.count();
        Long users = userRepository.count();

        getCounts.put("Blog", blogCount);
        getCounts.put("LatestUpdates", latestUpdates);
        getCounts.put("Users", users);

        return getCounts;
    }

}
