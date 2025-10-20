package app.web.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
