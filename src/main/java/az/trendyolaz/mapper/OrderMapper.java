package az.trendyolaz.mapper;



import az.trendyolaz.dto.OrderResponseDto;
import az.trendyolaz.entity.Order;
import org.mapstruct.Mapper;

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

        return OrderResponseDto.builder()
                .id(order.getId())
                .userId(userId)
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus() != null ? order.getStatus().name() : null)
                .createdAt(order.getCreatedAt())
                .build();
    }
}