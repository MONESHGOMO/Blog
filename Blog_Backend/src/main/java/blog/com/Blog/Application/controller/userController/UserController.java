package blog.com.Blog.Application.controller.userController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import blog.com.Blog.Application.model.Blog;
import blog.com.Blog.Application.service.userService.BlogsData;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private BlogsData blogsData;

    @GetMapping("/blogs") // Example: http://localhost:8080/users/blogs?page=0&size=5
    public ResponseEntity<?> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        List<Blog> allBlogs = blogsData.getAllBlogsFromDB();
        int total = allBlogs.size();
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, total);

        if (fromIndex >= total) {
            return new ResponseEntity<>("Page index out of range.", HttpStatus.BAD_REQUEST);
        }

        List<Blog> pagedBlogs = allBlogs.subList(fromIndex, toIndex);
        return new ResponseEntity<>(pagedBlogs, HttpStatus.OK);
    }

    @GetMapping("/blogs/{id}")
    public ResponseEntity<?> getBlogById(@PathVariable Long id) {
        Optional<Blog> blog = blogsData.getAllBlogByIdFromDB(id);
        return blog.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
