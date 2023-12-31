package ru.practicum.compilations.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.compilations.service.CompilationService;
import ru.practicum.compilations.dto.CompilationDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
public class CompilationPublicController {

    private final CompilationService compilationService;

    public CompilationPublicController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping("/compilations")
    public List<CompilationDto> getCollectionsEvents(@RequestParam(value = "pinned", required = false) Boolean pinned,
                                                     @PositiveOrZero @RequestParam(value = "from", defaultValue = "0",
                                                             required = false) int page,
                                                     @Positive @RequestParam(value = "size", defaultValue = "10",
                                                             required = false) int size) {
        PageRequest pageRequest = PageRequest.of(page / size, size, Sort.Direction.ASC, "id");
        return compilationService.getCollectionsEvents(pinned, pageRequest);
    }

    @GetMapping("/compilations/{compId}")
    public ResponseEntity<CompilationDto> getCompilationById(@PathVariable(value = "compId") Long compId) {
        return ResponseEntity.of(compilationService.getCompilationById(compId));
    }
}
