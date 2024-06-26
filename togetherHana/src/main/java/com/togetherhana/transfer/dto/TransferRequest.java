package com.togetherhana.transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequest {
    private Long sharingAccountIdx;
    private String receiveAccountNumber;
    private String receiver;
    private Long amount;
    private String accountPassword;
}
