package app.author.model;

import app.book.model.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String bio;

    @Column(length = 1000)
    private String authorPic;

    @OneToMany
    private List<Book> books = new ArrayList<>();
}
