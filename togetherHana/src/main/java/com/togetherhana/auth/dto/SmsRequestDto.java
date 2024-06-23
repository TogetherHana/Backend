package com.togetherhana.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SmsRequestDto {
    private String phoneNumber;
}
