package com.togetherhana.auth.controller;

import com.togetherhana.auth.dto.*;
import com.togetherhana.auth.jwt.JwtTokenExtractor;
import com.togetherhana.auth.service.AuthService;
import com.togetherhana.base.BaseResponse;
import com.togetherhana.member.entity.Member;
import com.togetherhana.member.service.MemberService;
import com.togetherhana.sms.service.SmsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class AuthController {
    private final SmsService smsService;
    private final AuthService authService;
    private final MemberService memberService;

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

    @GetMapping("/login")
    public BaseResponse<JwtToken> login(@RequestParam(name = "deviceToken") String deviceToken) {
        log.info("deviceToken: {}", deviceToken);
        // 디바이스토큰으로 멤버 정보 조회
        Member member = memberService.getMemberByDeviceToken(deviceToken);
        log.info("로그인 정보 :"+ member.toString());

        // JWT 토큰 생성
        return BaseResponse.success(authService.generateJwtToken(member));
    }

    @PostMapping("/reissue")
    public BaseResponse<JwtToken> reissue(HttpServletRequest request) {
        // refresh 토큰 추출
        String refreshToken = JwtTokenExtractor.extractRefresh(request);
        return BaseResponse.success(authService.reissue(refreshToken));
    }

}
