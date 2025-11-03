package app;

import app.author.model.Author;
import app.book.model.Book;
import app.book.model.BookGenre;
import app.book.model.BookType;
import app.user.model.User;
import app.user.model.UserRole;
import app.web.dto.AuthorRequest;
import app.web.dto.BookRequest;
import app.web.dto.LoginRequest;
import app.web.dto.RegisterRequest;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class TestBuilder {

    // DTO requests - start
    public static RegisterRequest aRandomRegisterRequest() {
        return RegisterRequest.builder()
                .email("UserTestEmail@gmail.com")
                .password("testPassword")
                .confirmPassword("testPassword")
                .build();
    }

    public static LoginRequest aRandomLoginRequest() {
        return LoginRequest.builder()
                .email("UserTestEmail@gmail.com")
                .password("testPassword")
                .build();
    }

    public static AuthorRequest aRandomAuthorRequest() {
        return  AuthorRequest.builder()
                .name("authorName")
                .bio("authorBio")
                .build();
    }

    public static BookRequest aRandomBookRequest() {
        return BookRequest.builder()
                .title("bookTitle")
                .genre(BookGenre.DRAMA)
                .type(BookType.HARDCOVER)
                .pages(255)
                .numOfBooks(101)
                .inStock(true)
                .price(BigDecimal.valueOf(25))
                .language("bookLanguage")
                .authorName("bookAuthor")
                .build();
    }
    // DTO requests - end

    //User - start
    public static User aRandomExistingUser() {
        return User.builder()
                .id(UUID.randomUUID())
                .role(UserRole.USER)
                .email("UserTestEmail@gmail.com")
                .password("testPassword")
                .name("testName")
                .address("testAddress")
                .isActive(true)
                .registerOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .orders(new ArrayList<>())
                .build();
    }

    public static User aRandomNewUser() {
        return User.builder()
                .role(UserRole.USER)
                .email("UserTestEmail@gmail.com")
                .password("testPassword")
                .isActive(true)
                .registerOn(LocalDateTime.now())
                .updatedOn(LocalDateTime.now())
                .orders(new ArrayList<>())
                .build();
    }
    // User - end

    // Author - start
    public static Author aRandomNewAuthor() {
        return Author.builder()
                .name("authorName")
                .bio("authorBio")
                .authorPic(null)
                .books(new ArrayList<>())
                .build();
    }

    public static Author aRandomExistingAuthor() {
        return Author.builder()
                .id(UUID.randomUUID())
                .name("authorName")
                .bio("authorBio")
                .authorPic("authorPic")
                .books(List.of(new Book(), new Book()))
                .build();
    }
    // Author - end

    // Book - start
    public static Book aRandomNewBook() {
        return Book.builder()
                .id(UUID.randomUUID())
                .title("bookTitle")
                .genre(BookGenre.DRAMA)
                .type(BookType.HARDCOVER)
                .pages(255)
                .numOfBooksInStoke(101)
                .addedOn(LocalDateTime.now())
                .InStock(true)
                .price(BigDecimal.valueOf(25))
                .language("bookLanguage")
                .build();
    }

    //Book - end
}
