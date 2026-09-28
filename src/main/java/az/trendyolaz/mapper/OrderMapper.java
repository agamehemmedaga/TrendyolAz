package az.trendyolaz.mapper;




import az.trendyolaz.dto.OrderItemDto;
import az.trendyolaz.dto.OrderResponseDto;
import az.trendyolaz.entity.Order;
import az.trendyolaz.entity.OrderItem;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    default OrderResponseDto toResponseDto(Order order) {
        if (order == null) {
            return null;
        }

        Long userId = null;
        if (order.getUser() != null) {
            userId = order.getUser().getId();
        }

        List<OrderItemDto> itemDtos = Collections.emptyList();
        if (order.getItems() != null) {
            itemDtos = order.getItems().stream()
                    .map(this::toItemDto)
                    .toList();
        }

        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(userId)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus() != null ? order.getStatus().name() : null)
                .createdAt(order.getCreatedAt())
                .items(itemDtos)
                .build();
    }

    default OrderItemDto toItemDto(OrderItem item) {
        if (item == null) {
            return null;
        }

        Long productId = null;
        String productName = null;
        if (item.getProduct() != null) {
            productId = item.getProduct().getId();
            productName = item.getProduct().getName();
        }

        return OrderItemDto.builder()
                .id(item.getId())
                .productId(productId)
                .productName(productName)
                .quantity(item.getQuantity())
                .unitPrice(item.getPrice())
                .totalPrice(item.getPrice() != null && item.getQuantity() != null
                        ? item.getPrice().multiply(java.math.BigDecimal.valueOf(item.getQuantity()))
                        : null)
                .build();
    }
}