package master.project.bookstore.service;
import master.project.bookstore.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void whenReviewExists_thenDeleteReview() {
        Long reviewId = 1L;

        when(reviewRepository.existsById(reviewId)).thenReturn(true);

        reviewService.deleteReview(reviewId);

        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    @Test
    public void whenReviewDoesNotExist_thenThrowException() {
        Long reviewId = 1L;

        when(reviewRepository.existsById(reviewId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            reviewService.deleteReview(reviewId);
        }, "RuntimeException should be thrown when the review is not found");
    }
}
