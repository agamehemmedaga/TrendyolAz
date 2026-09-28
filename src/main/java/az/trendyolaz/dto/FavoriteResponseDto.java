package az.trendyolaz.dto;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteResponseDto {

    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal originalPrice;
    private BigDecimal discountedPrice;
    private String categoryName;
    private LocalDateTime addedAt;
}