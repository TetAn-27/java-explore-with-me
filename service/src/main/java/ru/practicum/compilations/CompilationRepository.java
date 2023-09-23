package ru.practicum.compilations;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilations.model.Compilation;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    void deleteById(long compId);
    Compilation getById(long compId);
    Page<Compilation> findByPinned(boolean pinned, Pageable page);
}
