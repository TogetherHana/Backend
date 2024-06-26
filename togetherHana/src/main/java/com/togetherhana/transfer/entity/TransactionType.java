package com.togetherhana.transfer.entity;

import lombok.Getter;

@Getter
public enum TransactionType {
    DEPOSIT("입금"),
    WITHDRAW("출금");
    private final String description;

    TransactionType(String description) {
        this.description = description;
    }
}
