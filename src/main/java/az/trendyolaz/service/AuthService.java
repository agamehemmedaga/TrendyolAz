package az.trendyolaz.service;



import az.trendyolaz.dto.AuthResponseDto;
import az.trendyolaz.dto.LoginRequestDto;
import az.trendyolaz.dto.RegisterRequestDto;

public interface AuthService {

    AuthResponseDto register(RegisterRequestDto request);

    AuthResponseDto login(LoginRequestDto request);
}