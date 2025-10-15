package app.book.repository;

import app.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    @Query(
        value = "SELECT * FROM book ORDER BY added_on DESC LIMIT 10",
        nativeQuery = true
    )
    List<Book> findLatestAddedBooks();
}
