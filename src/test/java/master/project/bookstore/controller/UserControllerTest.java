package master.project.bookstore.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

import master.project.bookstore.entity.User;
import master.project.bookstore.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
    @Test
    public void getUserByUsername_ShouldReturnUser() throws Exception {
        String username = "ruxi";
        User user = new User();
        user.setUsername(username);

        given(userService.findUserByUsername(username)).willReturn(user);

        mockMvc.perform(get("/users/" + username))
                .andExpect(status().isOk());
    }
}
