package ru.practicum.categories.model;

import java.util.ArrayList;
import java.util.List;

public class CategoryMapper {

    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }

    public static Category toCategory(CategoryDto categoryDto) {
        return new Category(
                0,
                categoryDto.getName()
        );
    }

    public static List<CategoryDto> toListCategoryDto(List<Category> catList) {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : catList) {
            categoryDtoList.add(toCategoryDto(category));
        }
        return categoryDtoList;
    }
}
