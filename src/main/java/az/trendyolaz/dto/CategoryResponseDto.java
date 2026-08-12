package az.trendyolaz.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CategoryResponseDto {
    private Long id;
    private String name;
}
