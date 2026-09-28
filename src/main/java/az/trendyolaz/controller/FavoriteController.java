package az.trendyolaz.controller;



import az.trendyolaz.dto.FavoriteResponseDto;
import az.trendyolaz.entity.User;
import az.trendyolaz.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{productId}")
    public ResponseEntity<String> addToFavorites(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId) {
        favoriteService.addToFavorites(user.getId(), productId);
        return ResponseEntity.ok("Məhsul sevimlilərə əlavə olundu");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromFavorites(
            @AuthenticationPrincipal User user,
            @PathVariable Long productId) {
        favoriteService.removeFromFavorites(user.getId(), productId);
        return ResponseEntity.ok("Məhsul sevimlilərdən silindi");
    }

    @GetMapping
    public ResponseEntity<Page<FavoriteResponseDto>> getUserFavorites(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(favoriteService.getUserFavorites(user.getId(), pageable));
    }
}