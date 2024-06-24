package com.togetherhana.sportClub.dto;

import com.togetherhana.sportClub.entity.SportsClub;
import lombok.ToString;

@ToString
public class SportsClubResponse {
    private Long sportsClubIdx;
    private String name;
    private String imgUrl;

    public SportsClubResponse(SportsClub sportsClub) {
        this.sportsClubIdx = sportsClub.getSportsClubIdx();
        this.name = sportsClub.getName();
        this.imgUrl = sportsClub.getImgUrl();
    }
}
