package com.togetherhana.sms.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsService {

    private final DefaultMessageService messageService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final String CERTIFICATION = "[본인 인증 번호] : ";

    public SmsService(@Value("${coolsms.apikey}") String apiKey, @Value("${coolsms.apiPrivate}") String apiSecret,
                      RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey,
                apiSecret, "https://api.coolsms.co.kr");
    }

    public Boolean sendCertificationMessage(String phoneNumber) {
        String certification = createCertificationNumber();
        String text = CERTIFICATION + certification + " 입니다.";
        sendMessage(phoneNumber, text);
        redisTemplate.opsForValue().set(phoneNumber, certification, 5, TimeUnit.MINUTES);
        return Boolean.TRUE;
    }

    public void sendMessage(String phoneNumber, String text) {
        Message message = new Message();
        message.setFrom("01090410672");
        message.setTo(phoneNumber);
        message.setText(text);

        try {
            messageService.send(message);
        } catch (NurigoMessageNotReceivedException | NurigoEmptyResponseException | NurigoUnknownException e) {
            throw new RuntimeException(e);
        }
    }

    private String createCertificationNumber() {
        Random random = new Random();
        return String.valueOf(random.nextInt(9000) + 1000);
    }
}
