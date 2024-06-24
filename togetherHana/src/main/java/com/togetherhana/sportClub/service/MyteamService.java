package com.togetherhana.sportClub.service;

import com.togetherhana.base.SportsType;
import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.entity.Member;
import com.togetherhana.member.repository.MemberRepository;
import com.togetherhana.sportClub.dto.MyteamRequest;
import com.togetherhana.sportClub.dto.SportsClubResponse;
import com.togetherhana.sportClub.entity.MyTeam;
import com.togetherhana.sportClub.entity.SportsClub;
import com.togetherhana.sportClub.repository.MyteamRepository;
import com.togetherhana.sportClub.repository.SportsClubRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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

    public Long saveMyteam(MyteamRequest myteamRequest) {
        Member member = memberRepository.findById(myteamRequest.getMemberIdx())
                .orElseThrow(() -> new BaseException(ErrorType.INVAILD_MEMBER_IDX));
        SportsClub sportsClub = sportsClubRepository.findById(myteamRequest.getSportsClubIdx())
                .orElseThrow(() -> new BaseException(ErrorType.NO_SPORTSCLUB_INFO));

        MyTeam myTeam = MyTeam.builder().member(member).sportsClub(sportsClub).build();
        MyTeam CreatedMyTeam = myteamRepository.save(myTeam);
        return CreatedMyTeam.getMyTeamIdx();
    }
}
