package ru.practicum.categories.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.categories.CategoryRepository;
import ru.practicum.categories.model.Category;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.categories.model.CategoryMapper;
import ru.practicum.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<CategoryDto> addNewCategory(CategoryDto categoryDto) {
        Category category = CategoryMapper.toCategory(categoryDto);
        log.debug("Категория создана: {}", category);
        return Optional.of(CategoryMapper.toCategoryDto(categoryRepository.save(category)));
    }

    @Override
    public Optional<CategoryDto> updateCategory(long catId, CategoryDto categoryDto) {
        Category category = categoryRepository.getCategoryById(catId);
        category.setName(categoryDto.getName());
        return Optional.of(CategoryMapper.toCategoryDto(categoryRepository.save(category)));
    }

    @Override
    public void deleteCategory(long catId) {
        try {
            categoryRepository.deleteById(catId);
        } catch (DataAccessException ex) {
            log.error("Категории с Id {} не найдено", catId);
            throw new NotFoundException("Категории с таким Id не было найдено");
        }
    }

    @Override
    public List<CategoryDto> getAllCategory(PageRequest pageRequestMethod) {
        Pageable page = pageRequestMethod;
        do {
            Page<Category> pageRequest = categoryRepository.findAll(page);
            pageRequest.getContent().forEach(ItemRequest -> {
            });
            if (pageRequest.hasNext()) {
                page = PageRequest.of(pageRequest.getNumber() + 1, pageRequest.getSize(), pageRequest.getSort());
            } else {
                page = null;
            }
            return CategoryMapper.toListCategoryDto(pageRequest.getContent());
        } while (page != null);
    }

    @Override
    public Optional<CategoryDto> getCategoryById(long catId) {
        try {
            return Optional.of(CategoryMapper.toCategoryDto(categoryRepository.getById(catId)));
        } catch (EntityNotFoundException ex) {
            log.error("Категории с Id {} не найдено", catId);
            throw new NotFoundException("Каиегории с таким Id не было найдено");
        }
    }
}
