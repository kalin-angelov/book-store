package app.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditUserRequest {

    @NotEmpty(message = "Email is required")
    @Email(message = "Requires correct email format")
    private String email;

    @NotEmpty(message = "Name is required")
    private String name;

    private String address;
}
