package az.trendyolaz.controller;

import az.trendyolaz.dto.ProductSearchDto;
import az.trendyolaz.dto.ProductResponseDto;
import az.trendyolaz.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor

public class ProductController {

    private final ProductService productService;

    @GetMapping("/search")

    public ResponseEntity<Page<ProductResponseDto>> searchProducts(
            ProductSearchDto searchDto,
            Pageable pageable){
        return ResponseEntity.ok(productService.searchProducts(searchDto, pageable));
    }


}