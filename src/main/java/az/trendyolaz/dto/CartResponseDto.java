package az.trendyolaz.dto;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder



public class CartResponseDto {
    private Long id;
    private String username;
    private List<CartItemResponseDto> items;
    private BigDecimal grandTotal;
}
