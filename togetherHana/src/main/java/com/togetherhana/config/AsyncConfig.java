package com.togetherhana.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean("notificationAsyncExecutor")
    public TaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(12);
        executor.setThreadNamePrefix("TogetherHana-Thread: ");
        executor.initialize();
        return executor;
    }

    @Bean("notificationCallBackExecutor")
    public TaskExecutor getCallBackExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setThreadNamePrefix("TogetherHana-Call-Back-Thread: ");
        executor.initialize();
        return executor;
    }

    @Bean("eventConcludeExecutor")
    public TaskExecutor getEventConcludeExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setThreadNamePrefix("TogetherHana-Event-Thread: ");
        executor.initialize();
        return executor;
    }
}
