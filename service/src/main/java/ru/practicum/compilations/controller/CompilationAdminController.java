package ru.practicum.compilations.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.CompilationService;
import ru.practicum.compilations.model.CompilationDto;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin")
public class CompilationAdminController {

    private final CompilationService compilationService;

    public CompilationAdminController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @PostMapping("/compilations")
    public ResponseEntity<CompilationDto> addCompilation(@RequestBody @Valid CompilationDto compilationDto) {
        return ResponseEntity.of(compilationService.addCompilation(compilationDto));
    }

    @DeleteMapping("/compilation/{compId}")
    public void deleteCompilation(@PathVariable("compId") Long compId) {
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(@PathVariable("compId") Long compId,
                                                            @RequestBody  @Valid CompilationDto compilationDto) {
        return ResponseEntity.of(compilationService.updateCompilation(compId, compilationDto));
    }
}
