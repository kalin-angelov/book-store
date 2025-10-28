package app.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequest {

    @NotEmpty(message = "Old pass is required")
    private String oldPassword;

    @NotEmpty(message = "New pass is required")
    @Size(min = 5, max = 15, message = "Password must be between 5 and 15 symbols")
    private String newPassword;

    private String confirmPassword;
}
