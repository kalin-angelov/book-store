package app.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditUserRequest {

    private String email;
    private String name;
    private String address;
}
