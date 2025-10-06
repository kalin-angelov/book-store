package app.web;

import app.user.model.User;
import app.user.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public List<User> getAllUsers() {

        return adminService.findAllUsers();
    }

    @GetMapping("/user")
    public User getUserByEmail(@RequestParam(name = "email")String email) {
        return adminService.findUserByEmail(email);
    }
}
