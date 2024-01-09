package master.project.bookstore.repository;

import master.project.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository <Book, Long>{
    Optional<Book> findByTitleAndPublicationYear(String title, Integer publicationYear);
    Optional<Book> findByTitle(String title);
}
