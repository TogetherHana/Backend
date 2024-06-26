package com.togetherhana.member.service;

import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.dto.SignUpRequest;
import com.togetherhana.member.entity.Member;
import com.togetherhana.member.repository.MemberRepository;
import com.togetherhana.mileage.entity.Mileage;
import com.togetherhana.mileage.repository.MileageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MileageRepository mileageRepository;

    @Transactional
    public Long saveMember(SignUpRequest signUpRequest) {
        // 가입여부 확인
        joinedMemberCheck(signUpRequest);

        // 마일리지 생성
        Mileage mileage = mileageRepository.save(Mileage.builder().amount(0L).build());
        // 회원가입 진행
        Member member = Member.builder()
                .accountNumber(signUpRequest.getAccountNumber())
                .name(signUpRequest.getName())
                .address(signUpRequest.getAddress())
                .nickname(signUpRequest.getNickname())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .mileage(mileage)
                .build();
        Member savedMember = memberRepository.save(member);
        return savedMember.getMemberIdx();

    }

    public void joinedMemberCheck(SignUpRequest signUpRequest) {
        Member member = memberRepository.findByPhoneNumberAndName(signUpRequest.getPhoneNumber(), signUpRequest.getName());
        if(member != null) {
            throw new BaseException(ErrorType.JOINED_MEMBER);
        }
    }

    public Member getMemberByDeviceToken(String deviceToken){
        Member member = memberRepository.findByDeviceToken(deviceToken);
        if(member == null) {
            throw new BaseException(ErrorType.MEMBER_BY_DEVICE_TOKEN_NOT_FOUND);
        }
        return member;
    }

    // 닉네임 중복 체크
    public Boolean nicknameDuplicationCheck(String nickname) {
        Member member = memberRepository.findByNickname(nickname);
        if(member != null) {
            return true;
        }
        return false;
    }

}
