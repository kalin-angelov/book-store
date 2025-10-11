package app.web;

import app.user.service.UserService;
import app.web.dto.LoginRequest;
import app.web.dto.RegisterRequest;
import app.web.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> registerUser(@RequestBody RegisterRequest request) {

        String token = userService.registerUser(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.builder()
                        .message("User successfully register - [%s]".formatted(token))
                        .build());

    }

    @PostMapping("/login")
    public ResponseEntity<Response> loginUser(@RequestBody LoginRequest loginRequest) {

        userService.verify(loginRequest);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(Response.builder()
                        .message("Welcome")
                        .build());
    }
}
