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
        bookRepository.save(book);

        Optional<Author> optionalAuthor = authorRepository.findAuthorByName(request.getAuthorName());
        if (optionalAuthor.isPresent()) {
            Author author = optionalAuthor.get();
            List<Book> authorBooks = author.getBooks();
            authorBooks.add(book);
            author.setBooks(authorBooks);
            authorRepository.save(author);
        }
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
                .InStoke(true)
                .price(newBookRequest.getPrice())
                .language(newBookRequest.getLanguage())
                .authorName(newBookRequest.getAuthorName())
                .build();
    }

}
