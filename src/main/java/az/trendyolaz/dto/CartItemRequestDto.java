package az.trendyolaz.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CartItemRequestDto {

    @NotNull(message = "Məhsul ID-si vacibdir")
    private Long productId;

    @NotNull(message = "Miqdar daxil edilməlidir")
    @NotNull(message = "Miqdar müsbət olmalıdır")
    private Integer quantity;


}
