package com.togetherhana.event.predict.service;

import com.togetherhana.event.predict.dto.EventConcludeRequest;
import com.togetherhana.event.predict.dto.EventGameCreateRequest;
import com.togetherhana.event.predict.dto.EventGameInfoResponse;
import com.togetherhana.event.predict.dto.PredictionRequest;
import com.togetherhana.event.predict.entity.EventGame;
import com.togetherhana.event.predict.entity.Prediction;
import com.togetherhana.event.predict.repository.EventGameRepository;
import com.togetherhana.event.predict.repository.PredictionRepository;
import com.togetherhana.exception.BaseException;
import com.togetherhana.exception.ErrorType;
import com.togetherhana.member.entity.Member;
import com.togetherhana.sportClub.entity.SportsClub;
import com.togetherhana.sportClub.repository.SportsClubRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventGameService {
    private final EventGameRepository eventGameRepository;
    private final SportsClubRepository sportsClubRepository;
    private final PredictionRepository predictionRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final ScheduledExecutorService scheduledExecutorService;

    @Transactional
    public Boolean createEventGame(EventGameCreateRequest eventGameCreateRequest) {
        EventGame eventGame = EventGame.builder()
                .title(eventGameCreateRequest.getTitle())
                .location(eventGameCreateRequest.getLocation())
                .startTime(
                        LocalDateTime.of(eventGameCreateRequest.getStartDay(), eventGameCreateRequest.getStartTime()))
                .homeTeamIdx(eventGameCreateRequest.getHomeTeamIdx())
                .awayTeamIdx(eventGameCreateRequest.getAwayTeamIdx())
                .isDone(Boolean.FALSE)
                .build();
        eventGameRepository.save(eventGame);
        long delay = Date.from(eventGame.getStartTime().atZone(ZoneId.systemDefault()).toInstant()).getTime() -
                Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()).getTime();

        scheduledExecutorService.schedule(() -> applicationEventPublisher.publishEvent(
                EventGameStartEvent.builder().eventGameIdx(eventGame.getIdx()).build()), delay, TimeUnit.MILLISECONDS);
        return Boolean.TRUE;
    }


    public List<EventGameInfoResponse> allEventGames() {
        List<EventGame> eventGames = eventGameRepository.findAll();
        return eventGames.stream().filter(eventGame -> eventGame.getIsDone().equals(Boolean.FALSE))
                .map(eventGame -> {
                    SportsClub home = sportsClubRepository.findById(eventGame.getHomeTeamIdx())
                            .orElseThrow(() -> new BaseException(ErrorType.NO_SPORTSCLUB_INFO));
                    SportsClub away = sportsClubRepository.findById(eventGame.getAwayTeamIdx())
                            .orElseThrow(() -> new BaseException(ErrorType.NO_SPORTSCLUB_INFO));

                    return new EventGameInfoResponse(eventGame, home, away);
                }).toList();
    }

    @Transactional
    public Boolean concludeEventGame(EventConcludeRequest eventConcludeRequest) {

        EventGame eventGame = eventGameRepository.findById(eventConcludeRequest.getEventGameIdx()).orElseThrow(
                () -> new BaseException(ErrorType.EVENT_NOT_FOUND)
        );
        eventGame.winner(eventConcludeRequest.getWinningTeamIdx());

        List<Long> winners = findWinner(eventGame, eventConcludeRequest.getWinningTeamIdx());
        for (Long winner : winners) {
            giveMileageTo(winner, eventConcludeRequest.getAmount());
        }
        return Boolean.TRUE;
    }

    private void giveMileageTo(Long winner, int amount) {
        applicationEventPublisher.publishEvent(EventGameConcludeEvent.builder()
                .winner(winner)
                .prizeAmount(amount).build());
    }

    private List<Long> findWinner(EventGame eventGame, Long winningTeamIdx) {
        List<Prediction> predictions = predictionRepository.findByEventGameAndPredictTeamIdx(eventGame,
                winningTeamIdx);
        return predictions.stream().map(prediction -> prediction.getMember().getMemberIdx()).toList();
    }

    @Transactional
    public Boolean pickWinnerTeam(Member member, PredictionRequest predictionRequest) {
        EventGame eventGame = eventGameRepository.findById(predictionRequest.getEventGameIdx()).orElseThrow(
                () -> new BaseException(ErrorType.EVENT_NOT_FOUND)
        );
        verifyAlreadyPick(member, eventGame);
        verifyIsDone(eventGame);
        Prediction prediction = Prediction.builder()
                .eventGame(eventGame)
                .member(member)
                .predictTeamIdx(predictionRequest.getWinnerPredictTeamIdx())
                .build();

        predictionRepository.save(prediction);
        return Boolean.TRUE;
    }

    private void verifyIsDone(EventGame eventGame) {
        if (eventGame.getIsDone()) {
            throw new BaseException(ErrorType.ALREADY_START);
        }
    }

    private void verifyAlreadyPick(Member member, EventGame eventGame) {
        if (predictionRepository.existsByMemberAndEventGame(member, eventGame)) {
            throw new BaseException(ErrorType.ALREADY_PICK);
        }
    }
}
