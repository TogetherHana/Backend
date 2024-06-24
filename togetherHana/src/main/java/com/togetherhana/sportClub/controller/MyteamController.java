package com.togetherhana.sportClub.controller;

import com.togetherhana.base.SportsType;
import com.togetherhana.sportClub.dto.MyteamRequest;
import com.togetherhana.sportClub.dto.SportsClubResponse;
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

    @GetMapping("/myteam")
    public List<SportsClubResponse> getSportsCLubInfo(String type) {
        SportsType sportsType = SportsType.byName(type);
        return myteamService.getAllSportsClub(sportsType);
    }

    @PutMapping("/myteam/pick")
    public Long selectMyteam(@RequestBody MyteamRequest myteamRequest) {
        return myteamService.saveMyteam(myteamRequest);
    }

}
