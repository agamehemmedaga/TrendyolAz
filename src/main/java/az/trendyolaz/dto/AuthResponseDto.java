package az.trendyolaz.dto;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class AuthResponseDto {

    private String token;
    @Builder.Default
    private String tokenType = "Bearer";

    public AuthResponseDto(String token){
        this.token = token;
        this.tokenType = "Bearer";
    }
}
