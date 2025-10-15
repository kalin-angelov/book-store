package app.book.service;

import app.author.model.Author;
import app.author.repository.AuthorRepository;
import app.book.model.Book;
import app.book.repository.BookRepository;
import app.exeptions.BookException;
import app.web.dto.AddBookRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Book addNewBook(AddBookRequest request) {

        Book book = initializeBook(request);
        if(request.getAuthorName() != null) {
            addAuthor(book, request.getAuthorName());
        }
        bookRepository.save(book);
        log.info("Book [%s] successfully added in DB".formatted(book.getTitle()));
        return book;
    }

    public Book getBook(UUID bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookException("Book not found"));
    }

    private Book initializeBook(AddBookRequest newBookRequest) {

        return Book.builder()
                .title(newBookRequest.getTitle())
                .genre(newBookRequest.getGenre())
                .type(newBookRequest.getType())
                .pages(newBookRequest.getPages())
                .numOfBooksInStoke(newBookRequest.getNumOfBooks())
                .addedOn(LocalDateTime.now())
                .InStoke(true)
                .price(newBookRequest.getPrice())
                .language(newBookRequest.getLanguage())
                .build();
    }

    private void addAuthor(Book book, String authorName) {
        Optional<Author> optionalAuthor = authorRepository.findAuthorByName(authorName);
        if (optionalAuthor.isPresent()) {
            book.setAuthor(optionalAuthor.get());
            book.setAuthorName(authorName);
        } else {
            book.setAuthorName(authorName);
        }
    }

    public List<Book> getLatestAddedBooks() {

        return bookRepository.findLatestAddedBooks();
    }
}
