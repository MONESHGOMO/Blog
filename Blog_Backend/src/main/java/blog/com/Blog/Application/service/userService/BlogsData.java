package blog.com.Blog.Application.service.userService;

import blog.com.Blog.Application.Exceptions.BlogNotFoundException;
import blog.com.Blog.Application.Exceptions.UnauthorizedAccessException;
import blog.com.Blog.Application.model.Blogs;
import blog.com.Blog.Application.repository.BlogRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogsData {

    private static final Logger logger = LoggerFactory.getLogger(BlogsData.class);

    @Autowired
    private BlogRepository blogRepository;

    private static final int DEFAULT_PAGE_SIZE = 5;

    @Value("${auth.header}")
    private String AUTH_HEADER;
    public List<Blogs> getPagedBlogs(int page, String authHeader) {

        if (!AUTH_HEADER.equals(authHeader)) {
            throw new UnauthorizedAccessException();
        }

        logger.info("Fetching page {} with default size {}", page, DEFAULT_PAGE_SIZE);
        List<Blogs> allBlogs = blogRepository.findAll();

        int total = allBlogs.size();
        int fromIndex = page * DEFAULT_PAGE_SIZE;
        int toIndex = Math.min(fromIndex + DEFAULT_PAGE_SIZE, total);

        if (fromIndex >= total) {
            logger.warn("Page {} out of range (total blogs: {}).", page, total);
            throw new BlogNotFoundException();
        }

        return new ArrayList<>(allBlogs.subList(fromIndex, toIndex));
    }

    public Blogs getAllBlogByIdFromDB(Long id, String authToken) {
        logger.info("Fetching blog with ID: {}", id);

        if (!authToken.equals(AUTH_HEADER)) {
            throw new UnauthorizedAccessException();
        }

        return blogRepository.findById(id)
                .orElseThrow(BlogNotFoundException::new);
    }

    public Integer getAllCountOfBlog(String authToken) {

        if(authToken.equals(AUTH_HEADER)){
            List<Blogs> allBlog = blogRepository.findAll();
            return allBlog.size();
        }
        return 0;

    }

    public List<Blogs> getTheLatestBlog() {
        return blogRepository.getLatestBlogNative();
    }


    public List<String> getCategory(String authToken) {
        if (!AUTH_HEADER.equals(authToken)) {
            throw new UnauthorizedAccessException();
        }
        List<String> allCategories = blogRepository.getListOfCategory();
        Set<String> uniqueCategories = new HashSet<>(allCategories);
        return new ArrayList<>(uniqueCategories);
    }

    public List<Blogs> getBlogByCategory(String category,String authToken) {
        if (!AUTH_HEADER.equals(authToken)) {
            throw new UnauthorizedAccessException();
        }
        return blogRepository.getBlogByCategory(category);
    }
}
