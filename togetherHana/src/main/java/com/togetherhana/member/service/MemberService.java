package com.togetherhana.member.service;

import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.dto.MyInfoResponse;
import com.togetherhana.member.dto.SignUpRequest;
import com.togetherhana.member.entity.Member;
import com.togetherhana.member.repository.MemberRepository;
import com.togetherhana.mileage.entity.Mileage;
import com.togetherhana.mileage.repository.MileageRepository;
import com.togetherhana.sportClub.entity.MyTeam;
import com.togetherhana.sportClub.repository.MyteamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MileageRepository mileageRepository;
    private final MyteamRepository myteamRepository;

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

    // 내정보 조회(마일리지, 응원팀, 닉네임) - 메인 화면
    public MyInfoResponse getMyInfoByMemberIdx(Long memberIdx) {

       Member member = memberRepository.findById(memberIdx)
               .orElseThrow(() -> new BaseException(ErrorType.INVAILD_MEMBER_IDX));
       //log.info("마일리지 잔액 : "+ member.getMileage().getAmount());

       List<MyTeam> myTeamList = myteamRepository.findByMember_MemberIdx(memberIdx);

       return new MyInfoResponse(member,myTeamList);
    }
}
