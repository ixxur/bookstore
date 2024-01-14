package master.project.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import master.project.bookstore.dto.ReviewDto;
import master.project.bookstore.entity.Review;
import master.project.bookstore.entity.User;
import master.project.bookstore.repository.BookRepository;
import master.project.bookstore.repository.ReviewRepository;
import master.project.bookstore.repository.UserRepository;
import master.project.bookstore.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
//import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private UserRepository userRepository;
    @InjectMocks
    private ReviewController reviewController;


    @Test
    @WithMockUser(username = "maria", roles = "USER")
    public void testGetReviewsByBookTitle_Success() throws Exception {
        String title = "Pride and Prejudice";
        List<Review> reviews = Arrays.asList(new Review());

        when(reviewService.getReviewsByBookTitle(title)).thenReturn(reviews);

        mockMvc.perform(get("/reviews/book/" + title))
                .andExpect(status().isOk())
                .andExpect( jsonPath("$", hasSize(1)));
    }

}
