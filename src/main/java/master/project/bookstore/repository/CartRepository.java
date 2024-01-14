package master.project.bookstore.repository;

import master.project.bookstore.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository  extends JpaRepository<Cart, Long> {
    @Query("select c from Cart c where c.user.username = :username")
    Optional<Cart> findByUserUsername(@Param("username") String username);
}
