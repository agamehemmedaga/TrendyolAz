package az.trendyolaz.service;




import az.trendyolaz.dto.FavoriteResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FavoriteService {

    void addToFavorites(Long userId, Long productId);

    void removeFromFavorites(Long userId, Long productId);

    Page<FavoriteResponseDto> getUserFavorites(Long userId, Pageable pageable);
}