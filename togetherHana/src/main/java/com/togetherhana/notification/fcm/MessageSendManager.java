package com.togetherhana.notification.fcm;

public interface MessageSendManager {
    void sendMessageTo(String targetToken, String title, String body);
}
