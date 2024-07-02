package com.togetherhana.systemEvent;

import com.togetherhana.systemEvent.service.SystemEventSortedSetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SystemEventTest {

    @Autowired
    private SystemEventSortedSetService systemEventService;

    @BeforeEach
    public void setUp() {
        systemEventService.clearEvent();
    }

    @Test
    public void testConcurrentEventParticipation() throws InterruptedException {
        int numThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Future<Boolean>> futures = new ArrayList<>();

        for (long i = 1; i <= numThreads; i++) {
            long memberId = i;
            systemEventService.addOnRedis(memberId);
            Callable<Boolean> task = () -> systemEventService.getWinner(memberId);
            futures.add(executorService.submit(task));
        }


        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        int successCount = 0;
        int failureCount = 0;

        for (Future<Boolean> future : futures) {
            try {
                if (future.get()) {
                    successCount++;
                } else {
                    failureCount++;
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Success count: " + successCount);
        System.out.println("Failure count: " + failureCount);

        // Validate that exactly 5 participants were successful
        //assertTrue(successCount == 5);
    }


//    @Test
//    public void resetTest(){
//        systemEventService.clearEvent();
//    }
}
