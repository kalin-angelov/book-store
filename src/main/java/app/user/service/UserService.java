package app.user.service;

import app.exeptions.EmailAlreadyExistInExceptionDB;
import app.exeptions.PasswordException;
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

    public void registerUser(RegisterRequest request) {

        if (!request.getPassword().equals(request.getConfigPassword())) {
            log.info("Password's don't match");
            throw new PasswordException("Password's don't match");
        }

        Optional<User> optionalUser = userRepository.findUserByEmail(request.getEmail());

        if (optionalUser.isPresent()) {
            log.info("User with email [%s] already exist in DB".formatted(request.getEmail()));
            throw new EmailAlreadyExistInExceptionDB("User whit this email already exist.");
        }

        User user = initializeUser(request);
        userRepository.save(user);
        log.info("User successfully added in DB");
    }

    private User initializeUser(RegisterRequest request) {

        return User.builder()
                .role(UserRole.USER)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }
}
