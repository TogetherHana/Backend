package com.togetherhana.sharingAccount.service;

import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.entity.Member;
import com.togetherhana.sharingAccount.dto.ParticipateRequest;
import com.togetherhana.sharingAccount.dto.SharingAccountResponse;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import com.togetherhana.sharingAccount.entity.SharingMember;
import com.togetherhana.sharingAccount.repository.SharingAccountRepository;
import com.togetherhana.sharingAccount.repository.SharingMemberRepository;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final SharingAccountRepository sharingAccountRepository;
    private final SharingMemberRepository sharingMemberRepository;

    public String createInvitationCode(Long sharingAccountIdx) {
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(uuid, sharingAccountIdx.toString(),30, TimeUnit.MINUTES);
        return uuid;
    }

    public SharingAccountResponse getInvitationInfo(String invitationCode) {
        Object value = redisTemplate.opsForValue().get(invitationCode);
        if (value == null) {
            throw new BaseException(ErrorType.WRONG_INVITATION_CODE);
        }
        Long sharingAccountIdx = Long.parseLong((String) value);
        SharingAccount sharingAccount = sharingAccountRepository.findById(sharingAccountIdx).orElseThrow(
                () -> new BaseException(ErrorType.SHARING_ACCOUNT_NOT_FOUND)
        );
        return SharingAccountResponse.invitation(sharingAccount);
    }

    @Transactional
    public boolean participate(Member member, ParticipateRequest participateRequest) {
        verifyIsRightInvitationCode(participateRequest);
        SharingAccount sharingAccount = sharingAccountRepository.findById(participateRequest.getSharingAccountIdx())
                .orElseThrow(
                        () -> new BaseException(ErrorType.SHARING_ACCOUNT_NOT_FOUND)
                );

        SharingMember sharingMember = SharingMember.builder()
                .member(member)
                .sharingAccount(sharingAccount)
                .isLeader(Boolean.FALSE)
                .build();
        try {
            sharingMemberRepository.save(sharingMember);
            redisTemplate.expire(participateRequest.getInvitationCode(), 1, TimeUnit.SECONDS);
        } catch (DataIntegrityViolationException e) {
            throw new BaseException(ErrorType.INTERNAL_SERVER);
        }
        return Boolean.TRUE;
    }

    private void verifyIsRightInvitationCode(ParticipateRequest participateRequest) {
        Object o = redisTemplate.opsForValue().get(participateRequest.getInvitationCode());
        if (o == null) {
            throw new BaseException(ErrorType.WRONG_INVITATION_CODE);
        }
        Long sharingAccountIdx = Long.parseLong((String) o);
        if (!sharingAccountIdx.equals(participateRequest.getSharingAccountIdx())) {
            throw new BaseException(ErrorType.NOT_COINCIDE_INVITATION);
        }
    }
}
