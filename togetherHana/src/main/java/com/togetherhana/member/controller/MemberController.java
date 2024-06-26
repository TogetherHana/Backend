package com.togetherhana.member.controller;

import com.togetherhana.base.BaseResponse;
import com.togetherhana.member.dto.SignUpRequest;
import com.togetherhana.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public BaseResponse<Long> register(@RequestBody SignUpRequest signUpRequest) {
        return BaseResponse.success(memberService.saveMember(signUpRequest));
    }
    @GetMapping("/nickname-check")
    public BaseResponse<Boolean> register(@RequestParam("nickname") String nickname) {
        log.info("nickname is {}", nickname);
        return BaseResponse.success(memberService.nicknameDuplicationCheck(nickname));
    }


}
