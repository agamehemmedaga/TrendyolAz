package az.trendyolaz.service.impl;



import az.trendyolaz.dto.OrderRequestDto;
import az.trendyolaz.dto.OrderResponseDto;
import az.trendyolaz.entity.Order;
import az.trendyolaz.entity.OrderItem;
import az.trendyolaz.enums.OrderStatus;
import az.trendyolaz.entity.Product;
import az.trendyolaz.entity.User;
import az.trendyolaz.exception.InsufficientStockException;
import az.trendyolaz.exception.OrderNotFoundException;
import az.trendyolaz.exception.ProductNotFoundException;
import az.trendyolaz.exception.UsernameNotFoundException;
import az.trendyolaz.mapper.OrderMapper;
import az.trendyolaz.repository.OrderRepository;
import az.trendyolaz.repository.ProductRepository;
import az.trendyolaz.repository.UserRepository;
import az.trendyolaz.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, String username) {
        log.info("Sifariş yaradılması başladı. İstifadəçi: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("İstifadəçi tapılmadı: " + username));

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.CREATED)
                .items(new ArrayList<>())
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (var itemDto : requestDto.getItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException("Məhsul tapılmadı ID: " + itemDto.getProductId()));

            if (product.getStock() < itemDto.getQuantity()) {
                log.error("Kafi stok yoxdur. Məhsul: {}, Stok: {}, İstənilən: {}",
                        product.getName(), product.getStock(), itemDto.getQuantity());
                throw new InsufficientStockException("Məhsul ehtiyatı kifayət etmir: " + product.getName());
            }

            product.setStock(product.getStock() - itemDto.getQuantity());
            productRepository.save(product);

            // Endirimli qiymət məntiqi: discountedPrice varsa onu, yoxdursa ana price-i götürürük
            BigDecimal effectivePrice = (product.getDiscountedPrice() != null)
                    ? product.getDiscountedPrice()
                    : product.getPrice();

            BigDecimal itemTotal = effectivePrice.multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            total = total.add(itemTotal);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .price(effectivePrice)
                    .build();

            order.getItems().add(orderItem);
        }

        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);
        log.info("Sifariş uğurla yaradıldı. Order ID: {}", savedOrder.getId());

        return orderMapper.toResponseDto(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Sifariş tapılmadı ID: " + id));
        return orderMapper.toResponseDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrderByUsername(String username) {
        List<Order> orders = orderRepository.findByUserUsername(username);
        return orders.stream()
                .map(orderMapper::toResponseDto)
                .toList();
    }

    @Override
    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Sifariş tapılmadı ID: " + id));

        if (order.getStatus() == OrderStatus.CANCELED) {
            throw new IllegalStateException("Sifariş artıq ləğv edilib.");
        }

        // Ləğv edilən sifarişdəki məhsulların stokunu geri qaytarırıq
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
        log.info("Sifariş ləğv edildi. Order ID: {}", id);
    }
}