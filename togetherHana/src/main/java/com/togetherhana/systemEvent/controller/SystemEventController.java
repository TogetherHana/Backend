package com.togetherhana.systemEvent.controller;

import com.togetherhana.auth.jwt.Auth;
import com.togetherhana.base.BaseResponse;
import com.togetherhana.member.entity.Member;
import com.togetherhana.systemEvent.service.SystemEventSortedSetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/event")
public class SystemEventController {

    private final SystemEventSortedSetService systemEventSortedSetService;

    // 선착순 이벤트
    @GetMapping("/get-ticket")
    public BaseResponse<Boolean> tryGetTicket(@Auth Member member) {
        systemEventSortedSetService.addOnRedis(member.getMemberIdx());
        return BaseResponse.success(systemEventSortedSetService.getWinner(member.getMemberIdx()));
    }

}
