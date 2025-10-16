package app.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EditAuthorRequest {

    private String name;
    private String bio;
    private String authorPic;
}
