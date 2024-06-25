package com.togetherhana.game.dto.response;

import com.togetherhana.member.entity.Member;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDto {
	private String nickname;

	public static MemberDto of(Member member) {
		return new MemberDto(member.getNickname());
	}
}
