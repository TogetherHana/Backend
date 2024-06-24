package com.togetherhana.sportClub.dto;

import com.togetherhana.member.entity.Member;
import com.togetherhana.sportClub.entity.MyTeam;
import com.togetherhana.sportClub.entity.SportsClub;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyteamRequest {
    private Long sportsClubIdx;
    private Long memberIdx;

//    public MyTeam toEntity(Member member, SportsClub sportsClub){
//        return MyTeam.builder().sportsClub(sportsClub).member(member).build();
//    }
}
