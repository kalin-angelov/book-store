package app.web;

import app.author.model.Author;
import app.author.service.AuthorService;
import app.book.model.Book;
import app.web.dto.AuthorRequest;
import app.web.dto.Response;
import app.web.validation.OnCreate;
import app.web.validation.OnUpdate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/add-author")
    public ResponseEntity<Response> addNewAuthor(@Validated(OnCreate.class) @RequestBody AuthorRequest request) {

        Author author = authorService.addAuthor(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.builder()
                        .message("Author [%s] successfully added in DB".formatted(author.getName()))
                        .build());
    }

    @GetMapping("/{authorId}/bio")
    public Author getAuthor(@PathVariable UUID authorId) {
        return authorService.getAuthorById(authorId);
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{authorId}/author-books")
    public List<Book> getAllAuthorBooks(@PathVariable UUID authorId) {

        return authorService.getAllAuthorBooks(authorId);
    }

    @PutMapping("/{authorId}/edit-author")
    public ResponseEntity<Response> editAuthor(@PathVariable UUID authorId,@Validated(OnUpdate.class) @RequestBody AuthorRequest request) {

        Author author = authorService.editAuthor(authorId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.builder()
                        .message("Author [%s] successfully edited".formatted(author.getName()))
                        .build());
    }
}
