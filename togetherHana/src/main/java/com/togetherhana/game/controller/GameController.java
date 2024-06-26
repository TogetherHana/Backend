package com.togetherhana.game.controller;

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
		@RequestBody final GameCreateRequestDto gameCreateRequestDto)
	{
		gameService.createGame(sharingAccountIdx, gameCreateRequestDto);
		return BaseResponse.success();
	}

	@PostMapping("/option")
	public BaseResponse vote(@RequestBody OptionChoiceRequestDto optionChoiceRequestDto) {

		/**
		 * 추후 수정
		 */
		Long memberIdx = 1L;
		gameService.vote(memberIdx, optionChoiceRequestDto);
		return BaseResponse.success();
	}

	@PostMapping("/select")
	public BaseResponse decideGameWinner(@RequestBody OptionChoiceRequestDto optionChoiceRequestDto) {

		/**
		 * 추후 수정
		 */
		Long memberIdx = 2L;
		GameSelectResponseDto gameSelectResponseDto = gameService.decideGameWinner(memberIdx, optionChoiceRequestDto);
		return BaseResponse.success(gameSelectResponseDto);
	}

	@GetMapping("/{gameIdx}")
	public BaseResponse getGameDetail(@PathVariable(name = "gameIdx") final Long gameIdx) {

		/**
		 * 추후 수정
		 */
		Long memberIdx = 1L;
		GameDetailResponseDto gameDetail = gameService.getGameDetail(memberIdx, gameIdx);
		return BaseResponse.success(gameDetail);
	}
}
