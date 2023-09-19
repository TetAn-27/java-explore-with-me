package ru.practicum.categories;

import org.springframework.data.domain.PageRequest;
import ru.practicum.categories.model.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<CategoryDto> addNewCategory(CategoryDto categoryDto);

    Optional<CategoryDto> updateCategory(long catId, CategoryDto categoryDto);

    void deleteCategory(long catId);

    List<CategoryDto> getAllCategory(PageRequest pageRequest);

    Optional<CategoryDto> getCategoryById(long catId);
}
