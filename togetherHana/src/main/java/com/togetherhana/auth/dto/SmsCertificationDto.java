package com.togetherhana.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SmsCertificationDto {
    private String phoneNumber;
    private String certificationCode;
}
