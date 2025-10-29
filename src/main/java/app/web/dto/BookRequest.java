package app.web.dto;

import app.book.model.BookGenre;
import app.book.model.BookType;
import app.web.validation.OnCreate;
import app.web.validation.OnUpdate;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Data
@Builder
public class BookRequest {

    @NotEmpty(groups = OnCreate.class, message = "Title is required")
    private String title;

    @NotEmpty(groups = OnCreate.class, message = "Genre is required")
    private BookGenre genre;

    @NotEmpty(groups = OnCreate.class, message = "Type is required")
    private BookType type;

    @NotEmpty(groups = OnCreate.class, message = "Pages is required")
    private int pages;

    @NotEmpty(groups = OnCreate.class, message = "Number of books is required")
    private int numOfBooks;

    @Positive(groups = {OnCreate.class, OnUpdate.class})
    @NotEmpty(groups = OnCreate.class, message = "Price is required")
    private BigDecimal price;

    @NotEmpty(groups = OnCreate.class, message = "Language is required")
    private String language;

    @NotEmpty(groups = OnCreate.class, message = "Author is required")
    private String authorName;

    @URL(groups = OnUpdate.class, message = "Requires correct web link format")
    private String bookCover;

    private boolean inStock;


}
