package com.togetherhana.event.firstcome.scheduler;

import com.togetherhana.event.firstcome.service.FirstComeEventService;
import com.togetherhana.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class FirstComeEventScheduler {

    private final FirstComeEventService firstComeEventService;
    private volatile boolean eventEnded = false; // 스케줄러 중단 플래그

    @Scheduled(fixedDelay = 10000)
    public void addParticipation() {
        if (eventEnded) {
            return; // 이벤트 종료 시 스케줄러 중단
        }

        try {
            firstComeEventService.validEnd();
            firstComeEventService.giveWinningMileage();
        } catch (BaseException e) {
            //log.info("===== 선착순 이벤트가 종료되었습니다. =====");
            eventEnded = true; // 이벤트 종료 시 플래그 설정
        }
    }
}
