package com.togetherhana.game.service;

import static com.togetherhana.exception.ErrorType.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togetherhana.exception.BaseException;
import com.togetherhana.game.dto.GameOptionDto;
import com.togetherhana.game.dto.request.GameCreateRequestDto;
import com.togetherhana.game.entity.Game;
import com.togetherhana.game.entity.GameOption;
import com.togetherhana.game.repository.GameOptionRepository;
import com.togetherhana.game.repository.GameRepository;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import com.togetherhana.sharingAccount.service.SharingAccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

	private final GameRepository gameRepository;
	private final GameOptionRepository gameOptionRepository;
	private final SharingAccountService sharingAccountService;

	@Transactional
	public void createGame(Long sharingAccountIdx, GameCreateRequestDto gameCreateRequestDto) {

		SharingAccount sharingAccount = sharingAccountService.findBySharingAccountIdx(sharingAccountIdx);

		verifyIsThereOngoingGame(sharingAccount);

		Game game = Game.builder()
			.gameTitle(gameCreateRequestDto.getGameTitle())
			.deadline(gameCreateRequestDto.getDeadLine())
			.fine(gameCreateRequestDto.getFine())
			.sharingAccount(sharingAccount)
			.build();
		Game savedGame = gameRepository.save(game);

		List<GameOption> gameOptions = makeGameOptions(savedGame, gameCreateRequestDto.getGameOptions());
		gameOptionRepository.saveAll(gameOptions);
	}

	private void verifyIsThereOngoingGame(SharingAccount sharingAccount) {
		if (gameRepository.existsBySharingAccountAndIsPlaying(sharingAccount, true)) {
			throw new BaseException(ALREADY_GAME_CREATED);
		}
	}

	private List<GameOption> makeGameOptions(Game savedGame, List<GameOptionDto> gameOptionDtos) {
		return gameOptionDtos.stream()
			.map(optionDto -> GameOption.builder()
				.game(savedGame)
				.optionTitle(optionDto.getOptionTitle())
				.build())
			.collect(Collectors.toList());
	}

}
