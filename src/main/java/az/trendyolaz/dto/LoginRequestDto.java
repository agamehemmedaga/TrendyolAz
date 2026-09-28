package az.trendyolaz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDto {

    @NotBlank(message = "Email və ya telefon nömrəsi daxil edilməlidir")
    private String usernameOrEmailOrPhone;

    @NotBlank(message = "Şifrə boş ola bilməz")
    private String password;
}