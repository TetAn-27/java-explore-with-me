package ru.practicum.categories.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.categories.service.CategoryService;
import ru.practicum.categories.model.CategoryDto;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin")
public class CategoryAdminController {

    private final CategoryService categoryService;

    public CategoryAdminController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> addNewCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return ResponseEntity.of(categoryService.addNewCategory(categoryDto));
    }

    @PatchMapping("/categories/{catId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("catId") Long catId,
                                                      @RequestBody  @Valid CategoryDto categoryDto) {
        return ResponseEntity.of(categoryService.updateCategory(catId, categoryDto));
    }

    @DeleteMapping("/categories/{catId}")
    public void deleteCategory(@PathVariable("catId") Long catId) {
        categoryService.deleteCategory(catId);
    }
}