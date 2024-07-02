package com.togetherhana.systemEvent.controller;

import com.togetherhana.auth.jwt.Auth;
import com.togetherhana.base.BaseResponse;
import com.togetherhana.member.entity.Member;
import com.togetherhana.systemEvent.service.SystemEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/event")
public class SystemEventController {

    private final SystemEventService systemEventService;

    // 선착순 이벤트
    @GetMapping("/get-ticket")
    public BaseResponse<Boolean> tryGetTicket(@Auth Member member) {
        systemEventService.addQueue(member.getMemberIdx());
        return BaseResponse.success(systemEventService.getWinner(member.getMemberIdx()));
    }

}
