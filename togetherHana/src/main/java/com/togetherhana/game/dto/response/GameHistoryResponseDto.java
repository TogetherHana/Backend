package com.togetherhana.game.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class GameHistoryResponseDto {
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private GameResultDto playingGame;
	private List<GameResultDto> gameHistory;

	public static GameHistoryResponseDto of(GameResultDto playingGame, List<GameResultDto> gameHistory) {
		return GameHistoryResponseDto.builder()
			.playingGame(playingGame)
			.gameHistory(gameHistory)
			.build();
	}
}
