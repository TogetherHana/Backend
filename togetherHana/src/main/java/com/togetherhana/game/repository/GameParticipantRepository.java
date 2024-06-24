package com.togetherhana.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.togetherhana.game.entity.GameParticipant;

public interface GameParticipantRepository extends JpaRepository<GameParticipant, Long> {
}
