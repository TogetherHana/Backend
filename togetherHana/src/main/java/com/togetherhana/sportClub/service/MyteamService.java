package com.togetherhana.sportClub.service;

import com.togetherhana.base.SportsType;
import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.entity.Member;
import com.togetherhana.member.repository.MemberRepository;
import com.togetherhana.sportClub.dto.SportsClubResponse;
import com.togetherhana.sportClub.entity.MyTeam;
import com.togetherhana.sportClub.entity.SportsClub;
import com.togetherhana.sportClub.repository.MyteamRepository;
import com.togetherhana.sportClub.repository.SportsClubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyteamService {
    private final MyteamRepository myteamRepository;
    private final SportsClubRepository sportsClubRepository;
    private final MemberRepository memberRepository;

    public List<SportsClubResponse> getAllSportsClub(SportsType type){

        List<SportsClub> sportsClubs = sportsClubRepository.findByType(type);
        if(sportsClubs.isEmpty()){
            throw new BaseException(ErrorType.NO_SPORTSCLUB_INFO);
        }
        return sportsClubs.stream().map(SportsClubResponse::new).toList();
    }

    @Transactional
    public Long saveMyteam(Long sportsClubIdx, Member member) {

        // 응원팀 설정 여부 확인
        SportsClub sportsClub = savedMyteamCheck(sportsClubIdx, member.getMemberIdx());

        // 응원팀 저장
        MyTeam myTeam = MyTeam.builder().member(member).sportsClub(sportsClub).build();
        MyTeam CreatedMyTeam = myteamRepository.save(myTeam);
        return CreatedMyTeam.getMyTeamIdx();
    }

    public SportsClub savedMyteamCheck(Long sportsClubIdx, Long memberIdx) {
        // 구단 정보 조회
        SportsClub sportsClub = sportsClubRepository.findById(sportsClubIdx)
                .orElseThrow(() -> new BaseException(ErrorType.NO_SPORTSCLUB_INFO));

        // 종목별 1팀 설정 여부 체크
        MyTeam myTeam = myteamRepository.findByMember_MemberIdxAndSportsClub_Type(memberIdx,
                                                                                  sportsClub.getType());
        if(myTeam != null){
            throw new BaseException(ErrorType.ALREADY_MYTEAM_PICKED);
        }
        return sportsClub;
    }
}
