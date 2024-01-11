package master.project.bookstore.service;

import jakarta.transaction.Transactional;
import master.project.bookstore.entity.*;
import master.project.bookstore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Order createOrder(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Cart not found for user"));

        if (cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot create an order from an empty cart");
        }

        // Create a new Order object
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus("PROCESSING");

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderDetail> orderDetails = new ArrayList<>();

        // Iterate over CartItems, create OrderDetails, update book stock
        for (CartItem cartItem : cart.getItems()) {
            Book book = cartItem.getBook();
            int quantity = cartItem.getQuantity();

            if (book.getStock() < quantity) {
                throw new RuntimeException("Insufficient stock for book: " + book.getTitle());
            }

            // Update book stock
            book.setStock(book.getStock() - quantity);
            bookRepository.save(book);

            // Create and add OrderDetail
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setBook(book);
            orderDetail.setQuantity(quantity);
            orderDetail.setPrice(book.getPrice().multiply(new BigDecimal(quantity)));
            orderDetails.add(orderDetail);

            // Add to total price
            totalPrice = totalPrice.add(orderDetail.getPrice());
        }

        // Calculate total price
        order.setTotalPrice(totalPrice);

        // Set order details and save the order
        order.setOrderDetails(orderDetails);
        Order savedOrder = orderRepository.save(order);

        // Save order details
        orderDetailRepository.saveAll(orderDetails);

        // Clear the cart
        cart.getItems().clear();
        cartRepository.save(cart);

        // Return the saved order
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrders(String username) {
        return orderRepository.findByUsername(username);
    }
}
