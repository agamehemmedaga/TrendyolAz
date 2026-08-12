package az.trendyolaz.service.impl;
import az.trendyolaz.dto.CategoryRequestDto;
import az.trendyolaz.dto.CategoryRequestDto;
import az.trendyolaz.dto.CategoryResponseDto;
import az.trendyolaz.exception.ResourceNotFoundException;
import az.trendyolaz.entity.Category;
import az.trendyolaz.repository.CategoryRepository;
import az.trendyolaz.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.processing.Find;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository ;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto requestDto){
        Category category = Category.builder()
                .name(requestDto.getName())
                .build();
        Category saved = categoryRepository.save(category);
        return mapToResponseDto(saved);

    }

    @Override
    public List<CategoryResponseDto> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Override
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Kateqoriya tapılmadı ID: " + id));
        return mapToResponseDto(category);
    }

    private CategoryResponseDto mapToResponseDto(Category category){
        return CategoryResponseDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
