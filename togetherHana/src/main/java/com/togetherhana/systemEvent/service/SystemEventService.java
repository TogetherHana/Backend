package com.togetherhana.systemEvent.service;

import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class SystemEventService {

    private static final String EVENT_KEY = "event:winner";
    private static final int MAX_WINNERS = 5; // 최대 당첨자 수

    private final RedisTemplate<String, String> redisTemplate;


    public void addQueue(Long memberIdx){
        final long now = System.currentTimeMillis();

        redisTemplate.opsForZSet().add(EVENT_KEY, memberIdx.toString(), now);
        log.info("대기열에 추가 - {} ({}초)", memberIdx, now);
    }

    public boolean getWinner(Long memberIdx){
        Set<String> queue = redisTemplate.opsForZSet().range(EVENT_KEY, 0, MAX_WINNERS-1);
        if (queue == null) throw new BaseException(ErrorType.NO_EVENT_WINNERS);

        return queue.contains(memberIdx.toString());
    }

    public void clearEvent() {
        redisTemplate.delete(EVENT_KEY);
    }
}
