package master.project.bookstore.controller;
import master.project.bookstore.dto.UserRegistrationDto;
import master.project.bookstore.entity.User;
import master.project.bookstore.exception.UserAlreadyExistsException;
import master.project.bookstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void registerUser_Success() {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("test@test.com");
        registrationDto.setUsername("test");
        registrationDto.setPassword("test");

        when(userService.registerNewUser(anyString(), anyString(), anyString())).thenReturn(new User());

        ResponseEntity<?> response = authenticationController.registerUser(registrationDto);

        assertEquals(200, response.getStatusCodeValue(), "Response status should be OK (200)");
        assertEquals("User registered successfully", response.getBody(), "Response body should indicate successful registration");
    }

    @Test
    public void registerUser_UserAlreadyExists() {
        UserRegistrationDto registrationDto = new UserRegistrationDto();
        registrationDto.setEmail("ruxi@ruxi.com");
        registrationDto.setUsername("ruxi");
        registrationDto.setPassword("ruxi");

        when(userService.registerNewUser(anyString(), anyString(), anyString())).thenThrow(new UserAlreadyExistsException("User already exists"));

        ResponseEntity<?> response = authenticationController.registerUser(registrationDto);

        assertEquals(400, response.getStatusCodeValue(), "Response status should be Bad Request (400)");
        assertTrue(response.getBody().toString().contains("User already exists"), "Response body should indicate user already exists");
    }

    @Test
    public void testAuthentication_AuthenticatedUser() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("ruxi");

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        doReturn(authorities).when(authentication).getAuthorities();

        ResponseEntity<String> response = authenticationController.testAuthentication();

        assertEquals(200, response.getStatusCodeValue(), "Response status should be OK (200)");
        assertTrue(response.getBody().contains("Login successful! You are authenticated as: ruxi [ROLE_USER]"), "Response body should include authentication details");
    }
}
