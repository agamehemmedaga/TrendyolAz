package az.trendyolaz.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {

    private String token;

    @Builder.Default
    private String tokenType = "Bearer";

    private String message;
    private String fullName;
    private String email;
    private String phoneNumber;

    public AuthResponseDto(String token) {
        this.token = token;
        this.tokenType = "Bearer";
    }

    public AuthResponseDto(String token, String message, String fullName, String email, String phoneNumber) {
        this.token = token;
        this.tokenType = "Bearer";
        this.message = message;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}