package com.togetherhana.sharingAccount.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SharingAccountCreateRequest {
    private String accountName;
    private String accountPassword;
    private String sharePurpose;
}
