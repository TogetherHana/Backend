package com.togetherhana.auth.controller;

import com.togetherhana.auth.dto.SmsCertificationDto;
import com.togetherhana.auth.dto.SmsRequestDto;
import com.togetherhana.base.BaseResponse;
import com.togetherhana.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final SmsService smsService;

    @PostMapping("/sms")
    public BaseResponse<Boolean> sendSms(SmsRequestDto smsRequestDto) {
        return BaseResponse.success(smsService.sendCertificationMessage(smsRequestDto));
    }

    @PostMapping("/sms-verify")
    public BaseResponse<Boolean> sendSmsVerify(SmsCertificationDto smsCertificationDto) {
        return BaseResponse.success(smsService.certify(smsCertificationDto));
    }

}
