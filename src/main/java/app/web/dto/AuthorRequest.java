package app.web.dto;

import app.web.validation.OnCreate;
import app.web.validation.OnUpdate;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
@Builder
public class AuthorRequest {

    @NotEmpty(groups = OnCreate.class, message = "Name is required")
    private String name;

    @NotEmpty(groups = OnCreate.class, message = "Bio is required")
    private String bio;

    @URL(groups = {OnCreate.class, OnUpdate.class} ,message = "Author pic requires correct web link format")
    private String authorPic;
}
