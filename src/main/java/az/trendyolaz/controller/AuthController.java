package az.trendyolaz.controller;



import az.trendyolaz.dto.AuthRequestDto;
import az.trendyolaz.dto.AuthResponseDto;
import az.trendyolaz.dto.LoginRequestDto;
import az.trendyolaz.dto.RegisterRequestDto;
import az.trendyolaz.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUsernameOrEmailOrPhone(), requestDto.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getUsernameOrEmailOrPhone());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponseDto(token, "Uğurla daxil oldunuz", null, userDetails.getUsername(), null));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto requestDto) {
        return ResponseEntity.ok(AuthResponseDto.builder()
                .message("Qeydiyyat uğurla tamamlandı")
                .fullName(requestDto.getFullName())
                .email(requestDto.getEmail())
                .phoneNumber(requestDto.getPhoneNumber())
                .build());
    }
}