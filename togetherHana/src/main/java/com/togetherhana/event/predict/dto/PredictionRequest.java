package com.togetherhana.event.predict.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PredictionRequest {
    private Long eventGameIdx;
    private Long winnerPredictTeamIdx;
}
