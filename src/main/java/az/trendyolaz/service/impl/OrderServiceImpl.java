package az.trendyolaz.service.impl;

import az.trendyolaz.dto.OrderRequestDto;
import az.trendyolaz.dto.OrderResponseDto;
import az.trendyolaz.entity.Order;
import az.trendyolaz.entity.OrderItem;
import az.trendyolaz.entity.Product;
import az.trendyolaz.entity.User;
import az.trendyolaz.enums.OrderStatus;
import az.trendyolaz.exception.InsufficientStockException;
import az.trendyolaz.exception.ProductNotFoundException;
import az.trendyolaz.mapper.OrderMapper;
import az.trendyolaz.repository.OrderRepository;
import az.trendyolaz.repository.ProductRepository;
import az.trendyolaz.repository.UserRepository;
import az.trendyolaz.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    public OrderResponseDto createOrder(OrderRequestDto requestDto , String username){
        log.info("Sifariş yaradılması başladı. İstifadəçi: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("İstifadəçi tapılmadı"));

        Order order  =  Order.builder()
                .user(user)
                .status(OrderStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .items(new ArrayList<>())
                .totalAmount(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for( var itemDto : requestDto.getItems()){
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(()->new ProductNotFoundException("Məhsul tapılmadı ID: " + itemDto.getProductId()));

            if(product.getStock()< itemDto.getQuantity()){
                log.error("Kafi stok yoxdur. Məhsul: {}, Stok: {}, İstənilən: {}",
                        product.getName() , product.getStock() , itemDto.getQuantity());
                throw new InsufficientStockException("Məhsul ehtiyatı kifayət etmir: " + product.getName());
            }

            product.setStock(product.getStock() - itemDto.getQuantity());
            productRepository.save(product);

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            total = total.add(itemTotal);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .price(product.getPrice())
                    .build();

            order.getItems().add(orderItem);
            }

        order.setTotalAmount(total);
        Order savedOrder = orderRepository.save(order);
        log.info("Sifariş uğurla yaradıldı. Order ID: {}", savedOrder.getId());

        return orderMapper.toResponseDto(savedOrder);


        }

        @Override

        public OrderResponseDto getOrderById(Long id) {
            return null;
        }

        @Override
    public List<OrderResponseDto> getOrderByUsername (String username){
        return List.of();
        }

    @Override
    public void cancelOrder(Long id) {

    }

    }
