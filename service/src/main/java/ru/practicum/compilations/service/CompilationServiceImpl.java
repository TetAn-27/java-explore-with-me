package ru.practicum.compilations.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilations.CompilationRepository;
import ru.practicum.compilations.model.Compilation;
import ru.practicum.compilations.model.CompilationDto;
import ru.practicum.compilations.model.CompilationMapper;
import ru.practicum.compilations.model.NewCompilationDto;
import ru.practicum.events.EventRepository;
import ru.practicum.events.model.Event;
import ru.practicum.exception.NotFoundException;

import javax.persistence.EntityNotFoundException;
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
    public List<CompilationDto> getCollectionsEvents(boolean pinned, PageRequest pageRequestMethod) {
        Pageable page = pageRequestMethod;
        do {
            Page<Compilation> pageRequest = compilationRepository.findByPinned(pinned, page);
            pageRequest.getContent().forEach(ItemRequest -> {
            });
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
        List<Event> events = eventRepository.findByIdIn(newCompilationDto.getEvents());
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
    public Optional<CompilationDto> updateCompilation(long compId, NewCompilationDto newCompilationDto) {
        Compilation compOld = compilationRepository.getById(compId);
        Compilation compilation = getUpdateCompilation(compOld, newCompilationDto);
        return Optional.of(CompilationMapper.toCompilationDto(compilationRepository.save(compilation)));
    }

    private Compilation getUpdateCompilation(Compilation compOld, NewCompilationDto compNew) {
        compOld.setEvents(compNew.getEvents() != null ? eventRepository.findByIdIn(compNew.getEvents()) : compOld.getEvents());
        compOld.setPinned(compNew.getPinned() != null ? compNew.getPinned() : compOld.isPinned());
        compOld.setTitle(compNew.getTitle() != null ? compNew.getTitle() : compOld.getTitle());
        return compOld;
    }
}
