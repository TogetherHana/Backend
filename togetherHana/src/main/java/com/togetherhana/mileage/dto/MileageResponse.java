package com.togetherhana.mileage.dto;

import lombok.Builder;

@Builder
public class MileageResponse {
    private Long mileageIdx;
    private Long amount;
}
