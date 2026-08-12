package az.trendyolaz.service;
import az.trendyolaz.dto.ProductRequestDto;
import az.trendyolaz.dto.ProductResponseDto;
import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto responseDto);
        List<ProductResponseDto>getAllProducts();
        List<ProductResponseDto> getProductsByCategory(Long categoryId);
        ProductResponseDto getProductById(Long id);
        ProductResponseDto updateProduct(Long id , ProductRequestDto requestDto);
        void deleteProduct(Long id);


}
