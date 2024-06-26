package com.togetherhana.game.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.togetherhana.game.entity.Game;
import com.togetherhana.game.entity.QGame;
import com.togetherhana.game.entity.QGameOption;
import com.togetherhana.game.entity.QGameParticipant;
import com.togetherhana.member.entity.QMember;
import com.togetherhana.sharingAccount.entity.QSharingMember;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomGameRepositoryImpl implements CustomGameRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Game> findGameDetailByGameIdx(Long gameIdx) {
		QGame qGame = QGame.game;
		QGameOption qGameOption = QGameOption.gameOption;
		QGameParticipant qGameParticipant = QGameParticipant.gameParticipant;
		QSharingMember qSharingMember = QSharingMember.sharingMember;
		QMember qMember = QMember.member;

		Game game = queryFactory
			.selectFrom(qGame)
			.leftJoin(qGame.gameParticipants, qGameParticipant).fetchJoin()
			.leftJoin(qGameParticipant.gameOption, qGameOption).fetchJoin()
			.leftJoin(qGameParticipant.sharingMember, qSharingMember).fetchJoin()
			.leftJoin(qSharingMember.member, qMember).fetchJoin()
			.where(qGame.id.eq(gameIdx))
			.fetchOne();

		return Optional.ofNullable(game);
	}
}
