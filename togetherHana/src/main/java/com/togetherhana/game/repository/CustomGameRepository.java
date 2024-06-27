package com.togetherhana.game.repository;
import java.util.List;
import java.util.Optional;

import com.togetherhana.game.entity.Game;

public interface CustomGameRepository {

	Optional<Game> findGameDetailByGameIdx(Long gameIdx);

	List<Game> findGameHistoryBySharingAccountIdx(Long sharingAccountIdx);
}
