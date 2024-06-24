package com.togetherhana.game.entity;

import com.togetherhana.base.BaseEntity;
import com.togetherhana.sharingAccount.entity.SharingMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class GameParticipant extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_participant_idx")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sharing_member_idx")
    private SharingMember sharingMember;

    @ManyToOne
    @JoinColumn(name = "game_option_idx")
    private GameOption gameOption;

    @ManyToOne
    @JoinColumn(name = "game_idx")
    private Game game;

    private Boolean isWinner;

    @Builder
    public GameParticipant(SharingMember sharingMember, GameOption gameOption, Game game) {
        this.sharingMember = sharingMember;
        this.gameOption = gameOption;
        this.game = game;
        this.isWinner = Boolean.FALSE;
    }
}
