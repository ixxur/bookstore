//package master.project.bookstore.service;
//
//import master.project.bookstore.entity.Book;
//import master.project.bookstore.entity.Cart;
//import master.project.bookstore.entity.CartItem;
//import master.project.bookstore.entity.User;
//import master.project.bookstore.repository.BookRepository;
//import master.project.bookstore.repository.CartItemRepository;
//import master.project.bookstore.repository.CartRepository;
//import master.project.bookstore.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CartService {
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//    @Autowired
//    private BookRepository bookRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    public boolean userHasCart(String username) {
//        Optional<Cart> cart = cartRepository.findByUsername(username);
//        return cart.isPresent();
//    }
////    public Cart addToCart(String username, String title, int quantity) {
////        User user = userRepository.findByUsername(username)
////                .orElseThrow(new RuntimeException("User not found"));
////
////        Book book = bookRepository.findByTitle(title)
////                .orElseThrow(() -> new RuntimeException("Book not found"));
////
////        Cart cart;
////        if (userHasCart(username)) {
////            // Get the existing cart
////            cart = cartRepository.findByUsername(username).get();
////        } else {
////            // Create a new cart for the user
////            cart = new Cart();
////            cart.setUser(user);
////            cart = cartRepository.save(cart);
////        }
////
////        // Add or update the item in the cart
////        CartItem cartItem = cartItemRepository.findByCartIdAndBookId(cart.getId(), bookId)
////                .orElse(new CartItem(cart, book, 0));
////        cartItem.setQuantity(cartItem.getQuantity() + quantity);
////
////        cartItemRepository.save(cartItem);
////
////        return cart;
////    }
//
//}
