package app.user.service;

import app.exeptions.EmailAlreadyExistInExceptionDB;
import app.exeptions.PasswordException;
import app.exeptions.UserException;
import app.user.model.User;
import app.user.model.UserRole;
import app.user.repository.UserRepository;
import app.web.dto.LoginRequest;
import app.web.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
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

    public void verify(LoginRequest loginRequest) {

        try {
            userRepository.findUserByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User with the provided email [%s] is found.".formatted(loginRequest.getEmail())));

        } catch (Exception exception) {
            log.info(exception.getMessage());
        }

        throw new UserException("Invalid Email or Password");

    }

    private User initializeUser(RegisterRequest request) {

        return User.builder()
                .role(UserRole.USER)
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isActive(true)
                .registerOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .build();
    }
}
