package com.togetherhana.game.dto.response;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GameSelectResponseDto {
		String gameTitle;
		List<MemberDto> losers;

		public static GameSelectResponseDto of(String gameTitle, List<MemberDto> losers) {
			return new GameSelectResponseDto(gameTitle, losers);
		}
}
