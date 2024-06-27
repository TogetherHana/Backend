package com.togetherhana.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.togetherhana.game.entity.Game;
import com.togetherhana.sharingAccount.entity.SharingAccount;

public interface GameRepository extends JpaRepository<Game, Long>, CustomGameRepository {

	boolean existsBySharingAccountAndIsPlaying(SharingAccount sharingAccount, Boolean isPlaying);
}
