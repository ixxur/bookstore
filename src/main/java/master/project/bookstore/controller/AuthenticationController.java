package master.project.bookstore.controller;

import master.project.bookstore.dto.UserRegistrationDto;
import master.project.bookstore.entity.User;
import master.project.bookstore.exception.UserAlreadyExistsException;
import master.project.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto registrationDto) {
        try {
            User user = userService.registerNewUser(registrationDto.getEmail(),
                    registrationDto.getUsername(),
                    registrationDto.getPassword());
            return ResponseEntity.ok("User registered successfully");
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/test-login")
    public ResponseEntity<String> testAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        String currentUserRole = authentication.getAuthorities().toString();
        return ResponseEntity.ok("Login successful! You are authenticated as: " + currentUserName + " " + currentUserRole);
    }
}
