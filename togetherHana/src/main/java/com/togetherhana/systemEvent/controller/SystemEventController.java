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
    @PostMapping("/get-ticket/{memberIdx}")
    public BaseResponse<Boolean> tryGetTicket(@PathVariable(name = "memberIdx") Long memberIdx) {

        log.info("Received request for memberIdx: " + memberIdx);
        boolean result = systemEventService.tryGetTicket(memberIdx);
        log.info("Result for memberIdx: " + memberIdx + " is " + result);
        return BaseResponse.success(result);
        //return BaseResponse.success(systemEventService.tryGetTicket(memberIdx));
    }

    @PostMapping("/reset")
    public void resetEvent() {
        systemEventService.clearEvent();
    }
}
