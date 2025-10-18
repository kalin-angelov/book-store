package app.book.service;

import app.author.model.Author;
import app.author.repository.AuthorRepository;
import app.book.model.Book;
import app.book.repository.BookRepository;
import app.exeptions.BookException;
import app.web.dto.AddBookRequest;
import app.web.dto.EditBookRequest;
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

    public Book getBookById(UUID bookId) {

        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()) {
            log.info("Book with id-[%s] not found in DB".formatted(bookId));
            throw  new BookException("Book not found in DB");
        }

        return optionalBook.get();
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

    public Book editBook(UUID bookId, EditBookRequest request) {

        Book book = getBookById(bookId);

        if (request.getTitle() != null) book.setTitle(request.getTitle());
        if (request.getBookCover() != null) book.setBookCover(request.getBookCover());
        if (request.getGenre() != null) book.setGenre(request.getGenre());
        if (request.getType() != null) book.setType(request.getType());
        if (request.getPages() != book.getPages()) book.setPages(request.getPages());
        if (request.getNumOfBooks() != book.getNumOfBooksInStoke()) book.setNumOfBooksInStoke(request.getNumOfBooks());
        if (request.isInStoke() != book.isInStoke()) book.setInStoke(request.isInStoke());
        if (request.getPrice() != null) book.setPrice(request.getPrice());
        if (request.getLanguage() != null) book.setLanguage(request.getLanguage());
        if (request.getAuthorName() != null) book.setAuthorName(request.getAuthorName());

        bookRepository.save(book);
        log.info("Book with id [%s] successfully edited".formatted(bookId));
        return book;
    }
}
