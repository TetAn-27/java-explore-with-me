package ru.practicum.state;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.StateDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping
@Slf4j
@Validated
public class StateController {
    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/hit")
    public ResponseEntity<Object> addHit(@RequestBody StateDto stateDto) {
        log.info("Сохранена информация о том, что к эндпоинту был запрос {}", stateDto);
        return stateService.addHit(stateDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStats(@RequestParam(value = "start") @NotNull LocalDateTime start,
                                           @RequestParam(value = "end") @NotNull LocalDateTime end,
                                           @RequestParam(value = "uris") List<String> uris,
                                           @RequestParam(value = "unique", defaultValue = "false",
                                                   required = false) boolean unique) {
        log.info("Получение статистики по посещениям в период с {} по {}", start, end);
        return stateService.getStats(start, end, uris, unique);
    }
}
