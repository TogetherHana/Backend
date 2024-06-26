package com.togetherhana.game.dto.response;

import java.util.List;

import com.togetherhana.game.entity.GameOption;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class GameOptionDto {
	private Long gameOptionIdx;
	private String optionTitle;
	private List<MemberDto> votingMembers;

	public static GameOptionDto of(GameOption gameOption, List<MemberDto> memberDtos) {
		return GameOptionDto.builder()
			.gameOptionIdx(gameOption.getGameOptionIdx())
			.optionTitle(gameOption.getOptionTitle())
			.votingMembers(memberDtos)
			.build();
		}
}
