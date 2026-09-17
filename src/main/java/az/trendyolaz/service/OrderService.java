package az.trendyolaz.service;

import az.trendyolaz.dto.OrderRequestDto;
import az.trendyolaz.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(OrderRequestDto requestDto , String username);

    OrderResponseDto getOrderById(Long id);

    List<OrderResponseDto> getOrderByUsername(String username);

    void cancelOrder(Long id);

}
