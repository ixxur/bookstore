package master.project.bookstore.service;

import master.project.bookstore.entity.Book;
import master.project.bookstore.entity.Cart;
import master.project.bookstore.entity.CartItem;
import master.project.bookstore.entity.User;
import master.project.bookstore.repository.BookRepository;
import master.project.bookstore.repository.CartItemRepository;
import master.project.bookstore.repository.CartRepository;
import master.project.bookstore.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CartServiceTest {
    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUserHasCart_WhenCartExists() {
        String username = "ruxi";
        when(cartRepository.findByUserUsername(username)).thenReturn(Optional.of(new Cart()));

        boolean result = cartService.userHasCart(username);

        assertTrue(result);
        verify(cartRepository).findByUserUsername(username);
    }

    @Test
    void testUserHasCart_WhenCartDoesNotExist() {
        String username = "user";
        when(cartRepository.findByUserUsername(username)).thenReturn(Optional.empty());

        boolean result = cartService.userHasCart(username);

        assertFalse(result);
        verify(cartRepository).findByUserUsername(username);
    }

    @Test
    void testAddToCart_NewItem() throws Exception {
        String username = "maria";
        String title = "Pride and Prejudice";
        int quantity = 1;

        User user = new User();
        user.setId(10L);
        Cart cart = new Cart();
        cart.setId(4L);
        cart.setUser(user);
        user.setCart(cart);

        Book book = new Book();
        book.setId(12L);
        book.setStock(10);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(bookRepository.findByTitle(title)).thenReturn(Optional.of(book));
        when(cartRepository.findByUserUsername(username)).thenReturn(Optional.empty());

        cart = cartService.addToCart(username, title, quantity);

        assertNotNull(cart);
        verify(cartItemRepository).save(ArgumentMatchers.any(CartItem.class));
    }

    @Test
    void testAddToCart_QuantityExceedsStock() {
        String username = "ruxi";
        String title = "The Alchemist";
        int quantity = 70;

        User user = new User();
        Book book = new Book();
        book.setStock(10);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(bookRepository.findByTitle(title)).thenReturn(Optional.of(book));

        Exception exception = assertThrows(Exception.class, () -> {
            cartService.addToCart(username, title, quantity);
        });

        String expectedMessage = "Requested quantity exceeds book stock";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testEmptyCart_Successful() {
        String username = "ruxi";
        User user = new User();
        Cart cart = new Cart();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(cartRepository.findByUserUsername(username)).thenReturn(Optional.of(cart));

        cartService.emptyCart(username);

        verify(cartItemRepository).deleteAllByCartId(cart.getId());
    }

    @Test
    void testEmptyCart_UserNotFound() {
        String username = "user1234";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> {
            cartService.emptyCart(username);
        });

        String expectedMessage = "User not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
