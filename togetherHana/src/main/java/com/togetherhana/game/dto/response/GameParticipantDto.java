package com.togetherhana.game.dto.response;

import com.togetherhana.member.entity.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GameParticipantDto {
	private String nickname;

	public static GameParticipantDto of(Member member) {
		return new GameParticipantDto(member.getNickname());
	}
}
