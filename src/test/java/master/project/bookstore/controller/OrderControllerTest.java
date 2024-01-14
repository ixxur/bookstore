package master.project.bookstore.controller;

import master.project.bookstore.entity.Order;
import master.project.bookstore.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void getAllOrders_WithAdminRole_ShouldReturnOrders() throws Exception {
        List<Order> expectedOrders = Arrays.asList(new Order(), new Order());
        given(orderService.getAllOrders()).willReturn(expectedOrders);

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(expectedOrders.size())));
    }
}
