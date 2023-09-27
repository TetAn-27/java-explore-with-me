package ru.practicum.compilations.model;

import ru.practicum.compilations.dto.*;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.EventMapper;

import java.util.ArrayList;
import java.util.List;

public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getId(),
                EventMapper.toListEventShortDto(compilation.getEvents()),
                compilation.isPinned(),
                compilation.getTitle()
        );
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        return new Compilation(
                0,
                events != null ? events : new ArrayList<>(),
                newCompilationDto.getPinned() != null ? newCompilationDto.getPinned() : false,
                newCompilationDto.getTitle()
        );
    }

    public static List<CompilationDto> toListCompilationDto(List<Compilation> compList) {
        List<CompilationDto> compDtoList = new ArrayList<>();
        for (Compilation compilation : compList) {
            compDtoList.add(toCompilationDto(compilation));
        }
        return compDtoList;
    }
}
