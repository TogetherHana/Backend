package com.togetherhana.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountCertificationDto {
    private String accountNumber;
    private String certificationNumber;
}
