package ru.practicum.compilations.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationDto;
import ru.practicum.compilations.service.CompilationService;
import ru.practicum.compilations.dto.CompilationDto;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin")
public class CompilationAdminController {

    private final CompilationService compilationService;

    public CompilationAdminController(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @PostMapping("/compilations")
    public ResponseEntity<CompilationDto> addCompilation(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(compilationService.addCompilation(newCompilationDto).get());
    }

    @DeleteMapping("/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable("compId") Long compId) {
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/compilations/{compId}")
    public ResponseEntity<CompilationDto> updateCompilation(@PathVariable("compId") Long compId,
                                                            @RequestBody  @Valid UpdateCompilationDto compilationDto) {
        return ResponseEntity.of(compilationService.updateCompilation(compId, compilationDto));
    }
}
