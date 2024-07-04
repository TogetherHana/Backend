package com.togetherhana.notification.event;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class NotificationEvent {
    private final String deviceToken;
    private final String title;
    private final String body;
}