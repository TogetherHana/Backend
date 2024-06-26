package com.togetherhana.sportClub.controller;

import com.togetherhana.auth.jwt.Auth;
import com.togetherhana.base.BaseResponse;
import com.togetherhana.base.SportsType;
import com.togetherhana.member.entity.Member;
import com.togetherhana.sportClub.dto.PickSportsClubRequest;
import com.togetherhana.sportClub.dto.SportsClubResponse;
import com.togetherhana.sportClub.entity.MyTeam;
import com.togetherhana.sportClub.service.MyteamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/sports-club")
public class MyteamController {

    private final MyteamService myteamService;

    @GetMapping("/all")
    public BaseResponse<List<SportsClubResponse>> getSportsCLubInfo(String type) {
        SportsType sportsType = SportsType.byName(type);
        log.info(sportsType.toString());
        return BaseResponse.success(myteamService.getAllSportsClub(sportsType));
    }

    @PutMapping("/pick")
    public BaseResponse<Long> selectMyteam(@Auth Member member, @RequestBody Long sportsClubIdx) {
        //log.info("응원팀선택:" + sportsClubIdx);
        return BaseResponse.success(myteamService.saveMyteam(sportsClubIdx, member.getMemberIdx()));
    }

}
