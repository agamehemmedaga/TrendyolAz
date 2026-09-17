package az.trendyolaz.service;
import az.trendyolaz.dto.ProductSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import az.trendyolaz.dto.ProductRequestDto;
import az.trendyolaz.dto.ProductResponseDto;
import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto requestDto);
        List<ProductResponseDto>getAllProducts();
        List<ProductResponseDto> getProductsByCategory(Long categoryId);
        ProductResponseDto getProductById(Long id);
        ProductResponseDto updateProduct(Long id , ProductRequestDto requestDto);
    Page<ProductResponseDto> searchProducts(ProductSearchDto searchDto, Pageable pageable);
        void deleteProduct(Long id);


}
