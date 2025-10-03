package blog.com.Blog.Application.controller.adminController;


import blog.com.Blog.Application.service.adminService.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
@RequestMapping("/admin")
public class Dashboard {


    @Autowired
    private AdminService adminService;


    @GetMapping("/dashboard")
    public ResponseEntity<Map<String,Long>> getAllCounts(){
        return new ResponseEntity<>(adminService.getAllCounts(), HttpStatus.OK);
    }

}
