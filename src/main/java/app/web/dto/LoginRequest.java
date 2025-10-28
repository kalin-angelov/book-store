package app.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequest {

    @NotEmpty(message = "Email is required")
    @Email(message = "Requires correct email format")
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;
}
