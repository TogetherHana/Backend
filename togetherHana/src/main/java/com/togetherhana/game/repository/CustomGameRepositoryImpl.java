package com.togetherhana.game.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.togetherhana.game.entity.Game;

import static com.togetherhana.game.entity.QGame.game;
import static com.togetherhana.game.entity.QGameOption.gameOption;
import static com.togetherhana.game.entity.QGameParticipant.gameParticipant;
import static com.togetherhana.member.entity.QMember.member;
import static com.togetherhana.sharingAccount.entity.QSharingMember.sharingMember;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomGameRepositoryImpl implements CustomGameRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Game> findGameDetailByGameIdx(Long gameIdx) {

		Game finedGame = queryFactory
			.selectFrom(game)
			.leftJoin(game.gameParticipants, gameParticipant).fetchJoin()
			.leftJoin(gameParticipant.gameOption, gameOption).fetchJoin()
			.leftJoin(gameParticipant.sharingMember, sharingMember).fetchJoin()
			.leftJoin(sharingMember.member, member).fetchJoin()
			.where(game.id.eq(gameIdx))
			.fetchOne();

		return Optional.ofNullable(finedGame);
	}
}
