package com.togetherhana.event.predict.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EventConcludeRequest {
    private Long eventGameIdx;
    private Long winningTeamIdx;
    private Integer amount;
}
