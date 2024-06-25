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

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MileageRepository mileageRepository;

    public Long saveMember(SignUpRequest signUpRequest) {
        boolean joinCheck = joinedMemberCheck(signUpRequest);
        if(!joinCheck){
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
        throw new BaseException(ErrorType.JOINED_MEMBER);
    }

    public boolean joinedMemberCheck(SignUpRequest signUpRequest) {
        Member member = memberRepository.findByPhoneNumberAndName(signUpRequest.getPhoneNumber(), signUpRequest.getName());
        if(member == null) {
            return false;
        }
        return true;
    }

    public Member getMemberByDeviceToken(String deviceToken){
        Member member = memberRepository.findByDeviceToken(deviceToken);
        if(member == null) {
            throw new BaseException(ErrorType.MEMBER_BY_DEVICE_TOKEN_NOT_FOUND);
        }
        return member;
    }
}
