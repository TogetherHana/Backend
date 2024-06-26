package com.togetherhana.game.controller;


import com.togetherhana.auth.jwt.Auth;
import com.togetherhana.member.entity.Member;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.togetherhana.base.BaseResponse;
import com.togetherhana.game.dto.request.GameCreateRequestDto;
import com.togetherhana.game.dto.request.OptionChoiceRequestDto;
import com.togetherhana.game.dto.response.GameDetailResponseDto;
import com.togetherhana.game.dto.response.GameSelectResponseDto;
import com.togetherhana.game.service.GameService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
public class GameController {
    private final GameService gameService;

    @PostMapping("/game/{sharingAccountIdx}")
    public BaseResponse createGame(
            @PathVariable final Long sharingAccountIdx,
            @RequestBody final GameCreateRequestDto gameCreateRequestDto) {
        gameService.createGame(sharingAccountIdx, gameCreateRequestDto);
        return BaseResponse.success();
    }

    @PostMapping("/game/option")
    public BaseResponse vote(@Auth Member member, 
                             @RequestBody OptionChoiceRequestDto optionChoiceRequestDto) {
        gameService.vote(member.getMemberIdx(), optionChoiceRequestDto);
        return BaseResponse.success();
    }

    @PostMapping("/game/select")
    public BaseResponse decideGameWinner(@Auth Member member,
                                         @RequestBody OptionChoiceRequestDto optionChoiceRequestDto) {
        GameSelectResponseDto gameSelectResponseDto = gameService.decideGameWinner(member.getMemberIdx(),
                optionChoiceRequestDto);
        return BaseResponse.success(gameSelectResponseDto);
    }

	@GetMapping("/{gameIdx}")
	public BaseResponse getGameDetail(@Auth Member member,
                                    @PathVariable(name = "gameIdx") final Long gameIdx) {
		GameDetailResponseDto gameDetail = gameService.getGameDetail(member.getMemberIdx(), gameIdx);
		return BaseResponse.success(gameDetail);
	}
}
