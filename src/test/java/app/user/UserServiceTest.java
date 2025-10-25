package app.user;

import app.exeptions.EmailAlreadyExistInExceptionDB;
import app.exeptions.PasswordException;
import app.exeptions.UserException;
import app.jwt.JwtService;
import app.token.service.TokenService;
import app.user.model.User;
import app.user.model.UserRole;
import app.user.repository.UserRepository;
import app.user.service.UserService;
import app.web.dto.ChangePasswordRequest;
import app.web.dto.EditUserRequest;
import app.web.dto.LoginRequest;
import app.web.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static app.TestBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private TokenService tokenService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    @Test
    void givenExistingEmail_whenRegisterUser_exceptionIsThrown() {

        User user = aRandomExistingUser();
        RegisterRequest request = aRandomRegisterRequest();

        when(userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.of(user));

        assertThrows(EmailAlreadyExistInExceptionDB.class, () -> userService.registerUser(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void givenNotMatchingPasswords_whenRegister_exceptionIsThrown() {

        RegisterRequest request = RegisterRequest.builder()
                .email("userEmail@gmail.com")
                .password("1234")
                .confirmPassword("4321")
                .build();

        assertThrows(PasswordException.class, () -> userService.registerUser(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void registerHappyPath() {

        User user = aRandomNewUser();
        RegisterRequest request = aRandomRegisterRequest();

        when(userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(jwtService.generateToken(request.getEmail())).thenReturn(any());

        userService.registerUser(request);

        assertNull(user.getName());
        assertTrue(user.isActive());
        assertNull(user.getAddress());
        assertNotNull(user.getUpdatedOn());
        assertNotNull(user.getRegisterOn());
        assertEquals(user.getRole(), UserRole.USER);
        assertThat(user.getOrders()).hasSize(0);
        assertEquals(request.getEmail(), user.getEmail());
        assertEquals(request.getPassword(), user.getPassword());

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void givenWrongPasswordWithCorrectEmail_whenLogin_ExceptionIsThrown() {

        LoginRequest request = LoginRequest.builder()
                .email("UserTestEmail@gmail.com")
                .password("4321")
                .build();

        doThrow(new BadCredentialsException("Bad credentials")).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        assertThrows(UserException.class, () -> userService.verify(request));
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verifyNoInteractions(userRepository, jwtService, tokenService);
    }

    @Test
    void loginHappyPath() {

        LoginRequest request = aRandomLoginRequest();
        User user = aRandomExistingUser();

        when(userRepository.findUserByEmail(request.getEmail())).thenReturn(Optional.of(user));

        String token = userService.verify(request);

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(user.getEmail());
        verify(tokenService, times(1)).revokedAllUserTokens(user);
        verify(tokenService, times(1)).initializeToken(user, token);
    }

    @Test
    void givenInvalidUserId_whenEditUser_thenExceptionIsThrown() {

        UUID userId = UUID.randomUUID();
        EditUserRequest request = EditUserRequest.builder().build();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserException.class, () -> userService.editUser(userId,request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void givenOnlyEmailForChanging_whenEditUser_thenOnlyTheEmailIsChanged() {

        User user = aRandomExistingUser();
        LocalDateTime oldTimeStamp = user.getUpdatedOn();
        EditUserRequest request = EditUserRequest
                .builder()
                .email("newEmail@gmail.com")
                .name(null)
                .address(null)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.editUser(user.getId(), request);

        assertEquals("newEmail@gmail.com", user.getEmail());
        assertEquals("testName", user.getName());
        assertEquals("testAddress", user.getAddress());
        assertThat(user.getUpdatedOn().isAfter(oldTimeStamp));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void givenOnlyNameForChanging_whenEditUser_thenOnlyTheNameIsChanged() {

        User user = aRandomExistingUser();
        LocalDateTime oldTimeStamp = user.getUpdatedOn();
        EditUserRequest request = EditUserRequest
                .builder()
                .email(null)
                .name("newName")
                .address(null)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.editUser(user.getId(), request);

        assertEquals("UserTestEmail@gmail.com", user.getEmail());
        assertEquals("newName", user.getName());
        assertEquals("testAddress", user.getAddress());
        assertThat(user.getUpdatedOn().isAfter(oldTimeStamp));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void givenOnlyAddressForChanging_whenEditUser_thenOnlyTheAddressIsChanged() {

        User user = aRandomExistingUser();
        LocalDateTime oldTimeStamp = user.getUpdatedOn();
        EditUserRequest request = EditUserRequest
                .builder()
                .email(null)
                .name(null)
                .address("newAddress")
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.editUser(user.getId(), request);

        assertEquals("UserTestEmail@gmail.com", user.getEmail());
        assertEquals("testName", user.getName());
        assertEquals("newAddress", user.getAddress());
        assertThat(user.getUpdatedOn().isAfter(oldTimeStamp));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void givenOnlyEmailAndAddressForEdit_whenEditUser_thenOnlyTheGivenFieldsAreChanged() {

        User user = aRandomExistingUser();
        LocalDateTime oldTimeStamp = user.getUpdatedOn();
        EditUserRequest request = EditUserRequest
                .builder()
                .email("newEmail@gmail.com")
                .name(null)
                .address("newAddress")
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.editUser(user.getId(), request);

        assertEquals("newEmail@gmail.com", user.getEmail());
        assertEquals("testName", user.getName());
        assertEquals("newAddress", user.getAddress());
        assertThat(user.getUpdatedOn().isAfter(oldTimeStamp));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void givenOnlyNameAndAddressForEdit_whenEditUser_thenOnlyTheGivenFieldsAreChanged() {

        User user = aRandomExistingUser();
        LocalDateTime oldTimeStamp = user.getUpdatedOn();
        EditUserRequest request = EditUserRequest
                .builder()
                .email(null)
                .name("newName")
                .address("newAddress")
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.editUser(user.getId(), request);

        assertEquals("UserTestEmail@gmail.com", user.getEmail());
        assertEquals("newName", user.getName());
        assertEquals("newAddress", user.getAddress());
        assertThat(user.getUpdatedOn().isAfter(oldTimeStamp));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void givenOnlyNameAndEmailForEdit_whenEditUser_thenOnlyTheGivenFieldsAreChanged() {

        User user = aRandomExistingUser();
        LocalDateTime oldTimeStamp = user.getUpdatedOn();
        EditUserRequest request = EditUserRequest
                .builder()
                .email("newEmail@gmail.com")
                .name("newName")
                .address(null)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.editUser(user.getId(), request);

        assertEquals("newEmail@gmail.com", user.getEmail());
        assertEquals("newName", user.getName());
        assertEquals("testAddress", user.getAddress());
        assertThat(user.getUpdatedOn().isAfter(oldTimeStamp));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void givenUnCorrectPassword_whenEditPassword_thenExceptionIsThrown() {

        User user = aRandomExistingUser();
        ChangePasswordRequest request = ChangePasswordRequest
                .builder()
                .oldPassword("oldPassword")
                .newPassword("newPassword")
                .confirmPassword("newPassword")
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(PasswordException.class, () -> userService.editPassword(user.getId(), request));
        verify(userRepository, never()).save(user);
    }

    @Test
    void givenNotMatchedNewPasswordAndConfirmPassword_whenEditPassword_thenExceptionIsThrown() {

        User user = aRandomExistingUser();
        ChangePasswordRequest request = ChangePasswordRequest
                .builder()
                .oldPassword("testPassword")
                .newPassword("newPassword")
                .confirmPassword("password")
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertThrows(PasswordException.class, () -> userService.editPassword(user.getId(), request));
        verify(userRepository, never()).save(user);
    }
}
