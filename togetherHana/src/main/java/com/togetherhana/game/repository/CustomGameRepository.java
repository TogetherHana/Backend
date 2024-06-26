package com.togetherhana.game.repository;
import java.util.Optional;

import com.togetherhana.game.entity.Game;

public interface CustomGameRepository {

	Optional<Game> findGameDetailByGameIdx(Long gameIdx);
}
