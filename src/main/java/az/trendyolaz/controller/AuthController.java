package az.trendyolaz.controller;
import az.trendyolaz.dto.AuthRequestDto;
import az.trendyolaz.dto.AuthResponseDto;
import az.trendyolaz.dto.CartResponseDto;
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
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login (@Valid @RequestBody AuthRequestDto requestDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(requestDto.getUsername());
        String token = jwtService.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

}
