package com.togetherhana.mileage.dto;

import com.togetherhana.mileage.entity.Mileage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MileageResponse {
    private Long mileageIdx;
    private Long amount;

    public MileageResponse(Mileage mileage) {
        this.mileageIdx = mileage.getMileageIdx();
        this.amount = mileage.getAmount();
    }
}
