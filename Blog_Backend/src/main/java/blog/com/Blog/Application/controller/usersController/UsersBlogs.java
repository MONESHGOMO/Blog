package blog.com.Blog.Application.controller.usersController;

import java.util.List;

import blog.com.Blog.Application.model.Blogs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import blog.com.Blog.Application.service.userService.BlogsData;

@RestController
@RequestMapping("/users")
public class UsersBlogs {

    private static final Logger logger = LoggerFactory.getLogger(UsersBlogs.class);

    @Autowired
    private BlogsData blogsData;

    @GetMapping("/blogs")
    public ResponseEntity<List<Blogs>> getBlogs(
            @RequestParam(name = "page", defaultValue = "0") int page,String authToken) {

        logger.info("Request to fetch paginated blogs - Page: {}", page);

        List<Blogs> pagedBlogs = blogsData.getPagedBlogs(page,authToken);

        logger.info("Successfully fetched {} blogs for page {}", pagedBlogs.size(), page);
        logger.debug("Fetched blogs details: {}", pagedBlogs);

        return ResponseEntity.ok(pagedBlogs);
    }


    @GetMapping("/blogs/{id}")
    public ResponseEntity<Blogs> getBlogById(@RequestHeader("Authorization") String authToken, @PathVariable Long id) {
        logger.info("Request to fetch blog by ID: {}", id);
        Blogs blog = blogsData.getAllBlogByIdFromDB(id, authToken);
        logger.info("Successfully fetched blog with ID: {}", id);
        return ResponseEntity.ok(blog);
    }

    @GetMapping("/getBlogCount")
    public Integer getBlogCount(@RequestHeader("Authorization") String authToken) {
        logger.info("Request to fetch blog count");
        int count = blogsData.getAllCountOfBlog(authToken);
        logger.info("Total blog count fetched: {}", count);
        return count;
    }

        @GetMapping("/category")
    public ResponseEntity<List<String>> getAllCategory(@RequestHeader("Authorization") String authToken) {
        logger.info("Request to fetch blog categories");
        List<String> getAllCategory = blogsData.getCategory(authToken);
        logger.info("Fetched {} categories", getAllCategory.size());
        return new ResponseEntity<>(getAllCategory, HttpStatus.OK);
    }

    @GetMapping("/getBlogByCategory/{categoryName}")
    public ResponseEntity<List<Blogs>> getBlogByCategory(
            @PathVariable("categoryName") String category,
            @RequestHeader("Authorization") String authToken) {

        List<Blogs> getBlog = blogsData.getBlogByCategory(category, authToken);
        return ResponseEntity.ok(getBlog);
    }


    @GetMapping("/getRecentBlog")
    public ResponseEntity<List<Blogs>> getRecentBlogs(@RequestHeader("Authorization") String authToken) {
        logger.info("Request to fetch recent blogs");
        List<Blogs> recentBlogs = blogsData.getTheLatestBlog(authToken);
        if (recentBlogs != null && !recentBlogs.isEmpty()) {
            logger.info("Fetched {} recent blogs", recentBlogs.size());
            return new ResponseEntity<>(recentBlogs, HttpStatus.OK);
        } else {
            logger.warn("No recent blogs found");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
