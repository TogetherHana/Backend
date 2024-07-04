package com.togetherhana.event.firstcome.service;

import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.mileage.service.MileageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class FirstComeEventService {

    private final MileageService mileageService;
    private static final String EVENT_KEY = "event:participant";
    private static final int MAX_WINNERS = 5; // 최대 당첨자 수
    private static final long PUBLISH_SIZE = 2;
    private final RedisTemplate<String, Object> redisTemplate;

    private int remainingWinners = MAX_WINNERS;

    public void addQueue(Long memberIdx){
        final long now = System.currentTimeMillis();
        redisTemplate.opsForZSet().add(EVENT_KEY, memberIdx.toString(), now);
        log.info("대기열에 추가 - {} ({}초)", memberIdx, now);
    }

    public boolean isWinner(Long memberIdx){
        Set<Object> winners = redisTemplate.opsForZSet().range(EVENT_KEY, 0, MAX_WINNERS-1);

        if (winners == null){
            throw new BaseException(ErrorType.INVAILD_EVENT_WINNERS);
        }
        return winners.contains(memberIdx.toString());
    }


    @Transactional
    public void giveWinningMileage(){
        validEnd();
        log.info("남은 당첨자수 : {}", remainingWinners);
        Set<Object> winners = redisTemplate.opsForZSet().range(EVENT_KEY, 0, PUBLISH_SIZE - 1);

        List<Long> winnerList = winners.stream()
                .map(winner -> Long.parseLong((String) winner))
                .collect(Collectors.toList());

        for (Long winnerIdx : winnerList) {
            mileageService.depositMileage(winnerIdx, 1000L); // 마일리지 적립
            log.info("당첨자들에게 마일리지를 지급했습니다: {}", winnerIdx);
            redisTemplate.opsForZSet().remove(EVENT_KEY, winnerIdx.toString());
            remainingWinners--;
            //log.info("남은 당첨자수 : {}", remainingWinners);

            // max_winners가 publish_size로 딱 나누어떨어지지 않는 경우
            validEnd();
        }
    }

    public void validEnd() {
        if(remainingWinners <= 0){
            throw new BaseException(ErrorType.EVENT_END);
        };
    }

    public void clearEvent() {
        redisTemplate.delete(EVENT_KEY);
    }
}
