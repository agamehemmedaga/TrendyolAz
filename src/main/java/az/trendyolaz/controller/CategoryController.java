package az.trendyolaz.controller;
import az.trendyolaz.dto.CategoryRequestDto;
import az.trendyolaz.dto.CategoryResponseDto;
import az.trendyolaz.entity.Category;
import az.trendyolaz.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor

public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@Valid @RequestBody CategoryRequestDto requestDto){
        return new ResponseEntity<>(categoryService.createCategory(requestDto),HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(){
        return  ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getAllCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
}
