package app.web.dto;

import app.book.model.BookGenre;
import app.book.model.BookType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@Data
@Builder
public class BookRequest {

    @NotEmpty(message = "Title is required")
    private String title;

    @NotEmpty(message = "Genre is required")
    private BookGenre genre;

    @NotEmpty(message = "Type is required")
    private BookType type;

    @NotEmpty(message = "Pages is required")
    private int pages;

    @NotEmpty(message = "Number of books is required")
    private int numOfBooks;

    @Positive
    @NotEmpty(message = "Price is required")
    private BigDecimal price;

    @NotEmpty(message = "Language is required")
    private String language;

    @NotEmpty(message = "Author is required")
    private String authorName;

    @NotEmpty(message = "Cover is required")
    @URL(message = "Requires correct web link format")
    private String bookCover;

    private boolean inStock;


}
