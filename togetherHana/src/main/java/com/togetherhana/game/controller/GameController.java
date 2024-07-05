package com.togetherhana.game.controller;


import com.togetherhana.auth.jwt.Auth;
import com.togetherhana.game.dto.response.GameHistoryResponseDto;
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

    @PostMapping("/{sharingAccountIdx}")
    public BaseResponse createGame(
            @PathVariable(name = "sharingAccountIdx") final Long sharingAccountIdx,
            @RequestBody final GameCreateRequestDto gameCreateRequestDto
	) {
        gameService.createGame(sharingAccountIdx, gameCreateRequestDto);
        return BaseResponse.success();
    }

    @PostMapping("/option")
    public BaseResponse<Long> vote(@Auth Member member, @RequestBody OptionChoiceRequestDto optionChoiceRequestDto) {
        return BaseResponse.success(gameService.vote(member.getMemberIdx(), optionChoiceRequestDto));
    }

    @PostMapping("/select")
    public BaseResponse decideGameWinner(@Auth Member member,
                                         @RequestBody OptionChoiceRequestDto optionChoiceRequestDto
	) {
        return BaseResponse.success(gameService.decideGameWinner(member.getMemberIdx(), optionChoiceRequestDto));
    }

	@GetMapping("/{gameIdx}")
	public BaseResponse getGameDetail(@Auth Member member,
									  @PathVariable(name = "gameIdx") final Long gameIdx
	) {
		return BaseResponse.success(gameService.getGameDetail(member.getMemberIdx(), gameIdx));
	}

	@GetMapping("/history/{sharingAccountIdx}")
	public BaseResponse getGameHistoryAndCurrentGame(
		@PathVariable(name = "sharingAccountIdx") final Long sharingAccountIdx
	) {
		return BaseResponse.success(gameService.getGameHistoryAndCurrentGame(sharingAccountIdx));
	}
}
