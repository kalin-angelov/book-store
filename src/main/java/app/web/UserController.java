package app.web;

import app.user.model.User;
import app.user.service.UserService;
import app.web.dto.ChangePasswordRequest;
import app.web.dto.EditUserRequest;
import app.web.dto.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PutMapping("/{userId}/edit-user")
    public User editUser(@PathVariable UUID userId,@Valid @RequestBody EditUserRequest request) {

        return userService.editUser(userId, request);
    }

    @PutMapping("/{userId}/edit-password")
    public ResponseEntity<Response> changePassword(@PathVariable UUID userId,@Valid @RequestBody ChangePasswordRequest request) {

        userService.editPassword(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.builder()
                        .message("Password successfully change")
                        .build());
    }
}
