package az.trendyolaz.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProductSearchDto {

    private String name;
    private Long categoryId;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;


}
