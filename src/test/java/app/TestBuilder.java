package app;

import app.user.model.User;
import app.user.model.UserRole;
import app.web.dto.LoginRequest;
import app.web.dto.RegisterRequest;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@UtilityClass
public class TestBuilder {

    public static User aRandomExistingUser() {

        return User.builder()
                .id(UUID.randomUUID())
                .role(UserRole.USER)
                .email("UserTestEmail@gmail.com")
                .password("testPassword")
                .name("testName")
                .address("testAddress")
                .isActive(true)
                .registerOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .orders(new ArrayList<>())
                .build();
    }

    public static User aRandomNewUser() {

        return User.builder()
                .role(UserRole.USER)
                .email("UserTestEmail@gmail.com")
                .password("testPassword")
                .isActive(true)
                .registerOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .orders(new ArrayList<>())
                .build();
    }

    public static RegisterRequest aRandomRegisterRequest() {

        return RegisterRequest.builder()
                .email("UserTestEmail@gmail.com")
                .password("testPassword")
                .confirmPassword("testPassword")
                .build();
    }

    public static LoginRequest aRandomLoginRequest() {

        return LoginRequest.builder()
                .email("UserTestEmail@gmail.com")
                .password("testPassword")
                .build();
    }
}
