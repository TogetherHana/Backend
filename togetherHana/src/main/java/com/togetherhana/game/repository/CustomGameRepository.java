package com.togetherhana.game.repository;
import java.util.List;
import java.util.Optional;

import com.togetherhana.game.entity.Game;
import com.togetherhana.game.entity.GameParticipant;

public interface CustomGameRepository {

	Optional<Game> findGameWithOptions(Long gameIdx);

	List<GameParticipant> findParticipantsByGameIdx(Long gameIdx);

	List<Game> findGameHistoryBySharingAccountIdx(Long sharingAccountIdx);
}
