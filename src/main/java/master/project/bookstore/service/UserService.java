package master.project.bookstore.service;

import master.project.bookstore.entity.Cart;
import master.project.bookstore.entity.User;
import master.project.bookstore.exception.UserAlreadyExistsException;
import master.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(String email, String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Error: Username is already taken");
        }

        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Error: Email is already in use");
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(password));
        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);
        return userRepository.save(user);
    }
    public boolean loginUser(String username, String password) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return true; // Login successful
            }
        }
        return false; // Login failed
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Cart getCartByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        return user.getCart();
    }
}
