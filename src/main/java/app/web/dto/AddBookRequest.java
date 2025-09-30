package app.web.dto;

import app.book.model.BookGenre;
import app.book.model.BookType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AddBookRequest {

    private String title;
    private BookGenre genre;
    private BookType type;
    private int pages;
    private int numOfBooks;
    private BigDecimal price;
    private String language;
    private String authorName;
}
