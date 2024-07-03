package com.togetherhana.event.predict.dto;

import com.togetherhana.event.predict.entity.EventGame;
import com.togetherhana.sportClub.entity.SportsClub;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class EventGameInfoResponse {
    private Long eventGameIdx;
    private String eventTitle;
    private LocalDateTime eventStartTime;
    private String eventLocation;
    private boolean isDone;

    private TeamInfo home;
    private TeamInfo away;

    public EventGameInfoResponse(EventGame eventGame, SportsClub home, SportsClub away) {
        this.eventGameIdx = eventGame.getIdx();
        this.eventTitle = eventGame.getTitle();
        this.eventStartTime = eventGame.getStartTime();
        this.eventLocation = eventGame.getLocation();
        this.isDone = eventGame.getIsDone();
        this.home = new TeamInfo(home.getName(), home.getSportsClubIdx(), home.getImgUrl());
        this.away = new TeamInfo(away.getName(), away.getSportsClubIdx(), away.getImgUrl());
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class TeamInfo {
        private String teamName;
        private Long teamId;
        private String teamImg;
    }
}

