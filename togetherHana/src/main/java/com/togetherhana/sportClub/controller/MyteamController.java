package com.togetherhana.sportClub.controller;

import com.togetherhana.base.SportsType;
import com.togetherhana.member.entity.Member;
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
    public List<SportsClubResponse> getSportsCLubInfo(String type) {
        SportsType sportsType = SportsType.byName(type);
        return myteamService.getAllSportsClub(sportsType);
    }

    @PutMapping("/pick")
    public Long selectMyteam(@RequestBody Long sportsClubIdx, @RequestBody Member member) {
        return myteamService.saveMyteam(sportsClubIdx, member);
    }

}
