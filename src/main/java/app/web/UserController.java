package app.web;

import app.user.model.User;
import app.user.service.UserService;
import app.web.dto.EditUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PutMapping("/edit-user")
    public User editUser(@RequestParam(name = "userId")UUID userId, @RequestBody EditUserRequest request) {

        return userService.editUser(userId, request);
    }
}
