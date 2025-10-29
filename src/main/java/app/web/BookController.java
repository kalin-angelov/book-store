package app.web;

import app.book.model.Book;
import app.book.service.BookService;
import app.web.dto.BookRequest;
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
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @PostMapping("/add-book")
    public ResponseEntity<Response> addNewBook(@Validated(OnCreate.class) @RequestBody BookRequest request) {

        Book book = bookService.addNewBook(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.builder()
                        .message("Book with title [%s] successfully added in DB".formatted(book.getTitle()))
                        .build());
    }

    @PutMapping("/{bookId}/edit-book")
    public Book editBook(@PathVariable UUID bookId,@Validated(OnUpdate.class)  @RequestBody BookRequest request) {

        Book book = bookService.editBook(bookId, request);

        return book;
    }

    @GetMapping("/{bookId}")
    public Book getBookDetails(@PathVariable UUID bookId) {

        return bookService.getBookById(bookId);
    }

    @GetMapping("/latest-books")
    public List<Book> latestAddedBooks() {

        return bookService.getLatestAddedBooks();
    }
}
