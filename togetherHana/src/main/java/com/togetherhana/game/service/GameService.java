package com.togetherhana.game.service;

import static com.togetherhana.exception.ErrorType.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.togetherhana.exception.BaseException;
import com.togetherhana.game.dto.request.GameOptionRequestDto;
import com.togetherhana.game.dto.request.GameCreateRequestDto;
import com.togetherhana.game.dto.request.OptionChoiceRequestDto;
import com.togetherhana.game.dto.response.GameDetailResponseDto;
import com.togetherhana.game.dto.response.GameHistoryResponseDto;
import com.togetherhana.game.dto.response.GameOptionDto;
import com.togetherhana.game.dto.response.GameResultDto;
import com.togetherhana.game.dto.response.MemberDto;
import com.togetherhana.game.dto.response.GameSelectResponseDto;
import com.togetherhana.game.entity.Game;
import com.togetherhana.game.entity.GameOption;
import com.togetherhana.game.entity.GameParticipant;
import com.togetherhana.game.repository.GameOptionRepository;
import com.togetherhana.game.repository.GameParticipantRepository;
import com.togetherhana.game.repository.GameRepository;
import com.togetherhana.member.entity.Member;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import com.togetherhana.sharingAccount.entity.SharingMember;
import com.togetherhana.sharingAccount.service.SharingAccountService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

	private final GameRepository gameRepository;
	private final GameOptionRepository gameOptionRepository;
	private final GameParticipantRepository gameParticipantRepository;
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

	private List<GameOption> makeGameOptions(Game savedGame, List<GameOptionRequestDto> gameOptionRequestDtos) {
		return gameOptionRequestDtos.stream()
			.map(optionDto -> GameOption.builder()
				.game(savedGame)
				.optionTitle(optionDto.getOptionTitle())
				.build())
			.collect(Collectors.toList());
	}

	@Transactional
	public Long vote(Long memberIdx, OptionChoiceRequestDto optionChoiceRequestDto) {
		Game game = findGameById(optionChoiceRequestDto.getGameIdx());

		verifyIsDeadLinePassed(game.getDeadline());
		
		GameOption gameOption = gameOptionRepository.findById(optionChoiceRequestDto.getGameOptionIdx())
			.orElseThrow(() -> new BaseException(GAME_OPTION_NOT_FOUND));

		SharingAccount sharingAccount = game.getSharingAccount();
		SharingMember sharingMember = sharingAccountService.findBySharingAccountAndMemberIdx(
			sharingAccount.getSharingAccountIdx(), memberIdx);

		GameParticipant gameParticipant = GameParticipant
			.builder()
			.sharingMember(sharingMember)
			.gameOption(gameOption)
			.game(game)
			.build();

		GameParticipant participant = gameParticipantRepository.save(gameParticipant);
		return participant.getGameOption().getGameOptionIdx();
	}

	private void verifyIsDeadLinePassed(LocalDateTime deadline) {
		LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

		if (now.isAfter(deadline)) {
			throw new BaseException(IS_DEADLINE_PASSED);
		}
	}

	@Transactional
	public GameSelectResponseDto decideGameWinner(Long memberIdx, OptionChoiceRequestDto optionChoiceRequestDto) {

		Game game = findGameById(optionChoiceRequestDto.getGameIdx());

		sharingAccountService.verifyIsLeader(game.getSharingAccount().getSharingAccountIdx(), memberIdx);

		List<GameParticipant> gameParticipants = gameParticipantRepository.findByGame(game);

		List<GameParticipant> loserParticipants = decideWinnerAndLosers(gameParticipants, optionChoiceRequestDto.getGameOptionIdx());
		List<Member> loserMembers = toLoserMembers(loserParticipants);

		game.endGame();

		return createGameSelectResponseDto(game, loserMembers);

	}

	private Game findGameById(Long gameId) {
		return gameRepository.findById(gameId)
			.orElseThrow(() -> new BaseException(GAME_NOT_FOUND));
	}

	private List<GameParticipant> decideWinnerAndLosers(
		List<GameParticipant> gameParticipants,
		Long gameOptionIdx)
	{
		List<GameParticipant> loserParticipants = new ArrayList<>();

		gameParticipants.forEach(gameParticipant -> {
			if (gameParticipant.getGameOption().getGameOptionIdx().equals(gameOptionIdx)) {
				gameParticipant.setWinner();
			} else {
				loserParticipants.add(gameParticipant);
			}
		});

		return loserParticipants;
	}

	private List<Member> toLoserMembers(List<GameParticipant> loserParticipants) {
		return loserParticipants.stream()
			.map(gameParticipant -> gameParticipant.getSharingMember().getMember())
			.collect(Collectors.toList());
	}

	private GameSelectResponseDto createGameSelectResponseDto(Game game, List<Member> loserMembers) {
		List<MemberDto> memberDtos = loserMembers.stream()
			.map(MemberDto::of)
			.collect(Collectors.toList());

		return GameSelectResponseDto.of(game.getGameTitle(), memberDtos);
	}

	public GameDetailResponseDto getGameDetail(Long memberIdx, Long gameIdx) {
		Game game = gameRepository.findGameWithOptions(gameIdx)
			.orElseThrow(() -> new BaseException(GAME_NOT_FOUND));
		List<GameParticipant> participants = gameRepository.findParticipantsByGameIdx(gameIdx);

		LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
		boolean isVotingClosed = now.isAfter(game.getDeadline());

		Map<Long, List<MemberDto>> votedOptionMembersMap = new HashMap<>();
		Long votedOptionIdx = findVotedOptionIdxAndPopulateMembersMap(participants, memberIdx, votedOptionMembersMap);

		boolean isVotingMember = votedOptionIdx != null;

		List<GameOptionDto> gameOptionDtos = createGameOptionDtos(game.getGameOptions(), votedOptionMembersMap);

		return GameDetailResponseDto.of(votedOptionIdx, isVotingClosed, isVotingMember, game, gameOptionDtos);
	}

	private Long findVotedOptionIdxAndPopulateMembersMap(
		List<GameParticipant> participants,
		Long memberIdx,
		Map<Long, List<MemberDto>> votedOptionMembersMap
	) {
		Long votedOptionIdx = null;
		for (GameParticipant participant : participants) {
			Long gameOptionIdx = participant.getGameOption().getGameOptionIdx();
			MemberDto memberDto = MemberDto.of(participant.getSharingMember().getMember());

			votedOptionMembersMap
				.computeIfAbsent(gameOptionIdx, k -> new ArrayList<>())
				.add(memberDto);

			if (participant.getSharingMember().getMember().getMemberIdx().equals(memberIdx)) {
				votedOptionIdx = gameOptionIdx;
			}
		}
		return votedOptionIdx;
	}

	private List<GameOptionDto> createGameOptionDtos(
		List<GameOption> gameOptions,
		Map<Long, List<MemberDto>> votedOptionMembersMap
	) {
		return gameOptions.stream()
			.map(gameOption -> GameOptionDto.of(
				gameOption,
				votedOptionMembersMap.getOrDefault(gameOption.getGameOptionIdx(), Collections.emptyList())
			))
			.collect(Collectors.toList());
	}

	public GameHistoryResponseDto getGameHistoryAndCurrentGame(Long sharingAccountIdx) {
		List<Game> games = gameRepository.findGameHistoryBySharingAccountIdx(sharingAccountIdx);

		Game currentGame = games.stream()
			.filter(Game::getIsPlaying)
			.findFirst()
			.orElse(null);

		GameResultDto currentGameDto = currentGame != null ? convertToGameResultDto(currentGame) : null;

		List<GameResultDto> gameHistory = games.stream()
			.filter(game -> !game.getIsPlaying())
			.map(this::convertToGameResultDtoWithParticipants)
			.collect(Collectors.toList());

		return GameHistoryResponseDto.of(currentGameDto, gameHistory);
	}

	private GameResultDto convertToGameResultDtoWithParticipants(Game game) {
		List<MemberDto> winners = game.getGameParticipants().stream()
			.filter(GameParticipant::getIsWinner)
			.map(participant -> MemberDto.of(participant.getSharingMember().getMember()))
			.collect(Collectors.toList());

		List<MemberDto> losers = game.getGameParticipants().stream()
			.filter(participant -> !participant.getIsWinner())
			.map(participant -> MemberDto.of(participant.getSharingMember().getMember()))
			.collect(Collectors.toList());

		return GameResultDto.of(game, winners, losers);
	}

	private GameResultDto convertToGameResultDto(Game game) {
		return GameResultDto.of(game);
	}

	public GameDetailResponseDto getGameDetailV0(Long memberIdx, Long gameIdx) {
		Game game = gameRepository.findById(gameIdx).orElseThrow(() -> new BaseException(GAME_NOT_FOUND));

		List<GameParticipant> participants = game.getGameParticipants();
		LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
		boolean isVotingClosed = now.isAfter(game.getDeadline());

		Map<Long, List<MemberDto>> votedOptionMembersMap = new HashMap<>();
		Long votedOptionIdx = findVotedOptionIdxAndPopulateMembersMap(participants, memberIdx, votedOptionMembersMap);

		boolean isVotingMember = votedOptionIdx != null;
		List<GameOptionDto> gameOptionDtos = createGameOptionDtos(game.getGameOptions(), votedOptionMembersMap);

		return GameDetailResponseDto.of(votedOptionIdx, isVotingClosed, isVotingMember, game, gameOptionDtos);
	}
}
