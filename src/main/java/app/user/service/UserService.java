package app.user.service;

import app.user.model.User;
import app.user.model.UserRole;
import app.user.repository.UserRepository;
import app.web.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest request) {

        if (!request.getPassword().equals(request.getConfigPassword())) {
            log.info("Password's don't match");
            throw new IllegalArgumentException("Password's don't match");
        }

        Optional<User> optionalUser = userRepository.findUserByEmail(request.getEmail());

        if (optionalUser.isPresent()) {
            log.info("User with email [%s] already exist in DB".formatted(request.getEmail()));
            throw new IllegalArgumentException("User with email [%s] already exist in DB".formatted(request.getEmail()));
        }

        User user = initializeUser(request);
        userRepository.save(user);
        log.info("User successfully added in DB");

        return user;
    }

    private User initializeUser(RegisterRequest request) {

        return User.builder()
                .role(UserRole.USER)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }
}
