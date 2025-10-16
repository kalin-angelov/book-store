package app.web;

import app.book.model.Book;
import app.book.service.BookService;
import app.web.dto.AddBookRequest;
import app.web.dto.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    @PostMapping("/add-book")
    public ResponseEntity<Response> addNewBook(@RequestBody AddBookRequest request) {

        Book book = bookService.addNewBook(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Response.builder()
                        .message("Book with title [%s] successfully added in DB".formatted(book.getTitle()))
                        .build());
    }

    @GetMapping
    public Book getBookDetails(@RequestParam(name = "bookId") UUID bookId) {

        return bookService.getBook(bookId);
    }

    @GetMapping("/latest-books")
    public List<Book> latestAddedBooks() {

        return bookService.getLatestAddedBooks();
    }
}
