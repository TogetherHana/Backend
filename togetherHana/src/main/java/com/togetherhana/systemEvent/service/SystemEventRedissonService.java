package com.togetherhana.systemEvent.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Slf4j
@Service
public class SystemEventRedissonService {

    private static final String EVENT_KEY = "event:winner";
    private static final String LOCK_KEY = "event:lock";
    private static final int MAX_PARTICIPANTS = 5;

    private final RedisTemplate<String, String> redisTemplate;
    private final RedissonClient redissonClient;

    public boolean tryGetTicket(Long memberIdx) {
        long requestTime = System.currentTimeMillis();
        log.info("티켓 시도 시간: 사용자 {} - {}", memberIdx, requestTime);

        // 중복 참여 방지 : 똑같은 사용자가 여러번 요청한 경우
        if (redisTemplate.opsForList().range(EVENT_KEY, 0, -1).contains(memberIdx.toString())) {
            log.info("이미 참여한 사용자 : " + memberIdx);
            return false;
        }

        RLock lock = redissonClient.getFairLock(LOCK_KEY);
        boolean isLocked = false;

        try {
            // 락을 대기 시간 10초, 락 임대 시간 30초로 설정
            if (lock.tryLock(10, 30, TimeUnit.SECONDS)) {
                //long lockAcquiredTime = System.currentTimeMillis();
                //log.info("락 획득 시간: 사용자 {} - {}", memberIdx, lockAcquiredTime);
                isLocked = true;

                // 중복 참여 방지 로직 재검토 (락 획득 후)
                if (redisTemplate.opsForList().range(EVENT_KEY, 0, -1).contains(memberIdx.toString())) {
                    log.info("이미 참여한 사용자(락 획득 후) : " + memberIdx);
                    return false;
                }

                Long participantsCount = redisTemplate.opsForList().size(EVENT_KEY);
                if (participantsCount != null && participantsCount >= MAX_PARTICIPANTS) {
                    log.info("이벤트 종료(참여 가능 인원 초과) : " + memberIdx);
                    return false;
                }

                redisTemplate.opsForList().rightPush(EVENT_KEY, memberIdx.toString());
                log.info("티켓 잡음 이벤트 당첨 : " + memberIdx);
                return true;
            } else {
                log.info("락 획득 못함 : " + memberIdx);
                return false;
            }
        } catch (InterruptedException e) {
            log.error("락 획득 중 인터럽트 발생 : " + memberIdx, e);
            Thread.currentThread().interrupt();
            return false;
        } finally {
            if (isLocked) {
                lock.unlock();
                log.info("락 해제 : " + memberIdx);
            }
        }
    }

    public void clearEvent() {
        redisTemplate.delete(EVENT_KEY);
    }
}
