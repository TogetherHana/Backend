package com.togetherhana.auth.service;


import com.togetherhana.auth.dto.AccountCertificationDto;
import com.togetherhana.auth.dto.AccountCheckDto;
import com.togetherhana.auth.dto.JwtToken;
import com.togetherhana.auth.jwt.JwtTokenProvider;
import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.togetherhana.member.entity.Member;
import com.togetherhana.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
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

    // JWT
    public JwtToken generateJwtToken(Member member) {
        // JWT 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(member.getMemberIdx());
        String refreshToken = jwtTokenProvider.createRefreshToken(member.getMemberIdx());

        // Redis에 Refresh Token 저장 (만료 시간 설정을 통해 자동 삭제 처리)
        redisTemplate.opsForValue()
                .set("RT:" + member.getMemberIdx(), refreshToken, jwtTokenProvider.getRefreshExpireTime(),
                        TimeUnit.MILLISECONDS);
        return new JwtToken(accessToken, refreshToken);
    }

    public JwtToken reissue(String refreshToken) {
        // Refresh Token 검증
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BaseException(ErrorType.EXPIRED_TOKEN);
        }

        // refreshToken에서 User ID를 가져옵니다.
        Long userId = jwtTokenProvider.getMemberIdxFromToken(refreshToken);

        // Redis에서 저장된 Refresh Token 값을 가져옵니다.
        String storedRefreshToken = (String) redisTemplate.opsForValue().get("RT:" + userId);

        // 저장된 Refresh Token이 없거나 일치하지 않는 경우
        if (ObjectUtils.isEmpty(storedRefreshToken) || !storedRefreshToken.equals(refreshToken)) {
            throw new BaseException(ErrorType.INVALID_REFRESH_TOKEN);
        }

        // 새로운 토큰 생성
        return generateJwtToken(memberRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorType.INVALID_MEMBER_IDX)));

    }
}
