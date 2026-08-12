package az.trendyolaz.dto;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ProductResponseDto {
    private Long id;
    private String name;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;
    private Long categoryId;
    private String categoryName;
}
