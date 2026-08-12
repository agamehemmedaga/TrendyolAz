package az.trendyolaz.service.impl;
import az.trendyolaz.dto.ProductRequestDto;
import az.trendyolaz.dto.ProductResponseDto;
import az.trendyolaz.exception.ResourceNotFoundException;
import az.trendyolaz.entity.Category;
import az.trendyolaz.entity.Product;
import az.trendyolaz.repository.CategoryRepository;
import az.trendyolaz.repository.ProductRepository;
import az.trendyolaz.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Kateqoriya tapılmadı ID: " + requestDto.getCategoryId()));

        Product product = Product.builder()
                .name(requestDto.getName())
                .originalPrice(requestDto.getOriginalPrice())
                .discountedPrice(requestDto.getDiscountedPrice())
                .category(category)
                .build();

        Product saved = productRepository.save(product);
        return mapToResponseDto(saved);
    }

        @Override
        public List<ProductResponseDto>getAllProducts() {
            return productRepository.findAll()
                    .stream()
                    .map(this::mapToResponseDto)
                    .toList();
        }

    @Override
    public List<ProductResponseDto> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

        @Override
        public ProductResponseDto getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Məhsul tapılmadı ID: "  + id));
        return mapToResponseDto(product);
        }

        @Override
    public ProductResponseDto updateProduct (Long id , ProductRequestDto requestDto){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Məhsul tapılmadı ID: " + id));

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(()-> new ResourceNotFoundException("Kateqoriya tapılmadı ID: " + requestDto.getCategoryId()));
            product.setName(requestDto.getName());
            product.setOriginalPrice(requestDto.getOriginalPrice());
            product.setDiscountedPrice(requestDto.getDiscountedPrice());
            product.setCategory(category);

            Product updated = productRepository.save(product);
            return mapToResponseDto(updated);
        }

        @Override
    public void deleteProduct(Long id){
        if(!productRepository.existsById(id)){
            throw new ResourceNotFoundException("Məhsul tapılmadı ID: " + id);
        }
        productRepository.deleteById(id);
        }

        private ProductResponseDto mapToResponseDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .originalPrice(product.getOriginalPrice())
                .discountedPrice(product.getDiscountedPrice())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();

        }
}
