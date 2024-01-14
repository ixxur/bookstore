package master.project.bookstore.service;
import master.project.bookstore.entity.Order;
import master.project.bookstore.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void whenGetOrders_thenReturnListOfOrders() {
        String username = "andrei";
        Order mockOrder1 = new Order();
        Order mockOrder2 = new Order();

        List<Order> mockOrders = Arrays.asList(mockOrder1, mockOrder2);
        when(orderRepository.findByUsername(username)).thenReturn(mockOrders);

        List<Order> returnedOrders = orderService.getOrders(username);

        assertEquals(mockOrders.size(), returnedOrders.size(), "The size of returned orders should match the mock orders");
        assertIterableEquals(mockOrders, returnedOrders, "The returned orders should match the mock orders");
    }
}
