package az.trendyolaz.service;
import az.trendyolaz.dto.CategoryRequestDto;
import az.trendyolaz.dto.CategoryResponseDto;


import java.util.List;

public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto requestDto);
    List<CategoryResponseDto> getAllCategories();
    CategoryResponseDto getCategoryById(Long id);

}
