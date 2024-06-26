package com.togetherhana.game.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.togetherhana.game.entity.Game;
import com.togetherhana.game.entity.GameParticipant;

public interface GameParticipantRepository extends JpaRepository<GameParticipant, Long> {

	List<GameParticipant> findByGame(Game game);
}
