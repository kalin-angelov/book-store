package app.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {

    private String email;
    private String password;
    private String confirmPassword;
}
