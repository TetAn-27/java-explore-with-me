package ru.practicum.compilations.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationDto;

import java.util.List;
import java.util.Optional;

public interface CompilationService {

    List<CompilationDto> getCollectionsEvents(Boolean pinned, PageRequest pageRequest);

    Optional<CompilationDto> getCompilationById(long compId);

    Optional<CompilationDto> addCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(long compId);

    Optional<CompilationDto> updateCompilation(long compId, UpdateCompilationDto compilationDto);
}
