package ru.practicum.stat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndpointHit;
import ru.practicum.dto.ViewStats;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping
@Slf4j
@Validated
public class StatController {
    private final StateService stateService;

    public StatController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/hit")
    public ResponseEntity<EndpointHit> addHit(@RequestBody EndpointHit endpointHit) {
        log.info("Сохранена информация о том, что к эндпоинту был запрос {}", endpointHit);
        return ResponseEntity.of(stateService.addHit(endpointHit));
    }

    @GetMapping("/stats")
    public List<ViewStats> getStats(@RequestParam(value = "start") @NotNull String start,
                                    @RequestParam(value = "end") @NotNull String end,
                                    @RequestParam(value = "uris", required = false) List<String> uris,
                                    @RequestParam(value = "unique", defaultValue = "false",
                                                   required = false) boolean unique) {
        log.info("Получение статистики по посещениям в период с {} по {}", start, end);
        return stateService.getStats(start, end, uris, unique);
    }
}
