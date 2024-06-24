package com.togetherhana.game.entity;

import com.togetherhana.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="game_option")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class GameOption extends BaseEntity {

    // 빅매치 선택지 아이디
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "game_option_idx")
    private Long gameOptionIdx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_idx")
    private Game game;

    private String optionTitle;

}
