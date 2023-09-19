package ru.practicum.categories.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.categories.CategoryService;
import ru.practicum.categories.model.CategoryDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
public class CategoryPublicController {

    private final CategoryService categoryService;

    public CategoryPublicController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategory(@PositiveOrZero @RequestParam(value = "from", defaultValue = "0", required = false)
                                            int page,
                                            @Positive @RequestParam(value = "size", defaultValue = "10", required = false)
                                            int size) {
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.DESC, "id");
        return categoryService.getAllCategory(pageRequest);
    }

    @GetMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable(value = "catId") Long catId) {
        return ResponseEntity.of(categoryService.getCategoryById(catId));
    }
}
