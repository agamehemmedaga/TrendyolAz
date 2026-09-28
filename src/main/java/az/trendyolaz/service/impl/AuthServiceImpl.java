package az.trendyolaz.service.impl;



import az.trendyolaz.dto.AuthResponseDto;
import az.trendyolaz.dto.LoginRequestDto;
import az.trendyolaz.dto.RegisterRequestDto;
import az.trendyolaz.entity.User;
import az.trendyolaz.enums.Role;
import az.trendyolaz.exception.AccountLockedException;
import az.trendyolaz.exception.BadCredentialsExceptionCustom;
import az.trendyolaz.exception.UserAlreadyExistsException;
import az.trendyolaz.repository.UserRepository;
import az.trendyolaz.service.AuthService;
import az.trendyolaz.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_TIME_DURATION_MINUTES = 15;

    @Override
    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Bu email ünvanı ilə artıq qeydiyyatdan keçilib!");
        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new UserAlreadyExistsException("Bu telefon nömrəsi artıq istifadə olunub!");
        }

        User user = User.builder()
                .username(request.getEmail())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .accountNonLocked(true)
                .failedAttempt(0)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return AuthResponseDto.builder()
                .token(token)
                .message("Qeydiyyat uğurla tamamlandı")
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request) {
        String input = request.getUsernameOrEmailOrPhone();

        User user = userRepository.findByEmailOrPhoneNumber(input, input)
                .orElseGet(() -> userRepository.findByUsername(input)
                        .orElseThrow(() -> new BadCredentialsExceptionCustom("Daxil edilən məlumatlar yanlışdır")));

        if (!user.isAccountNonLocked()) {
            if (user.getLockTime() != null && user.getLockTime().plusMinutes(LOCK_TIME_DURATION_MINUTES).isBefore(LocalDateTime.now())) {
                user.setAccountNonLocked(true);
                user.setFailedAttempt(0);
                user.setLockTime(null);
                userRepository.save(user);
            } else {
                throw new AccountLockedException("Çoxlu yanlış cəhd! Hesabınız 15 dəqiqəlik dondurulub.");
            }
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            increaseFailedAttempts(user);
            throw new BadCredentialsExceptionCustom("Daxil edilən məlumatlar yanlışdır");
        }

        if (user.getFailedAttempt() > 0) {
            user.setFailedAttempt(0);
            userRepository.save(user);
        }

        String token = jwtService.generateToken(user);

        return AuthResponseDto.builder()
                .token(token)
                .message("Uğurla daxil oldunuz")
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    private void increaseFailedAttempts(User user) {
        int newFailedAttempts = user.getFailedAttempt() + 1;
        user.setFailedAttempt(newFailedAttempts);

        if (newFailedAttempts >= MAX_FAILED_ATTEMPTS) {
            user.setAccountNonLocked(false);
            user.setLockTime(LocalDateTime.now());
        }

        userRepository.save(user);
    }
}