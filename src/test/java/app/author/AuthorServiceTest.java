package app.author;

import app.author.model.Author;
import app.author.repository.AuthorRepository;
import app.author.service.AuthorService;
import app.exeptions.AuthorException;
import app.web.dto.AuthorRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static app.TestBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void givenAuthorNameThatIsAlreadyInDB_whenAddAuthor_thenExceptionIsThrown() {

        Author author = Author.builder()
                .name("authorName")
                .bio("authorBio")
                .build();

        AuthorRequest request = AuthorRequest.builder()
                .name("authorName")
                .build();

        when(authorRepository.findAuthorByName(request.getName())).thenReturn(Optional.of(author));

        assertThrows(AuthorException.class, () -> authorService.addAuthor(request));
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void addAuthorHappyPath() {

        AuthorRequest request = aRandomAuthorRequest();
        Author author = aRandomNewAuthor();

        when(authorRepository.findAuthorByName(request.getName())).thenReturn(Optional.empty());

        authorService.addAuthor(request);

        assertEquals(author.getName(), request.getName());
        assertEquals(author.getBio(), request.getBio());
        assertNull(author.getAuthorPic());
        assertThat(author.getBooks()).hasSize(0);
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void givenInvalidAuthorId_whenEditAuthor_thenExceptionIsThrown() {

        UUID invalidId = UUID.randomUUID();
        AuthorRequest request = aRandomAuthorRequest();

        when(authorRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(AuthorException.class, () -> authorService.editAuthor(invalidId, request));
        verify(authorRepository, never()).save(any(Author.class));
    }

    @Test
    void givenOnlyName_whenEditAuthor_thenOnlyTheNameIsUpdated() {

        AuthorRequest request = AuthorRequest.builder()
                .name("newAuthorName")
                .bio(null)
                .authorPic(null)
                .build();

        Author author = aRandomExistingAuthor();

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        authorService.editAuthor(author.getId(), request);

        assertEquals("newAuthorName", author.getName());
        assertEquals("authorBio", author.getBio());
        assertEquals("authorPic", author.getAuthorPic());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void givenOnlyBio_whenEditAuthor_thenOnlyTheBioIsUpdated() {

        AuthorRequest request = AuthorRequest.builder()
                .name(null)
                .bio("authorNewBio")
                .authorPic(null)
                .build();

        Author author = aRandomExistingAuthor();

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        authorService.editAuthor(author.getId(), request);

        assertEquals("authorName", author.getName());
        assertEquals("authorNewBio", author.getBio());
        assertEquals("authorPic", author.getAuthorPic());
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    void givenOnlyAuthorPic_whenEditAuthor_thenOnlyTheAuthorPicIsUpdated() {

        AuthorRequest request = AuthorRequest.builder()
                .name(null)
                .bio(null)
                .authorPic("newAuthorPic")
                .build();

        Author author = aRandomExistingAuthor();

        when(authorRepository.findById(author.getId())).thenReturn(Optional.of(author));

        authorService.editAuthor(author.getId(), request);

        assertEquals("authorName", author.getName());
        assertEquals("authorBio", author.getBio());
        assertEquals("newAuthorPic", author.getAuthorPic());
        verify(authorRepository, times(1)).save(author);
    }
}
