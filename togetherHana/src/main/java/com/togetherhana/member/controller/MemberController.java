package com.togetherhana.member.controller;

import com.togetherhana.base.SportsType;
import com.togetherhana.member.dto.MemberCreateDto;
import com.togetherhana.member.service.MemberService;
import com.togetherhana.sportClub.dto.SportsClubResponse;
import com.togetherhana.sportClub.service.MyteamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public Long register(@RequestBody MemberCreateDto memberCreateDto) {
        return memberService.saveMember(memberCreateDto);
    }

}
