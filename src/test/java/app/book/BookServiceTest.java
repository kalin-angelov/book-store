package app.book;

import app.author.repository.AuthorRepository;
import app.book.model.Book;
import app.book.repository.BookRepository;
import app.book.service.BookService;
import app.exeptions.BookException;
import app.web.dto.BookRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;
import java.util.UUID;

import static app.TestBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void addBookHappyPath() {

        BookRequest request = aRandomBookRequest();

        Book newBook = bookService.addNewBook(request);

        assertTrue(newBook.isInStock());
        assertEquals(newBook.getType(), request.getType());
        assertEquals(newBook.getTitle(), request.getTitle());
        assertEquals(newBook.getGenre(), request.getGenre());
        assertEquals(newBook.getPages(), request.getPages());
        assertEquals(newBook.getPrice(), request.getPrice());
        assertEquals(newBook.getLanguage(), request.getLanguage());
        assertEquals(newBook.getBookCover(), request.getBookCover());
        assertEquals(newBook.getAuthorName(), request.getAuthorName());
        assertEquals(newBook.getNumOfBooksInStoke(), request.getNumOfBooks());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void givenUnValidBookId_whenGetBookById_thenExceptionIsThrown() {

        UUID unValidId = UUID.randomUUID();

        when(bookRepository.findById(unValidId)).thenReturn(Optional.empty());

        assertThrows(BookException.class, () -> bookService.getBookById(unValidId));
    }

    @Test
    void getBookByIdHappyPath() {

        UUID unValidId = UUID.randomUUID();

        when(bookRepository.findById(unValidId)).thenReturn(Optional.empty());

        assertThrows(BookException.class, () -> bookService.getBookById(unValidId));
    }
}
