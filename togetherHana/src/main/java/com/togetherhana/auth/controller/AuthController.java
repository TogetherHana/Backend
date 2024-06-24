package com.togetherhana.auth.controller;

import com.togetherhana.auth.dto.AccountCertificationDto;
import com.togetherhana.auth.dto.AccountCheckDto;
import com.togetherhana.auth.dto.SmsCertificationDto;
import com.togetherhana.auth.dto.SmsRequestDto;
import com.togetherhana.auth.service.AuthService;
import com.togetherhana.base.BaseResponse;
import com.togetherhana.sms.service.SmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final SmsService smsService;
    private final AuthService authService;

    @PostMapping("/sms")
    public BaseResponse<Boolean> sendSms(@RequestBody SmsRequestDto smsRequestDto) {
        return BaseResponse.success(smsService.sendCertificationMessage(smsRequestDto));
    }

    @PostMapping("/sms-verify")
    public BaseResponse<Boolean> sendSmsVerify(@RequestBody SmsCertificationDto smsCertificationDto) {
        return BaseResponse.success(smsService.certify(smsCertificationDto));
    }

    @PostMapping("/account-check")
    public BaseResponse<Boolean> checkAccount(@RequestBody AccountCheckDto accountCheckDto) {
        return BaseResponse.success(authService.sendCoinWithAuthCode(accountCheckDto));
    }

    @PostMapping("/account-verify")
    public BaseResponse<Boolean> verifyAccount(@RequestBody AccountCertificationDto accountCertificationDto) {
        return BaseResponse.success(authService.verifyAccountAuthCode(accountCertificationDto));
    }

}
