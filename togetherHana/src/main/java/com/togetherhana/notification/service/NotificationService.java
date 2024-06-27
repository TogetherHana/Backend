package com.togetherhana.notification.service;

import com.togetherhana.notification.event.NotificationEvent;
import com.togetherhana.notification.fcm.MessageSendManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishPushEvent(NotificationEvent event) {
        applicationEventPublisher.publishEvent(event);
    }
}
