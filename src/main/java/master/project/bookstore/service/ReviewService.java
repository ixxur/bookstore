package master.project.bookstore.service;

import master.project.bookstore.dto.ReviewDto;
import master.project.bookstore.entity.Book;
import master.project.bookstore.entity.Review;
import master.project.bookstore.entity.User;
import master.project.bookstore.repository.BookRepository;
import master.project.bookstore.repository.ReviewRepository;
import master.project.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;
    public Review createReview(ReviewDto reviewDto) {
        Book book = bookRepository.findByTitle(reviewDto.getBookTitle())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        User user = userRepository.findByUsername(reviewDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setBook(book);
        review.setUser(user);
        review.setRating(reviewDto.getRating());
        review.setComment(reviewDto.getComment());
        review.setCreatedAt(new Date());

        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByBookTitle(String title) {
        return reviewRepository.findByBookTitle(title);
    }

    public void deleteReview(Long reviewId) {
        if (!reviewRepository.existsById(reviewId)) {
            throw new RuntimeException("Review not found with ID: " + reviewId);
        }
        reviewRepository.deleteById(reviewId);
    }
}
