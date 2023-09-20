package ru.practicum.categories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.categories.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category save(Category category);
    void deleteById(long userId);
    Category getCategoryById(long id);
    Page<Category> findAll(Pageable page);
    Category getById(long id);
}
