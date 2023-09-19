package ru.practicum.compilations;

import org.springframework.data.domain.PageRequest;
import ru.practicum.compilations.model.CompilationDto;

import java.util.List;
import java.util.Optional;

public interface CompilationService {

    List<CompilationDto> getCollectionsEvents(boolean pinned, PageRequest pageRequest);

    Optional<CompilationDto> getCompilationById(long compId);

    Optional<CompilationDto> addCompilation(CompilationDto compilationDto);

    void deleteCompilation(Long compId);

    Optional<CompilationDto> updateCompilation(Long compId, CompilationDto compilationDto);
}
