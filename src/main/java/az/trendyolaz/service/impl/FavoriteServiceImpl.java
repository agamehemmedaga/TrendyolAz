package az.trendyolaz.service.impl;



import az.trendyolaz.dto.FavoriteResponseDto;
import az.trendyolaz.entity.Favorite;
import az.trendyolaz.entity.Product;
import az.trendyolaz.entity.User;
import az.trendyolaz.exception.ResourceNotFoundException;
import az.trendyolaz.repository.FavoriteRepository;
import az.trendyolaz.repository.ProductRepository;
import az.trendyolaz.repository.UserRepository;
import az.trendyolaz.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void addToFavorites(Long userId, Long productId) {
        if (favoriteRepository.existsByUserIdAndProductId(userId, productId)) {
            return;
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("İstifadəçi tapılmadı ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Məhsul tapılmadı ID: " + productId));

        Favorite favorite = Favorite.builder()
                .user(user)
                .product(product)
                .build();

        favoriteRepository.save(favorite);
    }

    @Override
    @Transactional
    public void removeFromFavorites(Long userId, Long productId) {
        if (!favoriteRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new ResourceNotFoundException("Məhsul sevimlilər siyahısında tapılmadı");
        }
        favoriteRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    public Page<FavoriteResponseDto> getUserFavorites(Long userId, Pageable pageable) {
        return favoriteRepository.findByUserId(userId, pageable)
                .map(fav -> FavoriteResponseDto.builder()
                        .id(fav.getId())
                        .productId(fav.getProduct().getId())
                        .productName(fav.getProduct().getName())
                        .originalPrice(fav.getProduct().getOriginalPrice())
                        .discountedPrice(fav.getProduct().getDiscountedPrice())
                        .categoryName(fav.getProduct().getCategory() != null ? fav.getProduct().getCategory().getName() : null)
                        .addedAt(fav.getCreatedAt())
                        .build());
    }
}