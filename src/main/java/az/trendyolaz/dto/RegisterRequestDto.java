package az.trendyolaz.dto;




import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {

    @NotBlank(message = "Ad və soyad boş ola bilməz")
    @Size(min = 2, max = 50, message = "Ad və soyad 2 ilə 50 simvol arasında olmalıdır")
    private String fullName;

    @NotBlank(message = "Email ünvanı boş ola bilməz")
    @Email(message = "Düzgün email formatı daxil edin (nümunə: user@domain.com)")
    private String email;

    @NotBlank(message = "Telefon nömrəsi boş ola bilməz")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Telefon nömrəsi beynəlxalq formatda olmalıdır (nümunə: +994501234567)")
    private String phoneNumber;

    @NotBlank(message = "Şifrə boş ola bilməz")
    @Size(min = 8, message = "Şifrə ən az 8 simvoldan ibarət olmalıdır")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!._-]).{8,}$",
            message = "Şifrə ən az 1 böyük hərf, 1 kiçik hərf, 1 rəqəm və 1 xüsusi simvol (@#$%^&+=!._-) ehtiva etməlidir"
    )
    private String password;
}