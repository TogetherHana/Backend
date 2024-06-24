package com.togetherhana.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.togetherhana.game.entity.GameOption;

public interface GameOptionRepository extends JpaRepository<GameOption, Long> {
}
