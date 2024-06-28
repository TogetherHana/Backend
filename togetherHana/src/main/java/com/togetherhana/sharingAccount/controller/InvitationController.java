package com.togetherhana.sharingAccount.controller;

import com.togetherhana.auth.jwt.Auth;
import com.togetherhana.base.BaseResponse;
import com.togetherhana.member.entity.Member;
import com.togetherhana.sharingAccount.dto.ParticipateRequest;
import com.togetherhana.sharingAccount.dto.SharingAccountResponse;
import com.togetherhana.sharingAccount.service.InvitationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/invite")
public class InvitationController {
    private final InvitationService invitationService;

    @GetMapping("/code")
    public BaseResponse<String> inviteCode(@RequestParam(name = "sharingAccountIdx") Long sharingAccountIdx) {
        return BaseResponse.success(invitationService.createInvitationCode(sharingAccountIdx));
    }

    @GetMapping("/info")
    public BaseResponse<SharingAccountResponse> inviteInfo(@RequestParam("invitationCode") String invitationCode) {
        return BaseResponse.success(invitationService.getInvitationInfo(invitationCode));
    }

    @PostMapping("/participate")
    public BaseResponse<Boolean> participate(@Auth Member member, @RequestBody ParticipateRequest participateRequest)
    {
        return BaseResponse.success(invitationService.participate(member, participateRequest));
    }
}
