package app.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddAuthorRequest {

    private String name;
    private String bio;
}
