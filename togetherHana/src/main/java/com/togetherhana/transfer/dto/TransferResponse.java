package com.togetherhana.transfer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.togetherhana.transfer.entity.TransactionType;
import com.togetherhana.transfer.entity.TransferHistory;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TransferResponse {

    private Long remainBalance;
    private Long transactionAmount;
    private TransactionType transferType;
    @JsonInclude(Include.NON_NULL)
    private String sender;
    @JsonInclude(Include.NON_NULL)
    private String recipient;
    private LocalDateTime createdAt;

    public static TransferResponse toDepositResponse(TransferHistory transferHistory) {
        return new TransferResponse(transferHistory.getRemainBalance(),
                transferHistory.getTransactionAmount(),
                transferHistory.getTransferType(),
                transferHistory.getSender(),
                null,
                transferHistory.getCreatedAt());
    }

    public static TransferResponse toWithdrawResponse(TransferHistory transferHistory) {
        return new TransferResponse(transferHistory.getRemainBalance(),
                transferHistory.getTransactionAmount(),
                transferHistory.getTransferType(),
                null,
                transferHistory.getRecipient(),
                transferHistory.getCreatedAt());
    }

}
