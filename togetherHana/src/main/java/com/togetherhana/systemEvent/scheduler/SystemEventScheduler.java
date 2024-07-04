package com.togetherhana.systemEvent.scheduler;

import com.togetherhana.systemEvent.service.SystemEventService;
import com.togetherhana.exception.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class SystemEventScheduler {

    private final SystemEventService systemEventService;
    private volatile boolean eventEnded = false; // 스케줄러 중단 플래그

    @Scheduled(fixedDelay = 10000)
    public void addParticipation() {
        if (eventEnded) {
            return; // 이벤트 종료 시 스케줄러 중단
        }

        log.info("스케줄러 실행");
        try {
            systemEventService.validEnd();
            systemEventService.remainParticipantsCheck();
            systemEventService.giveWinningMileage();
        } catch (BaseException e) {
            log.info("===== 선착순 이벤트가 종료되었습니다. =====");
            eventEnded = true; // 이벤트 종료 시 플래그 설정
        }
    }
}
