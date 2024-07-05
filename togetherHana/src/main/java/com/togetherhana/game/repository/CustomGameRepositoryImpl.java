package com.togetherhana.game.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.togetherhana.game.entity.Game;
import com.togetherhana.game.entity.GameParticipant;
import com.togetherhana.game.entity.QGame;

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
	public Optional<Game> findGameWithOptions(Long gameIdx) {
		Game game = queryFactory.selectFrom(QGame.game)
			.leftJoin(QGame.game.gameOptions, gameOption).fetchJoin()
			.where(QGame.game.id.eq(gameIdx))
			.distinct()
			.fetchOne();

		return Optional.ofNullable(game);
	}

	@Override
	public List<GameParticipant> findParticipantsByGameIdx(Long gameIdx) {
		return queryFactory.selectFrom(gameParticipant)
			.leftJoin(gameParticipant.sharingMember, sharingMember).fetchJoin()
			.leftJoin(sharingMember.member, member).fetchJoin()
			.where(gameParticipant.game.id.eq(gameIdx))
			.fetch();
	}

	@Override
	public List<Game> findGameHistoryBySharingAccountIdx(Long sharingAccountIdx) {
		return queryFactory
			.selectFrom(game)
			.leftJoin(game.gameParticipants, gameParticipant).fetchJoin()
			.leftJoin(gameParticipant.sharingMember, sharingMember).fetchJoin()
			.leftJoin(sharingMember.member, member).fetchJoin()
			.where(game.sharingAccount.sharingAccountIdx.eq(sharingAccountIdx))
			.orderBy(game.createdAt.desc())
			.fetch();
	}
}
