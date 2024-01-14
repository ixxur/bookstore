package master.project.bookstore.service;

import jakarta.transaction.Transactional;
import master.project.bookstore.entity.Book;
import master.project.bookstore.entity.Cart;
import master.project.bookstore.entity.CartItem;
import master.project.bookstore.entity.User;
import master.project.bookstore.repository.BookRepository;
import master.project.bookstore.repository.CartItemRepository;
import master.project.bookstore.repository.CartRepository;
import master.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    public boolean userHasCart(String username) {
        Optional<Cart> cart = cartRepository.findByUserUsername(username);
        return cart.isPresent();
    }
    public Cart addToCart(String username, String title, int quantity) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Book book = bookRepository.findByTitle(title)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (quantity > book.getStock()) {
            throw new Exception("Requested quantity exceeds book stock");
        }

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
            cart = cartRepository.save(cart);
            userRepository.save(user);
        }

        final Cart finalCart = cart;
        CartItem cartItem = cartItemRepository.findByCartIdAndBookId(cart.getId(), book.getId())
                .orElseGet(() -> new CartItem(finalCart, book, 0));

        int totalQuantity = cartItem.getQuantity() + quantity;

        if (totalQuantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(totalQuantity);
            cartItemRepository.save(cartItem);
        }

        return cart;
    }
    @Transactional
    public void emptyCart(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Cart cart = cartRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cartItemRepository.deleteAllByCartId(cart.getId());
    }
}
