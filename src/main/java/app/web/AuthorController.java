package app.web;

import app.author.model.Author;
import app.author.service.AuthorService;
import app.book.model.Book;
import app.web.dto.AddAuthorRequest;
import app.web.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/add-author")
    public ResponseEntity<Response> addNewAuthor(@RequestBody AddAuthorRequest request) {

        Author author = authorService.addAuthor(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.builder()
                        .message("Author [%s] successfully added in DB".formatted(author.getName()))
                        .build());
    }

    @GetMapping("/bio")
    public Author getAuthor(@RequestParam(name = "authorId")UUID authorId) {
        return authorService.getAuthor(authorId);
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/author-books")
    public List<Book> getAllAuthorBooks(@RequestParam(name = "authorId") UUID authorId) {

        return authorService.getAllAuthorBooks(authorId);
    }
}
