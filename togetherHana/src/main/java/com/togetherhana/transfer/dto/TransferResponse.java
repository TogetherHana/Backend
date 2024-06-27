package com.togetherhana.transfer.dto;

import com.togetherhana.transfer.entity.TransactionType;
import com.togetherhana.transfer.entity.TransferHistory;
import lombok.Getter;


@Getter
public abstract class TransferResponse {

    private Long remainBalance;
    private Long transactionAmount;
    private TransactionType transferType;

    private TransferResponse(TransferHistory transferHistory) {
        this.remainBalance = transferHistory.getRemainBalance();
        this.transactionAmount = transferHistory.getTransactionAmount();
        this.transferType = transferHistory.getTransferType();

    }

    public static class DepositResponse extends TransferResponse {
        private String sender;

        private DepositResponse(TransferHistory transferHistory) {
            super(transferHistory);
            this.sender = transferHistory.getSender();
        }

        public static DepositResponse of(TransferHistory transferHistory) {
            return new DepositResponse(transferHistory);
        }
    }

    public static class WithdrawalResponse extends TransferResponse {
        private String recipient;

        private WithdrawalResponse(TransferHistory transferHistory) {
            super(transferHistory);
            this.recipient = transferHistory.getRecipient();
        }

        public static WithdrawalResponse of(TransferHistory transferHistory) {
            return new WithdrawalResponse(transferHistory);
        }
    }

}
