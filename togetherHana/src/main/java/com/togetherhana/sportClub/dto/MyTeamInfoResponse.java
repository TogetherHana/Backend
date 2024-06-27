package com.togetherhana.sportClub.dto;

import com.togetherhana.base.SportsType;
import com.togetherhana.sportClub.entity.MyTeam;
import com.togetherhana.sportClub.entity.SportsClub;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class MyTeamInfoResponse {

    private Long myTeamIdx;

    private Long sportsClubIdx;

    private String imgUrl;

    private String sportsClubName;

    private SportsType sportsType;

    public MyTeamInfoResponse(MyTeam myTeam) {
        this.myTeamIdx = myTeam.getMyTeamIdx();
        this.sportsClubIdx = myTeam.getSportsClub().getSportsClubIdx();
        this.imgUrl = myTeam.getSportsClub().getImgUrl();
        this.sportsClubName = myTeam.getSportsClub().getName();
        this.sportsType = myTeam.getSportsClub().getType();
    }
}
