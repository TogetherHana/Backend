package com.togetherhana.game.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.togetherhana.game.entity.Game;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class GameResultDto {
	private Long gameIdx;
	private String gameTitle;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
	private LocalDateTime createdAt;
	private LocalDateTime deadline;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<MemberDto> winners;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<MemberDto> losers;

	public static GameResultDto of(Game game, List<MemberDto> winners, List<MemberDto> losers) {
		return GameResultDto.builder()
			.gameIdx(game.getId())
			.gameTitle(game.getGameTitle())
			.createdAt(game.getCreatedAt())
			.deadline(game.getDeadline())
			.winners(winners)
			.losers(losers)
			.build();
	}

	public static GameResultDto of(Game game) {
		return GameResultDto.builder()
			.gameIdx(game.getId())
			.gameTitle(game.getGameTitle())
			.createdAt(game.getCreatedAt())
			.deadline(game.getDeadline())
			.build();
	}
}
