package com.togetherhana.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.togetherhana.game.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}
