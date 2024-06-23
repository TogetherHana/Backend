package com.togetherhana.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SmsCertificationDto {
    private final String phoneNumber;
    private final String certificationCode;
}
