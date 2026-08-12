package az.trendyolaz.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequestDto {

    @NotBlank(message = "İstifadəçi adı boş ola bilməz")
    private String username;

    @NotBlank(message = "Şifrə boş ola bilməz")
    private String password; // Long əvəzinə String olmalıdır
}