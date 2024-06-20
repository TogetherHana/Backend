package com.togetherhana.sportClub.entity;

import com.togetherhana.base.BaseEntity;
import com.togetherhana.base.SportsType;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="sportsclub")
@Table(name="sportsclub")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class SportsClub extends BaseEntity {

    // 응원팀 아이디
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "sports_club_idx")
    private Long sportsClubIdx;

    // 구단 이름
    private String name;

    // 엠블럼 이미지 URL
    private String img;

    @Enumerated(EnumType.STRING)
    private SportsType type;

}
