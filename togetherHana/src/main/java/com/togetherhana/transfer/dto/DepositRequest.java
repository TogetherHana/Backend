package com.togetherhana.transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DepositRequest {
    private Long sharingAccountIdx;
    private Long amount;
    private String sender;
}
