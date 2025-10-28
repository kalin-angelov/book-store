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
}
