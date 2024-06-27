package com.togetherhana.notification.service;

import com.togetherhana.notification.event.NotificationEvent;
import com.togetherhana.notification.fcm.FcmMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventListener {

    private final FcmMessageSender fcmMessageSender;

    @EventListener
    @Async("notificationAsyncExecutor")
    public void handleNotificationEvent(NotificationEvent event) {
        log.info("비동기 알림 START, Thread: " + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
        fcmMessageSender.sendMessageTo(event.getDeviceToken(), event.getTitle(), event.getBody());
        log.info("비동기 알림 END, Thread: " + Thread.currentThread().getId() + " " + Thread.currentThread().getName());
    }
}
