package ru.practicum.compilations.service;

import org.springframework.data.domain.PageRequest;
import ru.practicum.compilations.model.CompilationDto;
import ru.practicum.compilations.model.NewCompilationDto;

import java.util.List;
import java.util.Optional;

public interface CompilationService {

    List<CompilationDto> getCollectionsEvents(boolean pinned, PageRequest pageRequest);

    Optional<CompilationDto> getCompilationById(long compId);

    Optional<CompilationDto> addCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(long compId);

    Optional<CompilationDto> updateCompilation(long compId, NewCompilationDto newCompilationDto);
}
