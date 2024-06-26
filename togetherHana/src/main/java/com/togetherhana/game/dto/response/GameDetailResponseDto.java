package com.togetherhana.game.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.togetherhana.game.entity.Game;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class GameDetailResponseDto {
	@JsonProperty("isVotingClosed")
	private boolean isVotingClosed;
	@JsonProperty("isVotingMember")
	private boolean isVotingMember;
	private String gameTitle;
	private LocalDateTime deadline;
	private Integer fine;
	private List<GameOptionDto> gameOptionDtos;

	public static GameDetailResponseDto of(
		Boolean isVotingClosed,
		Boolean isVotingMember,
		Game game,
		List<GameOptionDto> gameOptionDtos
	) {
		return GameDetailResponseDto.builder()
			.isVotingClosed(isVotingClosed)
			.isVotingMember(isVotingMember)
			.gameTitle(game.getGameTitle())
			.deadline(game.getDeadline())
			.fine(game.getFine())
			.gameOptionDtos(gameOptionDtos)
			.build();
	}
}

