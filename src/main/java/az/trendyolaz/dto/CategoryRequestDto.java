package az.trendyolaz.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CategoryRequestDto {
    @NotBlank(message = "Kateqoriya adı boş ola bilməz")
    private String name;
}
