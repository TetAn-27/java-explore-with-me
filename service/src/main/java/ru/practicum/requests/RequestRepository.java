package ru.practicum.requests;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.requests.model.ParticipationRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<ParticipationRequest, Long> {

    ParticipationRequest save(ParticipationRequest request);
    ParticipationRequest getById(long id);
    List<ParticipationRequest> findByRequesterId(long id);
    List<ParticipationRequest> findByEventId(long id);
    List<ParticipationRequest> findByIdIn(Iterable ids);
}
