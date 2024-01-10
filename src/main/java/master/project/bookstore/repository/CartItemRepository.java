package master.project.bookstore.repository;

import master.project.bookstore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long id);

    Optional<CartItem> findByCartIdAndBookId(Long id, Long bookId);

    // Add necessary query methods
}
