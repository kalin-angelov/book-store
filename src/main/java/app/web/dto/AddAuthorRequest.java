package app.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddAuthorRequest {

    @NotNull(message = "Field can't be null")
    @NotBlank(message = "All fields are required")
    private String name;

    @NotNull(message = "Field can't be null")
    @NotBlank(message = "All fields are required")
    private String bio;
}
