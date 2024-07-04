package com.togetherhana.event.predict.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EventGameCreateRequest {
    private String title;
    private String location;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDay;
    @DateTimeFormat(pattern = "kk:mm:ss")
    private LocalTime startTime;
    private Long homeTeamIdx;
    private Long awayTeamIdx;
}
