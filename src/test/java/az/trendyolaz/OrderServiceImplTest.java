package az.trendyolaz;


import az.trendyolaz.dto.OrderItemRequestDto;
import az.trendyolaz.dto.OrderRequestDto;
import az.trendyolaz.entity.Product;
import az.trendyolaz.entity.User;
import az.trendyolaz.exception.InsufficientStockException;
import az.trendyolaz.mapper.OrderMapper;
import az.trendyolaz.repository.OrderRepository;
import az.trendyolaz.repository.ProductRepository;
import az.trendyolaz.repository.UserRepository;
import az.trendyolaz.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {
        User user = new User();
        user.setUsername("testuser");

        Product product = new Product();
        product.setId(1L);
        product.setStock(2);
        product.setDiscountedPrice(BigDecimal.TEN);

        OrderItemRequestDto itemDto = new OrderItemRequestDto(1L, 5);

        OrderRequestDto orderRequestDto = OrderRequestDto.builder()
                .items(List.of(itemDto))
                .build();

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThrows(InsufficientStockException.class, () -> orderService.createOrder(orderRequestDto, "testuser"));
    }
}