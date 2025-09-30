package app.book.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String bookCover;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookGenre genre;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BookType type;

    @Column(nullable = false)
    private int pages;

    @Column(nullable = false)
    private int numOfBooksInStoke;

    @Column(nullable = false)
    private boolean InStoke;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String language;

    @Column(nullable = false)
    private String authorName;
}
