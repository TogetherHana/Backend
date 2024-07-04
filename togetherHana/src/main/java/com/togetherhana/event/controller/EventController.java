package com.togetherhana.event.controller;

import com.togetherhana.auth.jwt.Auth;
import com.togetherhana.base.BaseResponse;
import com.togetherhana.event.firstcome.service.FirstComeEventService;
import com.togetherhana.event.predict.dto.EventConcludeRequest;
import com.togetherhana.event.predict.dto.EventGameCreateRequest;
import com.togetherhana.event.predict.dto.EventGameInfoResponse;
import com.togetherhana.event.predict.dto.PredictionRequest;
import com.togetherhana.event.predict.service.EventGameService;
import com.togetherhana.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/event")
public class EventController {
    private final EventGameService eventGameService;
    private final FirstComeEventService firstComeEventService;


    @GetMapping("/list")
    public BaseResponse<List<EventGameInfoResponse>> eventList() {
        return BaseResponse.success(eventGameService.allEventGames());
    }

    @PostMapping("/pick")
    public BaseResponse<Boolean> pick(@Auth Member member, @RequestBody PredictionRequest predictionRequest) {
        return BaseResponse.success(eventGameService.pickWinnerTeam(member, predictionRequest));
    }

    @PostMapping("/create")
    public BaseResponse<Boolean> create(@RequestBody EventGameCreateRequest eventGameCreateRequest) {
        return BaseResponse.success(eventGameService.createEventGame(eventGameCreateRequest));
    }

    @PostMapping("/conclude")
    public BaseResponse<Boolean> conclude(@RequestBody EventConcludeRequest eventConcludeRequest) {
        return BaseResponse.success(eventGameService.concludeEventGame(eventConcludeRequest));
    }

    // 선착순 이벤트
    @GetMapping("/get-ticket")
    public BaseResponse<Boolean> tryGetTicket(@Auth Member member) {
        firstComeEventService.addQueue(member.getMemberIdx());
        return BaseResponse.success(firstComeEventService.isWinner(member.getMemberIdx()));
    }
}
