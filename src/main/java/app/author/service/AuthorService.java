package app.author.service;

import app.author.model.Author;
import app.author.repository.AuthorRepository;
import app.book.model.Book;
import app.exeptions.AuthorException;
import app.web.dto.AddAuthorRequest;
import app.web.dto.EditAuthorRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public Author addAuthor(AddAuthorRequest request) {

        Optional<Author> optionalExistingAuthor = authorRepository.findAuthorByName(request.getName());

        if (optionalExistingAuthor.isPresent()) {
            log.info("Author with name-[%s] is already in DB".formatted(request.getName()));
            throw new AuthorException("This author [%s] is already in the DB".formatted(request.getName()));
        }

        Author author = initializeAuthor(request);
        authorRepository.save(author);
        log.info("Author [%s] successfully added in DB".formatted(author.getName()));
        return author;
    }

    public Author getAuthorById(UUID authorId) {

        Optional<Author> optionalAuthor = authorRepository.findById(authorId);

        if (optionalAuthor.isEmpty()) {
            log.info("Author with id-[%s] not found in DB".formatted(authorId));
            throw new AuthorException("Author not found in DB");
        }

        return optionalAuthor.get();
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<Book> getAllAuthorBooks(UUID authorId) {
        Author author = getAuthorById(authorId);
        return author.getBooks();
    }

    private Author initializeAuthor(AddAuthorRequest newAuthorRequest) {
        return Author.builder()
                .name(newAuthorRequest.getName())
                .bio(newAuthorRequest.getBio())
                .build();
    }

    public Author editAuthor(UUID authorId, EditAuthorRequest request) {

        Author author = getAuthorById(authorId);

        if (request.getName() != null) author.setName(request.getName());
        if (request.getBio() != null) author.setBio(request.getBio());
        if (request.getAuthorPic() != null) author.setAuthorPic(request.getAuthorPic());

        authorRepository.save(author);
        log.info("Author with id-[%s] successfully edited".formatted(authorId));
        return author;
    }
}
