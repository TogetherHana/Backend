package com.togetherhana.game.entity;

import com.togetherhana.base.BaseEntity;
import com.togetherhana.sharingAccount.entity.SharingAccount;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "game")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Game extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_idx")
    private Long id;

    private String gameTitle;
    private LocalDateTime deadline;
    private Integer fine;
    private Boolean isPlaying;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sharing_account_idx")
    private SharingAccount sharingAccount;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private List<GameParticipant> gameParticipants;

    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY)
    private List<GameOption> gameOptions;

    @Builder
    public Game(String gameTitle, LocalDateTime deadline, Integer fine, SharingAccount sharingAccount) {
        this.gameTitle = gameTitle;
        this.deadline = deadline;
        this.fine = fine;
        this.sharingAccount = sharingAccount;
        this.isPlaying = Boolean.TRUE;
    }

    public void endGame() {
        this.isPlaying = Boolean.FALSE;
    }
}
