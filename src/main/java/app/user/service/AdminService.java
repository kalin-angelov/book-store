package app.user.service;

import app.exeptions.UserException;
import app.user.model.User;
import app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UserException("User with email [%s] not found.".formatted(email)));
    }

    public List<User> findAllUsers() {

        return userRepository.findAll();
    }

}
