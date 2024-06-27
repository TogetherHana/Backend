package com.togetherhana.sharingAccount.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SharingAccountCreateResponse {
    private String accountNumber;
    private String accountName;
    private String owner;
    private LocalDateTime createdAt;
}
