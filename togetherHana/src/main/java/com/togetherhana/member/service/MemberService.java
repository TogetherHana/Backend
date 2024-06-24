package com.togetherhana.member.service;

import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.dto.MemberCreateDto;
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

    public Long saveMember(MemberCreateDto memberCreateDto) {
        boolean joinCheck = joinedMemberCheck(memberCreateDto);
        if(!joinCheck){
            // 마일리지 생성
            Mileage mileage = mileageRepository.save(Mileage.builder().amount(0L).build());
            // 회원가입 진행
            Member member = Member.builder()
                    .accountNumber(memberCreateDto.getAccountNumber())
                    .name(memberCreateDto.getName())
                    .address(memberCreateDto.getAddress())
                    .nickname(memberCreateDto.getNickname())
                    .phoneNumber(memberCreateDto.getPhoneNumber())
                    .mileage(mileage)
                    .build();
            Member savedMember = memberRepository.save(member);
            return savedMember.getMemberIdx();
        }
        throw new BaseException(ErrorType.JOINED_MEMBER);
    }

    public boolean joinedMemberCheck(MemberCreateDto memberCreateDto) {
        Member member = memberRepository.findByPhoneNumberAndName(memberCreateDto.getPhoneNumber(), memberCreateDto.getName());
        if(member == null) {
            return false;
        }
        return true;
    }
}
