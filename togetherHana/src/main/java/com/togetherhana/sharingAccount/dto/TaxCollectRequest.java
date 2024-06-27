package com.togetherhana.sharingAccount.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaxCollectRequest {
    private Long sharingAccountId;
    private Integer collectingAmount;
}
