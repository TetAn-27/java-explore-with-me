package ru.practicum.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.categories.model.Category;
import ru.practicum.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String annotation;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @ToString.Exclude
    private Category category;
    @Column(name = "confirmed_requests", nullable = false)
    private long confirmedRequests;
    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn;
    private String description;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiator_id", nullable = false)
    @ToString.Exclude
    private User initiator;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    @ToString.Exclude
    private Location location;
    private boolean paid;
    @Column(name = "participant_limit", nullable = false)
    private int participantLimit;
    @Column(name = "published_on", nullable = false)
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation", nullable = false)
    private boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state;
    private String title;
    private long views;
}
