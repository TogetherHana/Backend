package com.togetherhana.game.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.togetherhana.base.BaseResponse;
import com.togetherhana.game.dto.request.GameCreateRequestDto;
import com.togetherhana.game.service.GameService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class GameController {

	private final GameService gameService;

	@PostMapping("/game/{sharingAccountIdx}")
	public BaseResponse createGame(
		@PathVariable final Long sharingAccountIdx,
		@RequestBody final GameCreateRequestDto gameCreateRequestDto)
	{
		gameService.createGame(sharingAccountIdx, gameCreateRequestDto);
		return BaseResponse.success();
	}

}
