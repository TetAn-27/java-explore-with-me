package ru.practicum.requests.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
   private long id;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime created;
   private long event;
   private long requester;
   private Status status;
}
