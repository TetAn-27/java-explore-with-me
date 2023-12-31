package ru.practicum.compilations.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilations.CompilationRepository;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;
import ru.practicum.compilations.dto.UpdateCompilationDto;
import ru.practicum.compilations.model.*;
import ru.practicum.events.EventRepository;
import ru.practicum.events.model.Event;
import ru.practicum.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    public CompilationServiceImpl(CompilationRepository compilationRepository, EventRepository eventRepository) {
        this.compilationRepository = compilationRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public List<CompilationDto> getCollectionsEvents(Boolean pinned, PageRequest pageRequestMethod) {
        Pageable page = pageRequestMethod;
        do {
            Page<Compilation> pageRequest;
            if (pinned == null) {
                pageRequest = compilationRepository.findAll(page);
            } else {
                pageRequest = compilationRepository.findByPinned(pinned, page);
            }
            if (pageRequest.hasNext()) {
                page = PageRequest.of(pageRequest.getNumber() + 1, pageRequest.getSize(), pageRequest.getSort());
            } else {
                page = null;
            }
            return CompilationMapper.toListCompilationDto(pageRequest.getContent());
        } while (page != null);
    }

    @Override
    public Optional<CompilationDto> getCompilationById(long compId) {
        try {
            return Optional.of(CompilationMapper.toCompilationDto(compilationRepository.getById(compId)));
        } catch (EntityNotFoundException ex) {
            log.error("Подборка с Id {} не была найдена", compId);
            throw new NotFoundException("Подборка с таким Id не была найдена");
        }
    }

    @Override
    public Optional<CompilationDto> addCompilation(NewCompilationDto newCompilationDto) {
        List<Event> events;
        if (newCompilationDto.getEvents() == null) {
            events = new ArrayList<>();
        } else {
            events = eventRepository.findByIdIn(newCompilationDto.getEvents());
        }
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto, events);
        return Optional.of(CompilationMapper.toCompilationDto(compilationRepository.save(compilation)));
    }

    @Override
    public void deleteCompilation(long compId) {
        try {
            compilationRepository.deleteById(compId);
        } catch (DataAccessException ex) {
            log.error("Подборка с Id {} не была найдена", compId);
            throw new NotFoundException("Подборка с таким Id не была найдена");
        }
    }

    @Override
    public Optional<CompilationDto> updateCompilation(long compId, UpdateCompilationDto compilationDto) {
        Compilation compOld = compilationRepository.getById(compId);
        Compilation compilation = getUpdateCompilation(compOld, compilationDto);
        return Optional.of(CompilationMapper.toCompilationDto(compilationRepository.save(compilation)));
    }

    private Compilation getUpdateCompilation(Compilation compOld, UpdateCompilationDto compNew) {
        compOld.setEvents(compNew.getEvents() != null ? eventRepository.findByIdIn(compNew.getEvents()) : compOld.getEvents());
        compOld.setPinned(compNew.getPinned() != null ? compNew.getPinned() : compOld.isPinned());
        compOld.setTitle(compNew.getTitle() != null ? compNew.getTitle() : compOld.getTitle());
        return compOld;
    }
}
