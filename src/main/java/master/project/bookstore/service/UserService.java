package master.project.bookstore.service;

import master.project.bookstore.entity.User;
import master.project.bookstore.exception.UserAlreadyExistsException;
import master.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return userRepository.save(user);
    }
    public boolean loginUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return true; // Login successful
        }
        return false; // Login failed
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
