package com.togetherhana.auth.service;


import com.togetherhana.auth.dto.AccountCertificationDto;
import com.togetherhana.auth.dto.AccountCheckDto;
import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final List<String> wordSet = List.of("물구나무", "바람개비", "손목시계", "강아지풀", "사과나무", "호랑나비", "고양이발", "자동차문", "바다거북",
            "유리창문");

    public Boolean sendCoinWithAuthCode(AccountCheckDto accountCheckDto) {
        String authCode = makeAccountAuthCode();
        log.info("Send Coin To {} with Code {}", accountCheckDto.getAccountNumber(), authCode);
        redisTemplate.opsForValue().set(accountCheckDto.getAccountNumber(), authCode.substring(4));
        return Boolean.TRUE;
    }

    public Boolean verifyAccountAuthCode(AccountCertificationDto accountCertificationDto) {
        Object certification = redisTemplate.opsForValue().get(accountCertificationDto.getAccountNumber());
        if (certification == null) {
            throw new BaseException(ErrorType.WRONG_ACCOUNT_NUMBER);
        }
        String code = (String) certification;
        if (!code.equals(accountCertificationDto.getCertificationNumber())) {
            throw new BaseException(ErrorType.WRONG_CERTIFICATION_NUMBER);
        }

        redisTemplate.delete(accountCertificationDto.getAccountNumber());

        return Boolean.TRUE;
    }

    private String makeAccountAuthCode() {
        Random random = new Random();
        return wordSet.get(random.nextInt(10)) + (random.nextInt(900) + 100);
    }
}
