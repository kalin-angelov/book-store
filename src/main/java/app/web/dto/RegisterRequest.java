package app.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {

    @NotEmpty(message = "Email is required")
    @Email(message = "Requires correct email format")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 5, max = 15, message = "Password must be between 5 and 15 symbols")
    private String password;

    private String confirmPassword;
}
