package com.togetherhana.member.controller;

import com.togetherhana.auth.jwt.Auth;
import com.togetherhana.base.BaseResponse;
import com.togetherhana.member.dto.MyInfoResponse;
import com.togetherhana.member.dto.SignUpRequest;
import com.togetherhana.member.entity.Member;
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
    public BaseResponse<Boolean> nicknameCheck(@RequestParam("nickname") String nickname) {
        log.info("nickname is {}", nickname);
        return BaseResponse.success(memberService.nicknameDuplicationCheck(nickname));
    }

    @GetMapping("/me")
    public BaseResponse<MyInfoResponse> getMyInfo(@Auth Member member) {
        return BaseResponse.success(memberService.getMyInfoByMemberIdx(member.getMemberIdx()));
    }

}
