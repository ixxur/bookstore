package master.project.bookstore.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Arrays;
import java.util.List;

import master.project.bookstore.entity.Book;
import master.project.bookstore.service.BookService;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {
    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }
    @Test
    @WithMockUser(username = "maria", roles = "USER")
    public void listBooks_ShouldReturnAllBooks() throws Exception {
        List<Book> expectedBooks = Arrays.asList(new Book(), new Book());
        given(bookService.listBooks()).willReturn(expectedBooks);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedBooks.size())));
    }
}
