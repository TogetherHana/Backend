package com.togetherhana.event.predict.service;

import com.togetherhana.event.predict.entity.EventGame;
import com.togetherhana.event.predict.repository.EventGameRepository;
import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.entity.Member;
import com.togetherhana.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventGameListener {

    private final EventGameRepository eventGameRepository;
    private final MemberRepository memberRepository;

    @EventListener
    @Transactional
    protected void close(EventGameStartEvent event) {
        EventGame eventGame = eventGameRepository.findById(event.getEventGameIdx()).get();
        log.info("이벤트 게임 : {}이 시작되었습니다. 이벤트 참가를 종료합니다.", eventGame.getTitle());
        eventGame.closeGame();
    }

    @EventListener
    @Transactional
    @Async("eventConcludeExecutor")
    protected void conclude(EventGameConcludeEvent event) {
        Member winningMember = memberRepository.findById(event.getWinner()).orElseThrow(
                () -> new BaseException(ErrorType.INVALID_MEMBER_IDX)
        );
        log.info("{} 님에게 마일리지 {}를 지급합니다.", winningMember.getName(), event.getPrizeAmount());
        winningMember.getMileage().plus(event.getPrizeAmount());
    }
}
