package com.togetherhana.systemEvent;

import com.togetherhana.systemEvent.service.SystemEventRedissonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class ConcurrentTest {

    @Autowired
    private SystemEventRedissonService eventService;

    private static final int THREAD_COUNT = 10;

    @Test
    public void firstComeEventTest() {
        // 이벤트 초기화
        eventService.clearEvent();

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

        for (Long i = 0L; i < THREAD_COUNT; i++) {
            Long memberIdx = i + 1;
            executorService.submit(() -> sendRequest(memberIdx));
        }

        executorService.shutdown();
    }

    private void sendRequest(Long memberIdx) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/event/get-ticket/" + memberIdx;

        try {
            Boolean response = restTemplate.postForObject(url, null, Boolean.class);
            System.out.println("Request for memberIdx: " + memberIdx + ", Response: " + response);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Request for memberIdx: " + memberIdx + " failed: " + e.getMessage());
        }
    }
}
