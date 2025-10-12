package app.author.service;

import app.author.model.Author;
import app.author.repository.AuthorRepository;
import app.book.model.Book;
import app.exeptions.AuthorException;
import app.web.dto.AddAuthorRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Author addAuthor(AddAuthorRequest request) {

        authorRepository.findAuthorByName(request.getName()).orElseThrow(() -> new AuthorException("This author [%s] is already in the DB".formatted(request.getName())));

        Author author = initializeAuthor(request);
        authorRepository.save(author);
        log.info("Author [%s] successfully added in DB".formatted(author.getName()));
        return author;
    }

    public Author getAuthor(UUID authorId) {
        return authorRepository.findById(authorId).orElseThrow(() -> new AuthorException("Author not found."));
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Book> getAllAuthorBooks(UUID authorId) {
        Author author = getAuthor(authorId);
        return author.getBooks();
    }

    private Author initializeAuthor(AddAuthorRequest newAuthorRequest) {
        return Author.builder()
                .name(newAuthorRequest.getName())
                .bio(newAuthorRequest.getBio())
                .build();
    }
}
