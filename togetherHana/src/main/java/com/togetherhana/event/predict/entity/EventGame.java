package com.togetherhana.event.predict.entity;

import com.togetherhana.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventGame extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_game_idx")
    private Long idx;

    private Long homeTeamIdx;
    private Long awayTeamIdx;
    private String title;
    private LocalDateTime startTime;
    private String location;
    private Long winnerTeamIdx;
    private Boolean isDone;

    public void closeGame() {
        this.isDone = Boolean.TRUE;
    }
    public void winner(Long winnerTeam) {
        this.winnerTeamIdx = winnerTeam;
    }
}
